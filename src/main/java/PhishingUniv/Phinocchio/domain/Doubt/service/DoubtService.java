package PhishingUniv.Phinocchio.domain.Doubt.service;

import PhishingUniv.Phinocchio.domain.Doubt.dto.DoubtRequestDto;
import PhishingUniv.Phinocchio.domain.Doubt.dto.MLRequestDto;
import PhishingUniv.Phinocchio.domain.Doubt.dto.MLResponseDto;
import PhishingUniv.Phinocchio.domain.Doubt.entity.DoubtEntity;
import PhishingUniv.Phinocchio.domain.Doubt.repository.DoubtRepository;
import PhishingUniv.Phinocchio.domain.Login.repository.UserRepository;
import PhishingUniv.Phinocchio.domain.Setting.entity.SettingEntity;
import PhishingUniv.Phinocchio.domain.Setting.repository.SettingRepository;
import PhishingUniv.Phinocchio.domain.Sos.dto.MessageDTO;
import PhishingUniv.Phinocchio.domain.Sos.dto.SmsResponseDTO;
import PhishingUniv.Phinocchio.domain.Sos.entity.SosEntity;
import PhishingUniv.Phinocchio.domain.Sos.service.SmsService;
import PhishingUniv.Phinocchio.domain.Sos.service.SosService;
import PhishingUniv.Phinocchio.domain.User.entity.UserEntity;
import PhishingUniv.Phinocchio.domain.Voice.entity.VoiceEntity;
import PhishingUniv.Phinocchio.domain.Voice.repository.VoiceRepository;
import PhishingUniv.Phinocchio.exception.Doubt.DoubtAppException;
import PhishingUniv.Phinocchio.exception.Doubt.DoubtErrorCode;
import PhishingUniv.Phinocchio.exception.Login.LoginAppException;
import PhishingUniv.Phinocchio.exception.Login.LoginErrorCode;
import PhishingUniv.Phinocchio.exception.Setting.SettingAppException;
import PhishingUniv.Phinocchio.exception.Setting.SettingErrorCode;
import PhishingUniv.Phinocchio.exception.Sos.SosAppException;
import PhishingUniv.Phinocchio.exception.Sos.SosErrorCode;
import PhishingUniv.Phinocchio.exception.Voice.VoiceAppException;
import PhishingUniv.Phinocchio.exception.Voice.VoiceErrorCode;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RestController;

import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@RestController
public class DoubtService {

    private final MLService mlService;

    private final DoubtRepository doubtRepository;

    private final SettingRepository settingRepository;

    private final VoiceRepository voiceRepository;

    private final UserRepository userRepository;

    private final SosService sosService;

    private final SmsService smsService;

    private void addDoubt(DoubtRequestDto doubtRequestDto, String text, int level) {
        // 목소리 저장
        VoiceEntity voiceEntity = new VoiceEntity();
        voiceEntity.setText(text);
        VoiceEntity savedVoiceEntity = voiceRepository.save(voiceEntity);
        if(savedVoiceEntity == null) {
            throw new DoubtAppException(DoubtErrorCode.FAILED_TO_SAVE, "의심내역 저장에 실패하였습니다.");
        }

        // 의심내역 저장
        DoubtEntity doubtEntity = new DoubtEntity();
        doubtEntity.setPhoneNumber(doubtRequestDto.getPhoneNumber());
        doubtEntity.setLevel(level);
        doubtEntity.setUserId(doubtRequestDto.getUserId());
        doubtEntity.setVoice_id(savedVoiceEntity.getVoiceId());
        DoubtEntity savedDoubtEntity = doubtRepository.save(doubtEntity);
        System.out.println(savedDoubtEntity);
        if(savedDoubtEntity == null) {
            throw new VoiceAppException(VoiceErrorCode.FAILED_TO_SAVE, "목소리 저장에 실패하였습니다.");
        }

    }

    private void sendSms(int level) throws UnsupportedEncodingException, URISyntaxException, NoSuchAlgorithmException, InvalidKeyException, JsonProcessingException {
        String id = SecurityContextHolder.getContext().getAuthentication().getName();
        // 메세지 설정
        UserEntity userEntity = userRepository.findById(id)
                .orElseThrow(() -> new LoginAppException(LoginErrorCode.USERNAME_NOT_FOUND, "사용자를 불러올 수 없습니다."));
        String userPhone = userEntity.getPhoneNumber();
        String smsMsg = "[피노키오] " + userPhone + " 번호로 보이스피싱이 감지되었습니다. <" + level +"단계>";

        MessageDTO messageDTO = MessageDTO.builder()
                .content(smsMsg).build();

        // 메세지 보내기
        List<SosEntity> sosEntities = sosService.getSosListByLevel(level);
        for(SosEntity sosEntity : sosEntities) {
            messageDTO.setTo(sosEntity.getPhoneNumber());
            SmsResponseDTO smsResponseDTO = smsService.sendSms(messageDTO);
            if(!smsResponseDTO.getStatusCode().equals("202"))
                throw new SosAppException(SosErrorCode.FAILED_TO_SEND_SMS, "메세지 전송 실패");
        }
    }

    public ResponseEntity<?> doubt(DoubtRequestDto doubtRequestDto) throws UnsupportedEncodingException, URISyntaxException, NoSuchAlgorithmException, InvalidKeyException, JsonProcessingException {

        // 머신러닝 서버로 mlRequestDto를 전송하고 응답을 받음
        MLRequestDto mlRequestDto = new MLRequestDto(doubtRequestDto.getText());
        MLResponseDto mlResponseDto = mlService.processText(mlRequestDto);
        if(mlRequestDto == null) {
            throw new DoubtAppException(DoubtErrorCode.DISCONNCECTED_TO_MLSERVER, "머신러닝 서버와 연결이 되지 않습니다.");
        }


        // 보이스피싱 의심내역 저장
        String text = doubtRequestDto.getText();
        int level = mlResponseDto.getLevel();
        if(level > 0) {
            addDoubt(doubtRequestDto, text, level);
        }



        // 알람 설정 확인
        SettingEntity settingEntity = settingRepository.findByUserId(doubtRequestDto.getUserId())
                .orElseThrow(() -> new SettingAppException(SettingErrorCode.SETTING_NOT_FOUND, "설정을 불러올 수 없습니다."));

        // 보이스피싱 알람 설정 안 한 경우
        if(!settingEntity.getDetectAlram())
            return ResponseEntity.ok("not set detect alram");

        // 긴급 연락처 알람 설정 한 경우
        if(settingEntity.getSosAlram()) {
            sendSms(level);
        }

        // 보이스피싱 알람 설정 한 경우
        return ResponseEntity.ok(mlResponseDto);
    }

}

package PhishingUniv.Phinocchio.domain.Doubt.service;

import PhishingUniv.Phinocchio.domain.Doubt.dto.DoubtRequestDto;
import PhishingUniv.Phinocchio.domain.Doubt.dto.MLRequestDto;
import PhishingUniv.Phinocchio.domain.Doubt.dto.MLResponseDto;
import PhishingUniv.Phinocchio.domain.Doubt.entity.DoubtEntity;
import PhishingUniv.Phinocchio.domain.Doubt.repository.DoubtRepository;
import PhishingUniv.Phinocchio.domain.Setting.entity.SettingEntity;
import PhishingUniv.Phinocchio.domain.Setting.repository.SettingRepository;
import PhishingUniv.Phinocchio.domain.Sos.dto.MessageDTO;
import PhishingUniv.Phinocchio.domain.Sos.dto.SmsResponseDTO;
import PhishingUniv.Phinocchio.domain.Sos.entity.SosEntity;
import PhishingUniv.Phinocchio.domain.Sos.service.SmsService;
import PhishingUniv.Phinocchio.domain.Sos.service.SosService;
import PhishingUniv.Phinocchio.domain.Voice.entity.VoiceEntity;
import PhishingUniv.Phinocchio.domain.Voice.repository.VoiceRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
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

    private final SosService sosService;

    private final SmsService smsService;

    public ResponseEntity<?> doubt(DoubtRequestDto doubtRequestDto) throws UnsupportedEncodingException, URISyntaxException, NoSuchAlgorithmException, InvalidKeyException, JsonProcessingException {
        /*
            머신러닝 서버로 mlRequestDto를 전송하고 응답을 받음
         */

        System.out.println(doubtRequestDto);

        MLRequestDto mlRequestDto = new MLRequestDto(doubtRequestDto.getText());
        MLResponseDto mlResponseDto = mlService.processText(mlRequestDto);

        if(mlRequestDto == null) {
            return ResponseEntity.ok("disconnect ML server");
        }


        /*
            level 확인 및 데이터 저장
         */

        String text = doubtRequestDto.getText();
        int level = mlResponseDto.getLevel();

        //// 보이스피싱 의심하지 않는 경우
        if(level == 0) {
            return ResponseEntity.ok("not voice phishing");
        }

        //// 보이스피싱 의심하는 경우

        // 목소리 저장
        VoiceEntity voiceEntity = new VoiceEntity();
        voiceEntity.setText(text);
        VoiceEntity savedVoiceEntity = voiceRepository.save(voiceEntity);
        if(savedVoiceEntity == null) {
            return ResponseEntity.ok("not save voice");
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
            return ResponseEntity.ok("not save doubt");
        }

        // 알람 설정 확인
        Optional<SettingEntity> optionalSettingEntity = settingRepository.findByUserId(doubtRequestDto.getUserId());

        // 알람 설정 데이터 불러오기 실패
        if(!optionalSettingEntity.isPresent()) {
            return ResponseEntity.ok("no exist setting");
        }

        SettingEntity settingEntity = optionalSettingEntity.get();

        // 보이스피싱 알람 설정 안 한 경우
        if(!settingEntity.getDetectAlram())
            return ResponseEntity.ok("not set detect alram");

        // 긴급 연락처 알람 설정 한 경우
        if(settingEntity.getSosAlram()) {
            MessageDTO messageDTO = MessageDTO.builder()
                    .content("[피노키오] 보이스피싱이 의심됩니다.").build();

            List<SosEntity> sosEntities = sosService.getSosListByLevel(level);
            for(SosEntity sosEntity : sosEntities) {
                messageDTO.setTo(sosEntity.getPhoneNumber());
                SmsResponseDTO smsResponseDTO = smsService.sendSms(messageDTO);
                if(!smsResponseDTO.getStatusCode().equals("202"))
                    return ResponseEntity.ok("failed to send SMS");
            }
        }


        // 보이스피싱 알람 설정 한 경우
        return ResponseEntity.ok(mlResponseDto);
    }

}

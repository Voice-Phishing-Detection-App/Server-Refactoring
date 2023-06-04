package PhishingUniv.Phinocchio.Doubt.service;

import PhishingUniv.Phinocchio.Doubt.dto.DoubtRequestDto;
import PhishingUniv.Phinocchio.Doubt.dto.MLRequestDto;
import PhishingUniv.Phinocchio.Doubt.dto.MLResponseDto;
import PhishingUniv.Phinocchio.Doubt.entity.DoubtEntity;
import PhishingUniv.Phinocchio.Doubt.repository.DoubtRepository;
import PhishingUniv.Phinocchio.Setting.entity.SettingEntity;
import PhishingUniv.Phinocchio.Setting.repository.SettingRepository;
import PhishingUniv.Phinocchio.Voice.entity.VoiceEntity;
import PhishingUniv.Phinocchio.Voice.repository.VoiceRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@RestController
public class DoubtService {

    private final MLService mlService;

    private final DoubtRepository doubtRepository;

    private final SettingRepository settingRepository;

    private final VoiceRepository voiceRepository;

    private final ModelMapper modelMapper = new ModelMapper();

    public ResponseEntity<?> doubt(DoubtRequestDto doubtRequestDto) {
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

        //  알람 설정 안 한 경우
        if(!settingEntity.getDetectAlram())
            return ResponseEntity.ok("not set detect alram");

        // 알람 설정 한 경우
        return ResponseEntity.ok(mlResponseDto);
    }

}

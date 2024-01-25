package PhishingUniv.Phinocchio.domain.Doubt.service;

import PhishingUniv.Phinocchio.domain.Doubt.dto.AnalyzedVoicePhishingDto;
import PhishingUniv.Phinocchio.domain.Doubt.dto.DoubtRequestDto;
import PhishingUniv.Phinocchio.domain.Doubt.dto.DoubtResponseDto;
import PhishingUniv.Phinocchio.domain.Doubt.dto.MLRequestDto;
import PhishingUniv.Phinocchio.domain.Doubt.dto.MLResponseDto;
import PhishingUniv.Phinocchio.domain.Doubt.dto.MLServerRequestDto;
import PhishingUniv.Phinocchio.domain.Doubt.dto.MLServerResponseDto;
import PhishingUniv.Phinocchio.domain.Doubt.entity.DoubtEntity;
import PhishingUniv.Phinocchio.domain.Doubt.repository.DoubtRepository;
import PhishingUniv.Phinocchio.domain.FCM.service.FCMService;
import PhishingUniv.Phinocchio.domain.Login.service.UserService;
import PhishingUniv.Phinocchio.domain.SMS.service.SmsService;
import PhishingUniv.Phinocchio.domain.User.entity.UserEntity;
import PhishingUniv.Phinocchio.domain.Voice.entity.VoiceEntity;
import PhishingUniv.Phinocchio.domain.Voice.service.VoiceService;
import PhishingUniv.Phinocchio.exception.Doubt.DoubtAppException;
import PhishingUniv.Phinocchio.exception.Doubt.DoubtErrorCode;
import PhishingUniv.Phinocchio.exception.Login.InvalidJwtException;
import PhishingUniv.Phinocchio.exception.Setting.SettingAppException;
import PhishingUniv.Phinocchio.exception.Sos.SosAppException;
import PhishingUniv.Phinocchio.exception.Voice.VoiceAppException;
import com.fasterxml.jackson.core.JsonProcessingException;
import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DoubtService {

  private final MLService mlService;

  private final DoubtRepository doubtRepository;

  private final VoiceService voiceService;

  private final SmsService smsService;

  private final FCMService fcmNotificationService;

  private final UserService userService;

  public DoubtResponseDto doubt(DoubtRequestDto doubtRequestDto)
      throws UnsupportedEncodingException, URISyntaxException, NoSuchAlgorithmException, InvalidKeyException,
      JsonProcessingException, InvalidJwtException, DoubtAppException, SettingAppException, SosAppException {

    // 보이스피싱 분석
    String phoneNumber = doubtRequestDto.getPhoneNumber();
    String text = doubtRequestDto.getText();
    AnalyzedVoicePhishingDto analysisResultDto = analyzeVoicePhishing(phoneNumber, text);

    int level = analysisResultDto.getLevel();
    if (isVoicePhishing(level)) {
      // 보이스피싱 분석결과 저장
      saveAnalyzedVoicePhishing(analysisResultDto);

      // 긴급 연락처 SMS 전송
      smsService.sendSms(level);

      // push 알림 전송
      fcmNotificationService.sendPushNotification(level);
    }

    DoubtResponseDto doubtResponseDto = generateDoubtResponse(analysisResultDto);
    return doubtResponseDto;
  }

  public List<DoubtEntity> getDoubtList() throws InvalidJwtException {
    UserEntity user = userService.getCurrentUser();
    return user.getDoubtList();
  }

  public MLServerResponseDto setMLServerUrl(MLServerRequestDto mlServerRequestDto) {
    return mlService.setMLServerUrl(mlServerRequestDto.getUrl());
  }

  private AnalyzedVoicePhishingDto analyzeVoicePhishing(String phoneNumber, String text)
      throws DoubtAppException {
    MLRequestDto mlRequestDto = MLRequestDto.builder().text(text).build();
    MLResponseDto mlResponseDto = mlService.processText(mlRequestDto);
    if (mlRequestDto == null) {
      throw new DoubtAppException(DoubtErrorCode.DISCONNCECTED_TO_MLSERVER);
    }

    AnalyzedVoicePhishingDto analysisResult = AnalyzedVoicePhishingDto.builder()
        .phoneNumber(phoneNumber)
        .text(text)
        .level(mlResponseDto.getLevel())
        .phishing(mlResponseDto.getPhishing())
        .build();

    return analysisResult;
  }

  private void saveAnalyzedVoicePhishing(AnalyzedVoicePhishingDto analyzedVoicePhishingDto)
      throws VoiceAppException {
    int level = analyzedVoicePhishingDto.getLevel();
    if (isVoicePhishing(level)) {
      VoiceEntity voice = voiceService.saveVoice(analyzedVoicePhishingDto.getText());
      saveDoubt(analyzedVoicePhishingDto, voice);
    }
  }

  private boolean isVoicePhishing(int level) {
    if (level > 0) {
      return true;
    }
    return false;
  }

  private void saveDoubt(AnalyzedVoicePhishingDto analyzedVoicePhishingDto, VoiceEntity voice)
      throws DoubtAppException, InvalidJwtException {
    DoubtEntity doubtEntity = new DoubtEntity();
    doubtEntity.setPhoneNumber(analyzedVoicePhishingDto.getPhoneNumber());
    doubtEntity.setLevel(analyzedVoicePhishingDto.getLevel());
    doubtEntity.setVoice(voice);
    doubtEntity.setTitle(generateTitleWithCurrentTime());
    doubtEntity.setUser(userService.getCurrentUser());

    DoubtEntity savedDoubtEntity = doubtRepository.save(doubtEntity);
    if (savedDoubtEntity == null) {
      throw new DoubtAppException(DoubtErrorCode.FAILED_TO_SAVE);
    }
  }

  public String generateTitleWithCurrentTime() {
    LocalDate nowDate = LocalDate.now();
    LocalTime nowTime = LocalTime.now();

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("a hh:mm");

    StringBuilder title = new StringBuilder();
    title.append(nowDate.getYear())
        .append('-')
        .append(nowDate.getMonthValue())
        .append('-')
        .append(nowDate.getDayOfMonth())
        .append(' ')
        .append(nowTime.format(formatter))
        .append(" 통화내역");

    return title.toString();
  }

  private DoubtResponseDto generateDoubtResponse(
      AnalyzedVoicePhishingDto analyzedVoicePhishingDto) {
    DoubtResponseDto doubtResponseDto = DoubtResponseDto.builder()
        .level(analyzedVoicePhishingDto.getLevel())
        .phishing(analyzedVoicePhishingDto.getPhishing())
        .build();
    return doubtResponseDto;
  }
}

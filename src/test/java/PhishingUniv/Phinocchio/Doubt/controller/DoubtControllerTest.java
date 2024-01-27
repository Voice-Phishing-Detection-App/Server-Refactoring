package PhishingUniv.Phinocchio.Doubt.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.refEq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import PhishingUniv.Phinocchio.domain.Doubt.controller.DoubtController;
import PhishingUniv.Phinocchio.domain.Doubt.dto.DoubtRequestDto;
import PhishingUniv.Phinocchio.domain.Doubt.dto.DoubtResponseDto;
import PhishingUniv.Phinocchio.domain.Doubt.dto.MLResponseDto;
import PhishingUniv.Phinocchio.domain.Doubt.dto.MLServerRequestDto;
import PhishingUniv.Phinocchio.domain.Doubt.dto.MLServerResponseDto;
import PhishingUniv.Phinocchio.domain.Doubt.entity.DoubtEntity;
import PhishingUniv.Phinocchio.domain.Doubt.service.DoubtService;
import PhishingUniv.Phinocchio.domain.Report.entity.ReportEntity;
import PhishingUniv.Phinocchio.domain.User.entity.UserEntity;
import PhishingUniv.Phinocchio.domain.Voice.entity.VoiceEntity;
import PhishingUniv.Phinocchio.exception.Doubt.DoubtAppException;
import PhishingUniv.Phinocchio.exception.Doubt.DoubtErrorCode;
import PhishingUniv.Phinocchio.exception.FCM.FCMAppException;
import PhishingUniv.Phinocchio.exception.FCM.FCMErrorCode;
import PhishingUniv.Phinocchio.exception.Login.InvalidJwtException;
import PhishingUniv.Phinocchio.exception.Login.LoginAppException;
import PhishingUniv.Phinocchio.exception.Login.LoginErrorCode;
import PhishingUniv.Phinocchio.exception.Sos.SosAppException;
import PhishingUniv.Phinocchio.exception.Sos.SosErrorCode;
import PhishingUniv.Phinocchio.exception.Voice.VoiceAppException;
import PhishingUniv.Phinocchio.exception.Voice.VoiceErrorCode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.ArrayList;
import java.util.List;
import org.apache.catalina.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;

@WebMvcTest(controllers = DoubtController.class)
@MockBean(JpaMetamodelMappingContext.class)
public class DoubtControllerTest {

  private MockMvc mockMvc;

  @Autowired
  private WebApplicationContext context;

  @Autowired
  ObjectMapper objectMapper;

  @MockBean
  private DoubtService doubtService;

  @BeforeEach
  private void setup() {
    mockMvc = MockMvcBuilders.webAppContextSetup(context)
        .addFilter(new CharacterEncodingFilter("UTF-8", true))
        .alwaysDo(print())
        .build();
  }

  @DisplayName("보이스피싱 의심 여부 판단 - 성공")
  @Test
  void SuccessToVoicePhishingDetection() throws Exception {
    // stub
    DoubtRequestDto doubtRequestDto = doubtRequestDto();
    DoubtResponseDto doubtResponseDto = doubtResponseDto();

    // given
    given(doubtService.doubt(any(DoubtRequestDto.class))).willReturn(doubtResponseDto);

    // when
    ResultActions result = mockMvc.perform(post("/doubt")
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(doubtRequestDto)));

    // then
    result
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.level").exists())
        .andExpect(jsonPath("$.level").value(2))
        .andExpect(jsonPath("$.phishing").exists())
        .andExpect(jsonPath("$.phishing").value(true));

    verify(doubtService).doubt(refEq(doubtRequestDto));
  }

  @DisplayName("보이스피싱 의심 여부 판단 - 실패 (토큰 만료)")
  @Test
  void FailToVoicePhishingDetectionWhenJwtExpires() throws Exception {
    // stub
    DoubtRequestDto doubtRequestDto = doubtRequestDto();
    LoginAppException loginAppException = new LoginAppException(LoginErrorCode.JWT_USER_NOT_FOUND);

    // given
    given(doubtService.doubt(any(DoubtRequestDto.class))).willThrow(loginAppException);

    // when
    ResultActions result = mockMvc.perform(post("/doubt")
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(doubtRequestDto)));

    // then
    result
        .andExpect(status().isNotFound())
        .andExpect(jsonPath("$.error").exists())
        .andExpect(jsonPath("$.error").value("JWT_USER_NOT_FOUND"))
        .andExpect(jsonPath("$.message").exists())
        .andExpect(jsonPath("$.message").value("로그인 중인 사용자 정보 찾을 수 없습니다."));

    verify(doubtService).doubt(refEq(doubtRequestDto));
  }

  @DisplayName("보이스피싱 의심 여부 판단 - 실패 (머신러닝 서버와 통신 문제)")
  @Test
  void FailToVoicePhishingDetectionDueToUnconnectedMLServer() throws Exception {
    // stub
    DoubtRequestDto doubtRequestDto = doubtRequestDto();
    DoubtAppException doubtAppException = new DoubtAppException(
        DoubtErrorCode.DISCONNCECTED_TO_MLSERVER);

    // given
    given(doubtService.doubt(any(DoubtRequestDto.class))).willThrow(doubtAppException);

    // when
    ResultActions result = mockMvc.perform(post("/doubt")
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(doubtRequestDto)));

    // then
    result
        .andExpect(status().isInternalServerError())
        .andExpect(jsonPath("$.error").exists())
        .andExpect(jsonPath("$.error").value("DISCONNCECTED_TO_MLSERVER"))
        .andExpect(jsonPath("$.message").exists())
        .andExpect(jsonPath("$.message").value("머신러닝 서버와 연결이 되지 않습니다."));

    verify(doubtService).doubt(refEq(doubtRequestDto));
  }

  @DisplayName("보이스피싱 의심 여부 판단 - 실패 (의심내역 저장 문제)")
  @Test
  void FailToVoicePhishingDetectionDueToUnsavedDoubt() throws Exception {
    // stub
    DoubtRequestDto doubtRequestDto = doubtRequestDto();
    DoubtAppException doubtAppException = new DoubtAppException(DoubtErrorCode.FAILED_TO_SAVE);

    // given
    given(doubtService.doubt(any(DoubtRequestDto.class))).willThrow(doubtAppException);

    // when
    ResultActions result = mockMvc.perform(post("/doubt")
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(doubtRequestDto)));

    // then
    result
        .andExpect(status().isInternalServerError())
        .andExpect(jsonPath("$.error").exists())
        .andExpect(jsonPath("$.error").value("FAILED_TO_SAVE"))
        .andExpect(jsonPath("$.message").exists())
        .andExpect(jsonPath("$.message").value("의심내역 저장에 실패하였습니다."));

    verify(doubtService).doubt(refEq(doubtRequestDto));
  }

  @DisplayName("보이스피싱 의심 여부 판단 - 실패 (목소리 저장 문제)")
  @Test
  void FailToVoicePhishingDetectionDueToUnsavedVoice() throws Exception {
    // stub
    DoubtRequestDto doubtRequestDto = doubtRequestDto();
    VoiceAppException voiceAppException = new VoiceAppException(VoiceErrorCode.FAILED_TO_SAVE);

    // given
    given(doubtService.doubt(any(DoubtRequestDto.class))).willThrow(voiceAppException);

    // when
    ResultActions result = mockMvc.perform(post("/doubt")
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(doubtRequestDto)));

    // then
    result
        .andExpect(status().isInternalServerError())
        .andExpect(jsonPath("$.error").exists())
        .andExpect(jsonPath("$.error").value("FAILED_TO_SAVE"))
        .andExpect(jsonPath("$.message").exists())
        .andExpect(jsonPath("$.message").value("목소리 저장에 실패하였습니다."));

    verify(doubtService).doubt(refEq(doubtRequestDto));
  }

  @DisplayName("보이스피싱 의심 여부 판단 - 실패 (FCM push 문제)")
  @Test
  void FailToVoicePhishingDetectionDueToFcmPush() throws Exception {
    // stub
    DoubtRequestDto doubtRequestDto = doubtRequestDto();
    FCMAppException fcmAppException = new FCMAppException(FCMErrorCode.FCM_ERROR);

    // given
    given(doubtService.doubt(any(DoubtRequestDto.class))).willThrow(fcmAppException);

    // when
    ResultActions result = mockMvc.perform(post("/doubt")
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(doubtRequestDto)));

    // then
    result
        .andExpect(status().isConflict())
        .andExpect(jsonPath("$.error").exists())
        .andExpect(jsonPath("$.error").value("FCM_ERROR"))
        .andExpect(jsonPath("$.message").exists())
        .andExpect(jsonPath("$.message").value("FCM 관련 오류입니다."));

    verify(doubtService).doubt(refEq(doubtRequestDto));
  }

  @DisplayName("보이스피싱 의심 여부 판단 - 실패 (긴급연락처로 SMS 문자 전송 문제)")
  @Test
  void FailToVoicePhishingDetectionDueToSMS() throws Exception {
    // stub
    DoubtRequestDto doubtRequestDto = doubtRequestDto();
    SosAppException sosAppException = new SosAppException(SosErrorCode.FAILED_TO_SEND_SMS);

    // given
    given(doubtService.doubt(any(DoubtRequestDto.class))).willThrow(sosAppException);

    // when
    ResultActions result = mockMvc.perform(post("/doubt")
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(doubtRequestDto)));

    // then
    result
        .andExpect(status().isInternalServerError())
        .andExpect(jsonPath("$.error").exists())
        .andExpect(jsonPath("$.error").value("FAILED_TO_SEND_SMS"))
        .andExpect(jsonPath("$.message").exists())
        .andExpect(jsonPath("$.message").value("메세지 전송 실패"));

    verify(doubtService).doubt(refEq(doubtRequestDto));
  }

  @DisplayName("보이스피싱 의심내역 목록 조회 - 성공 (의심내역이 있는 경우)")
  @Test
  void SuccessToVoicePhishingListSearchInDoubt () throws Exception {
    // stub
    List<DoubtEntity> doubtList = doubtList();

    // given
    given(doubtService.getDoubtList()).willReturn(doubtList);

    // when
    ResultActions result = mockMvc.perform(get("/doubt/get")
        .contentType(MediaType.APPLICATION_JSON));

    // then
    result
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.[0].doubtId").exists())
        .andExpect(jsonPath("$.[0].doubtId").value(1))
        .andExpect(jsonPath("$.[0].level").exists())
        .andExpect(jsonPath("$.[0].level").value(2))
        .andExpect(jsonPath("$.[0].phoneNumber").exists())
        .andExpect(jsonPath("$.[0].phoneNumber").value("01012341234"))
        .andExpect(jsonPath("$.[0].title").exists())
        .andExpect(jsonPath("$.[0].title").value("2023-01-01 통화내역"))
        .andExpect(jsonPath("$.[1].doubtId").exists())
        .andExpect(jsonPath("$.[1].doubtId").value(2))
        .andExpect(jsonPath("$.[1].level").exists())
        .andExpect(jsonPath("$.[1].level").value(3))
        .andExpect(jsonPath("$.[1].phoneNumber").exists())
        .andExpect(jsonPath("$.[1].phoneNumber").value("01043214321"))
        .andExpect(jsonPath("$.[1].title").exists())
        .andExpect(jsonPath("$.[1].title").value("2023-01-02 통화내역"));

    verify(doubtService).getDoubtList();
  }

  @DisplayName("보이스피싱 의심내역 목록 조회 - 성공 (의심내역이 없는 경우)")
  @Test
  void SuccessToEmptyVoicePhishingListSearchInDoubt () throws Exception {
    // stub
    List<DoubtEntity> doubtList = doubtList();

    // given
    given(doubtService.getDoubtList()).willReturn(doubtList);

    // when
    ResultActions result = mockMvc.perform(get("/doubt/get")
        .contentType(MediaType.APPLICATION_JSON));

    // then
    result
        .andExpect(status().isOk())
        .andDo(print());

    verify(doubtService).getDoubtList();
  }

  @DisplayName("보이스피싱 의심내력 목록 조회 - 실패 (토큰 만료)")
  @Test
  void FailToVoicePhishingListSearchWhenJwtExpires () throws Exception {
    // stub
    InvalidJwtException invalidJwtException = new InvalidJwtException(LoginErrorCode.JWT_USER_NOT_FOUND);

    // given
    given(doubtService.getDoubtList()).willThrow(invalidJwtException);

    // when
    ResultActions result = mockMvc.perform(get("/doubt/get")
        .contentType(MediaType.APPLICATION_JSON));

    // then
    result
        .andExpect(status().isNotFound())
        .andExpect(jsonPath("$.error").exists())
        .andExpect(jsonPath("$.error").value("JWT_USER_NOT_FOUND"))
        .andExpect(jsonPath("$.message").exists())
        .andExpect(jsonPath("$.message").value("로그인 중인 사용자 정보 찾을 수 없습니다."));

    verify(doubtService).getDoubtList();
  }

  @DisplayName("보이스피싱 의심 분석하는 머신러닝 서버 주소 설정 - 성공")
  @Test
  void SuccessToSettingMLServer () throws Exception {
    // stub
    MLServerRequestDto mlServerRequestDto = new MLServerRequestDto();
    mlServerRequestDto.setUrl(mlserver());

    MLServerResponseDto mlServerResponseDto = new MLServerResponseDto();
    mlServerResponseDto.setMlServer(mlserver());

    // given
    given(doubtService.setMLServerUrl(any(MLServerRequestDto.class))).willReturn(mlServerResponseDto);

    // when
    ResultActions result = mockMvc.perform(post("/doubt/set-ml-server")
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(mlServerRequestDto)));

    // then
    result
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.mlServer").exists())
        .andExpect(jsonPath("$.mlServer").value(mlserver()))
        .andDo(print());

    verify(doubtService).setMLServerUrl(refEq(mlServerRequestDto));
  }

  private DoubtRequestDto doubtRequestDto() {
    DoubtRequestDto doubtRequestDto = new DoubtRequestDto();
    doubtRequestDto.setPhoneNumber("01012341234");
    doubtRequestDto.setText("안녕하십니까 서울중앙지방검찰청 김피싱 검사입니다 현재 김피해님의 통장이 범죄에 연루되어 있어 검찰청으로 출석해주셔야 합니다");
    return doubtRequestDto;
  }

  private DoubtResponseDto doubtResponseDto() {
    DoubtResponseDto doubtResponseDto = new DoubtResponseDto();
    doubtResponseDto.setLevel(2);
    doubtResponseDto.setPhishing(true);
    return doubtResponseDto;
  }

  private List<DoubtEntity> doubtList() {
    UserEntity userEntity = UserEntity.builder().build();
    ReportEntity reportEntity = ReportEntity.builder().build();
    VoiceEntity voiceEntity = VoiceEntity.builder().build();

    DoubtEntity doubtEntity1 = DoubtEntity.builder()
            .doubtId(1L)
            .level(2)
            .phoneNumber("01012341234")
            .title("2023-01-01 통화내역")
            .user(userEntity)
            .report(reportEntity)
            .voice(voiceEntity)
            .build();

    DoubtEntity doubtEntity2 = DoubtEntity.builder()
        .doubtId(2L)
        .level(3)
        .phoneNumber("01043214321")
        .title("2023-01-02 통화내역")
        .user(userEntity)
        .report(reportEntity)
        .voice(voiceEntity)
        .build();

    List<DoubtEntity> doubtList = new ArrayList<>();
    doubtList.add(doubtEntity1);
    doubtList.add(doubtEntity2);

    return doubtList;
  }

  private List<DoubtEntity> emptydoubtList() {
    List<DoubtEntity> doubtList = new ArrayList<>();

    return doubtList;
  }

  private String mlserver() {
    return "https://ml-server.com";
  }

}

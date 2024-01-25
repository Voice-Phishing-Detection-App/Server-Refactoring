package PhishingUniv.Phinocchio.domain.Login.controller;

import PhishingUniv.Phinocchio.domain.Login.dto.LoginDto;
import PhishingUniv.Phinocchio.domain.Login.dto.SignupRequestDto;
import PhishingUniv.Phinocchio.domain.Login.dto.SignupResponseDto;
import PhishingUniv.Phinocchio.domain.Login.security.CustomUserDetailsService;
import PhishingUniv.Phinocchio.domain.Login.security.JwtAuthEntryPoint;
import PhishingUniv.Phinocchio.domain.Login.security.JwtGenerator;
import PhishingUniv.Phinocchio.domain.Login.security.SecurityConfig;
import PhishingUniv.Phinocchio.domain.Login.service.UserService;
import PhishingUniv.Phinocchio.exception.Login.LoginAppException;
import PhishingUniv.Phinocchio.exception.Login.LoginErrorCode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.client.AutoConfigureWebClient;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;


import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
@MockBean(JpaMetamodelMappingContext.class)
@Import(SecurityConfig.class)
@AutoConfigureWebClient
class UserControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    UserService userService;

    @MockBean
    CustomUserDetailsService customUserDetailsService;

    @MockBean
    JwtAuthEntryPoint jwtAuthEntryPoint;

    @MockBean
    JwtGenerator jwtGenerator;

    @Autowired
    ObjectMapper objectMapper;


    @Test
    @DisplayName("회원가입 성공")
    void successToSignUp() throws Exception {
        // stub
         SignupRequestDto requestDto = signupRequestDto();

        SignupResponseDto responseDto = new SignupResponseDto();
        responseDto.setId("userId");

        //given
        given(userService.registerUser(any(SignupRequestDto.class)))
            .willReturn(responseDto);

        // when
        ResultActions result = mockMvc.perform(
                post("/signup")
                        .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(requestDto)));


        // then
        result
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id").exists())
            .andExpect(jsonPath("$.id").value("userId"))
            .andDo(print());
    }


  @Test
  @DisplayName("로그인 성공")
  void successToLogin() throws Exception {
      // stub
      LoginDto loginDto = loginDto();

      // given
      given(userService.login(any(LoginDto.class)))
        .willReturn("token");

      // when
      ResultActions result = mockMvc.perform(
            post("/login")
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(loginDto)));

      // then
      result
          .andExpect(status().isOk())
          .andExpect(jsonPath("$.token").exists())
          .andDo(print());
  }

  @Test
  @DisplayName("로그인 실패 - 사용자 아이디 불일치")
  void failToLoginIfUserIdInvalid() throws Exception {

      // stub
      LoginDto loginDto = loginDto();

      // given
      given(userService.login(any(LoginDto.class)))
        .willThrow(new LoginAppException(LoginErrorCode.USERNAME_NOT_FOUND));

      // when
      ResultActions result = mockMvc.perform(
            post("/login")
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(loginDto)));

      // result
      result
          .andExpect(status().isNotFound())
          .andExpect(jsonPath("$.error").exists())
          .andExpect(jsonPath("$.error").value("USERNAME_NOT_FOUND"))
          .andExpect(jsonPath("$.message").exists())
          .andExpect(jsonPath("$.message").value("사용자를 불러올 수 없습니다."))
          .andDo(print());
  }

  @Test
  @DisplayName("로그인 실패 - 비밀번호 불일치")
  void failToLoginIfPasswordInvalid() throws Exception {

    // stub
    LoginDto loginDto = loginDto();

    // given
    given(userService.login(any(LoginDto.class)))
        .willThrow(new LoginAppException(LoginErrorCode.INVALID_PASSWORD));

    // when
    ResultActions result = mockMvc.perform(
        post("/login")
            .with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsBytes(loginDto)));

    // result
    result
        .andExpect(status().isUnauthorized())
        .andExpect(jsonPath("$.error").exists())
        .andExpect(jsonPath("$.error").value("INVALID_PASSWORD"))
        .andExpect(jsonPath("$.message").exists())
        .andExpect(jsonPath("$.message").value("틀린 비밀번호 입니다."))
        .andDo(print());
  }

  @Test
  @DisplayName("회원가입 실패 - 사용자 아이디 중복")
  void failToSignUpIfUserIdInvalid() throws Exception {

    // stub
    SignupRequestDto requestDto = signupRequestDto();

    // given
    given(userService.registerUser(any(SignupRequestDto.class)))
        .willThrow(new LoginAppException(LoginErrorCode.USERNAME_DUPLICATED));

    // when
    ResultActions result = mockMvc.perform(
        post("/signup")
            .with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsBytes(requestDto)));

    // result
    result
        .andExpect(status().isConflict())
        .andExpect(jsonPath("$.error").exists())
        .andExpect(jsonPath("$.error").value("USERNAME_DUPLICATED"))
        .andExpect(jsonPath("$.message").exists())
        .andExpect(jsonPath("$.message").value("이미 존재하는 아이디입니다."))
        .andDo(print());
  }

  @Test
  @DisplayName("회원가입 실패 - 전화번호 중복")
  void failToSignUpIfPhoneNumberInvalid() throws Exception {

    // stub
    SignupRequestDto requestDto = signupRequestDto();

    // given
    given(userService.registerUser(any(SignupRequestDto.class)))
        .willThrow(new LoginAppException(LoginErrorCode.PHONENUMBER_DUPLICATED));

    // when
    ResultActions result = mockMvc.perform(
        post("/signup")
            .with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsBytes(requestDto)));

    // result
    result
        .andExpect(status().isConflict())
        .andExpect(jsonPath("$.error").exists())
        .andExpect(jsonPath("$.error").value("PHONENUMBER_DUPLICATED"))
        .andExpect(jsonPath("$.message").exists())
        .andExpect(jsonPath("$.message").value("이미 사용중인 전화번호입니다."))
        .andDo(print());
  }

  @Test
  @DisplayName("회원가입 실패 - 디바이스 중복")
  void failToSignUpIfDeviceInvalid() throws Exception {

    // stub
    SignupRequestDto requestDto = signupRequestDto();

    // given
    given(userService.registerUser(any(SignupRequestDto.class)))
        .willThrow(new LoginAppException(LoginErrorCode.DEVICE_DUPLICATED));

    // when
    ResultActions result = mockMvc.perform(
        post("/signup")
            .with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsBytes(requestDto)));

    // result
    result
        .andExpect(status().isConflict())
        .andExpect(jsonPath("$.error").exists())
        .andExpect(jsonPath("$.error").value("DEVICE_DUPLICATED"))
        .andExpect(jsonPath("$.message").exists())
        .andExpect(jsonPath("$.message").value("해당 기기에서 사용중인 계정이 존재합니다."))
        .andDo(print());
  }


  public SignupRequestDto signupRequestDto(){
    SignupRequestDto requestDto = new SignupRequestDto();
    requestDto.setId("userId");
    requestDto.setName("userName");
    requestDto.setPassword("password");
    requestDto.setPhoneNumber("01012345678");
    requestDto.setFcmToken("fcmToken");

    return requestDto;
  }

  LoginDto loginDto(){
    LoginDto loginDto = new LoginDto();
    loginDto.setId("userId");
    loginDto.setPassword("password");

    return loginDto;
  }

    @Test
    void login() {
    }
}
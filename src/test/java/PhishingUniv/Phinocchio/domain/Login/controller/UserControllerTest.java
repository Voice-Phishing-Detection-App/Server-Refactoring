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
    void signup() throws Exception {
        // stub
         SignupRequestDto requestDto = new SignupRequestDto();
         requestDto.setId("userId3");
         requestDto.setName("userName3");
         requestDto.setPassword("password3");
         requestDto.setPhoneNumber("01033330000");
         requestDto.setFcmToken("fcmToken3");

        SignupResponseDto responseDto = new SignupResponseDto();
        responseDto.setId("userId3");

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
            .andExpect(jsonPath("$.id").value("userId3"))
            .andDo(print());
    }


  @Test
  @DisplayName("로그인 성공")
  void login_success() throws Exception {
      // stub
      LoginDto loginDto = new LoginDto();
      loginDto.setId("userId3");
      loginDto.setPassword("password3");

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
  @DisplayName("로그인 실패 - 올바르지 않은 사용자 id 입력")
  void login_fail_username() throws Exception {

      // stub
      LoginDto loginDto = new LoginDto();
      loginDto.setId("love");
      loginDto.setPassword("iloveyou");

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
    void login() {
    }
}
package PhishingUniv.Phinocchio.Login.controller;

import PhishingUniv.Phinocchio.domain.Login.dto.LoginDto;
import PhishingUniv.Phinocchio.domain.Login.dto.SignupRequestDto;
import PhishingUniv.Phinocchio.exception.Login.LoginAppException;
import PhishingUniv.Phinocchio.exception.Login.LoginErrorCode;
import PhishingUniv.Phinocchio.domain.Login.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.test.web.servlet.MockMvc;


import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
class UserControllerTest {

    @Autowired
    MockMvc mockMvc;


    @MockBean
    UserService userService;

    @Autowired
    ObjectMapper objectMapper;





    @Test
    @DisplayName("회원가입 성공")
    void signup() throws Exception {


         SignupRequestDto requestDto = new SignupRequestDto();
         requestDto.setId("userId");
         requestDto.setName("userName");
         requestDto.setPassword("password");
         requestDto.setPhoneNumber("010-0101-0101");

        mockMvc.perform(
                post("/signup")
                        .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(requestDto)))
                .andDo(print())
                .andExpect(status().isOk());



    }

    @Test
    @DisplayName("로그인 성공")
    @WithAnonymousUser
    void login_success() throws Exception {


        when(userService.login(any()))
                .thenReturn("token");


        LoginDto loginDto = new LoginDto();
        loginDto.setId("userId");
        loginDto.setPassword("password");



        mockMvc.perform(
                        post("/login")
                                .with(csrf())
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsBytes(loginDto)))
                .andDo(print())
                .andExpect(status().isOk());



    }

    @Test
    @DisplayName("로그인 실패 - username 없음")
    @WithAnonymousUser
    void login_fail_username() throws Exception {

        when(userService.login(any()))
                .thenThrow(new LoginAppException(LoginErrorCode.USERNAME_NOT_FOUND));



        LoginDto loginDto = new LoginDto();
        loginDto.setId("userId2");
        loginDto.setPassword("password");

        mockMvc.perform(
                        post("/login")
                                .with(csrf())
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsBytes(loginDto)))
                .andDo(print())
                .andExpect(status().isNotFound());


    }

    @Test
    @DisplayName("로그인 실패 - password 오류")
    @WithAnonymousUser
    void login_fail_password() throws Exception {
        when(userService.login(any()))
                .thenThrow(new LoginAppException(LoginErrorCode.INVALID_PASSWORD));


        LoginDto loginDto = new LoginDto();
        loginDto.setId("userId2");
        loginDto.setPassword("password");

        mockMvc.perform(
                        post("/login")
                                .with(csrf())
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsBytes(loginDto)))
                .andDo(print())
                .andExpect(status().isUnauthorized());

    }

    @Test
    @DisplayName("회원가입 실패 - 중복 회원")
    void signup_fail() throws Exception {


        SignupRequestDto requestDto = new SignupRequestDto();
        requestDto.setId("userId");
        requestDto.setName("userName");
        requestDto.setPassword("password");
        requestDto.setPhoneNumber("010-0101-0101");

        mockMvc.perform(
                        post("/signup")
                                .with(csrf())
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsBytes(requestDto)))
                .andDo(print())
                .andExpect(status().isConflict());



    }


    @Test
    void login() {
    }
}
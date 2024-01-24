package PhishingUniv.Phinocchio.domain.Login.service;

import PhishingUniv.Phinocchio.domain.Login.dto.LoginDto;
import PhishingUniv.Phinocchio.domain.Login.dto.SignupRequestDto;
import PhishingUniv.Phinocchio.domain.Login.dto.SignupResponseDto;
import PhishingUniv.Phinocchio.domain.Login.repository.UserRepository;
import PhishingUniv.Phinocchio.domain.Login.security.JwtGenerator;
import PhishingUniv.Phinocchio.domain.User.entity.UserEntity;
import PhishingUniv.Phinocchio.exception.Login.LoginAppException;
import PhishingUniv.Phinocchio.exception.Login.LoginErrorCode;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

  @InjectMocks
  private UserService userService;

  @Mock
  private AuthenticationManager authenticationManager;

  @Mock
  private UserRepository userRepository;

  @Mock
  private PasswordEncoder passwordEncoder;

  @Mock
  private JwtGenerator jwtGenerator;

  final String userId = "userId";
  final String password = "password";



  @Test
  @DisplayName("회원가입 성공")
  void registerUser_success() throws LoginAppException {
    // given
    SignupRequestDto requestDto = signupRequestDto();

    doReturn(new UserEntity(requestDto)).when(userRepository).save(any(UserEntity.class));


    // when
    SignupResponseDto responseDto = userService.registerUser(requestDto);

    // then
    assertThat(responseDto.getId()).isEqualTo(requestDto.getId());

    // verify
    verify(userRepository, times(1)).save(any(UserEntity.class));
    verify(passwordEncoder, times(1)).encode(any(String.class));

  }

  @Test
  @DisplayName("로그인 성공")
  void login_success() throws LoginAppException {
    // given

    LoginDto loginDto = loginDto();

    SignupRequestDto userDto = signupRequestDto();

    UserEntity userEntity = new UserEntity(userDto);

    when(userRepository.findById(userId)).thenReturn(Optional.of(userEntity));
    when(passwordEncoder.matches(password, userDto.getPassword())).thenReturn(true);

    Authentication authentication = mock(Authentication.class);
    when(authenticationManager.authenticate(any())).thenReturn(authentication);

    when(jwtGenerator.generateToken(authentication)).thenReturn("mockedToken");

    // when
    String token = userService.login(loginDto);

    // then
    assertEquals("mockedToken", token);

    // verify
    verify(userRepository, times(1)).findById(userId);
    verify(passwordEncoder, times(1)).matches(password, userDto.getPassword());
    verify(authenticationManager, times(1)).authenticate(any());
    verify(jwtGenerator, times(1)).generateToken(authentication);
  }



  SignupRequestDto signupRequestDto(){

    String encryptedPw = passwordEncoder.encode("password3");

    SignupRequestDto signupRequestDto = new SignupRequestDto();
    signupRequestDto.setId("userId");
    signupRequestDto.setName("userName");
    signupRequestDto.setPassword(encryptedPw);
    signupRequestDto.setPhoneNumber("01012340000");
    signupRequestDto.setFcmToken("fcmToken");

    return signupRequestDto;
  }

  LoginDto loginDto(){
    LoginDto loginDto = new LoginDto();
    loginDto.setId(userId);
    loginDto.setPassword(password);

    return loginDto;
  }


}

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
  void successToRegisterUser() throws LoginAppException {
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
  void successToLogin() throws LoginAppException {
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

  @Test
  @DisplayName("로그인 실패 - 사용자 아이디 불일치")
  void failToLoginIfUserIdInvalid() {
    // given
    LoginDto loginDto = loginDto();

    when(userRepository.findById(userId)).thenReturn(Optional.empty());

    // when & then
    assertThrows(LoginAppException.class, () -> userService.login(loginDto),
        LoginErrorCode.USERNAME_NOT_FOUND.getMessage());

    // verify
    verify(userRepository, times(1)).findById(userId);
    verify(passwordEncoder, times(0)).matches(any(), any());
    verify(authenticationManager, times(0)).authenticate(any());
    verify(jwtGenerator, times(0)).generateToken(any(Authentication.class));
  }

  @Test
  @DisplayName("로그인 실패 - 비밀번호 불일치")
  void failToLoginIfPasswordInvalid() {
    // given
    LoginDto loginDto = loginDto();

    SignupRequestDto userDto = signupRequestDto();

    UserEntity userEntity = new UserEntity(userDto);

    when(userRepository.findById(userId)).thenReturn(Optional.of(userEntity));
    when(passwordEncoder.matches(password, userDto.getPassword())).thenReturn(false);

    // when & then
    assertThrows(LoginAppException.class, () -> userService.login(loginDto),
        LoginErrorCode.INVALID_PASSWORD.getMessage());

    // verify
    verify(userRepository, times(1)).findById(userId);
    verify(passwordEncoder, times(1)).matches(password, userDto.getPassword());
    verify(authenticationManager, times(0)).authenticate(any());
    verify(jwtGenerator, times(0)).generateToken(any(Authentication.class));
  }

  @Test
  @DisplayName("회원가입 실패 - 사용자 아이디 중복")
  void failToSignUpIfUserIdDuplicate() throws LoginAppException {
    // given
    String userId = "userId";
    SignupRequestDto requestDto = signupRequestDto();

    when(userRepository.findById(userId)).thenReturn(Optional.of(new UserEntity()));

    // when & then
    assertThrows(LoginAppException.class, () -> userService.registerUser(requestDto),
        LoginErrorCode.USERNAME_DUPLICATED.getMessage());
  }

  @Test
  @DisplayName("회원가입 실패 - 전화번호 중복")
  void failToSignUpIfPhoneNumberDuplicate() throws LoginAppException {
    // given
    String phoneNumber = "01012340000";
    SignupRequestDto requestDto = signupRequestDto();

    when(userRepository.findByPhoneNumber(phoneNumber)).thenReturn(Optional.of(new UserEntity()));

    // when & then
    assertThrows(LoginAppException.class, () -> userService.registerUser(requestDto),
        LoginErrorCode.PHONENUMBER_DUPLICATED.getMessage());
  }

  @Test
  @DisplayName("회원가입 실패 - 디바이스 중복")
  void failToSignUpIfDeviceDuplicate() throws LoginAppException {
    // given
    String fcmToken = "fcmToken";
    SignupRequestDto requestDto = signupRequestDto();

    when(userRepository.findByFcmToken(fcmToken)).thenReturn(Optional.of(new UserEntity()));

    // when & then
    assertThrows(LoginAppException.class, () -> userService.registerUser(requestDto),
        LoginErrorCode.DEVICE_DUPLICATED.getMessage());
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

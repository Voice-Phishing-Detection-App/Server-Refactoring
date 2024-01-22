package PhishingUniv.Phinocchio.domain.Login.service;

import PhishingUniv.Phinocchio.domain.Login.dto.LoginDto;
import PhishingUniv.Phinocchio.domain.Login.dto.SignupRequestDto;
import PhishingUniv.Phinocchio.domain.Login.dto.SignupResponseDto;
import PhishingUniv.Phinocchio.domain.Login.repository.UserRepository;
import PhishingUniv.Phinocchio.domain.Login.security.JwtGenerator;
import PhishingUniv.Phinocchio.domain.User.entity.UserEntity;
import PhishingUniv.Phinocchio.exception.Login.LoginAppException;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
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

  @Test
  @DisplayName("회원가입 성공")
  void registerUser_success() throws LoginAppException {
    // given
    String encryptedPw = passwordEncoder.encode("password3");

    SignupRequestDto requestDto = new SignupRequestDto();
    requestDto.setId("userId3");
    requestDto.setName("userName3");
    requestDto.setPassword(encryptedPw);
    requestDto.setPhoneNumber("01033330000");
    requestDto.setFcmToken("fcmToken3");

    doReturn(new UserEntity(requestDto)).when(userRepository).save(any(UserEntity.class));


    // when
    SignupResponseDto responseDto = userService.registerUser(requestDto);

    // then
    assertThat(responseDto.getId()).isEqualTo(requestDto.getId());

    // verify
    verify(userRepository, times(1)).save(any(UserEntity.class));
    verify(passwordEncoder, times(1)).encode(any(String.class));

  }


}

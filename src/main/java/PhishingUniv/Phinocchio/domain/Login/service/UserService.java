package PhishingUniv.Phinocchio.domain.Login.service;

import PhishingUniv.Phinocchio.domain.Login.dto.LoginDto;
import PhishingUniv.Phinocchio.domain.Login.dto.SignupRequestDto;
import PhishingUniv.Phinocchio.domain.Login.dto.SignupResponseDto;
import PhishingUniv.Phinocchio.domain.Login.repository.UserRepository;
import PhishingUniv.Phinocchio.domain.Login.security.JwtGenerator;
import PhishingUniv.Phinocchio.domain.User.entity.UserEntity;
import PhishingUniv.Phinocchio.exception.Login.InvalidJwtException;
import PhishingUniv.Phinocchio.exception.Login.LoginAppException;
import PhishingUniv.Phinocchio.exception.Login.LoginErrorCode;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

  private final AuthenticationManager authenticationManager;
  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;

  private final JwtGenerator jwtGenerator;

  private void validateDuplicateUser(SignupRequestDto requestDto) throws LoginAppException {
    userRepository.findById(requestDto.getId())
        .ifPresent(m -> {
          throw new LoginAppException(LoginErrorCode.USERNAME_DUPLICATED);
        });

  }

  private void validateDuplicatePhoneNumber(SignupRequestDto requestDto)
      throws LoginAppException {
    userRepository.findByPhoneNumber(requestDto.getPhoneNumber())
        .ifPresent(m -> {
          throw new LoginAppException(LoginErrorCode.PHONENUMBER_DUPLICATED);
        });
  }

  private void validateDuplicateDevice(SignupRequestDto requestDto) throws LoginAppException {
    userRepository.findByFcmToken(requestDto.getFcmToken())
        .ifPresent(m -> {
          throw new LoginAppException(LoginErrorCode.DEVICE_DUPLICATED);
        });
  }

  public String login(LoginDto loginDto) {

    UserEntity user = userRepository.findById(loginDto.getId())
        .orElseThrow(() -> new LoginAppException((LoginErrorCode.USERNAME_NOT_FOUND)));
    if (passwordEncoder.matches(loginDto.getPassword(), user.getPassword())) {
      Authentication authentication = authenticationManager.authenticate(
          new UsernamePasswordAuthenticationToken(
              loginDto.getId(),
              loginDto.getPassword()));
      SecurityContextHolder.getContext().setAuthentication(authentication);
      return jwtGenerator.generateToken(authentication);
    } else {
      throw new LoginAppException(LoginErrorCode.INVALID_PASSWORD);
    }
  }



  public SignupResponseDto registerUser(SignupRequestDto requestDto) {
    //같은 id를 가지는 중복 회원 X
    validateDuplicateUser(requestDto);
    validateDuplicatePhoneNumber(requestDto);
    validateDuplicateDevice(requestDto);
    requestDto.setPassword(passwordEncoder.encode(requestDto.getPassword()));
    UserEntity user = new UserEntity(requestDto);
    userRepository.save(user);

    return new SignupResponseDto(user.getId());


  }

  public List<UserEntity> findUsers() {
    return userRepository.findAll();
  }

  public UserEntity getCurrentUser() {
    String currentId = getCurrentId();
    UserEntity currentUser = findUserById(currentId);
    return currentUser;
  }

  public String getCurrentId() {
    String currentId = SecurityContextHolder.getContext().getAuthentication().getName();
    return currentId;
  }

  public UserEntity findUserById(String id) throws InvalidJwtException {
    UserEntity user = userRepository.findById(id).orElseThrow(
        () -> new InvalidJwtException(LoginErrorCode.JWT_USER_NOT_FOUND));
    return user;
  }
}

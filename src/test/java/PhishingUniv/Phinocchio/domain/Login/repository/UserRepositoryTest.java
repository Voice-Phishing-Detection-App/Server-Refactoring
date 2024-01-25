package PhishingUniv.Phinocchio.domain.Login.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import PhishingUniv.Phinocchio.domain.Login.dto.SignupRequestDto;
import PhishingUniv.Phinocchio.domain.User.entity.UserEntity;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.DirtiesContext;

@DataJpaTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class UserRepositoryTest {

  @Autowired
  private UserRepository userRepository;

  @Test
  @DisplayName("아이디로 사용자 엔티티 조회")
  public void testFindById() {
    // given
    UserEntity user = new UserEntity(signupRequestDto());
    userRepository.save(user);

    // when
    Optional<UserEntity> foundUser = userRepository.findById("userId");

    // then
    assertTrue(foundUser.isPresent());
    assertEquals("userId", foundUser.get().getId());
  }

  @Test
  @DisplayName("전화번호로 사용자 엔티티 조회")
  public void testFindByPhoneNumber() {
    // given
    UserEntity user = new UserEntity(signupRequestDto());
    userRepository.save(user);

    // when
    Optional<UserEntity> foundUser = userRepository.findByPhoneNumber("01012340000");

    // then
    assertTrue(foundUser.isPresent());
    assertEquals("01012340000", foundUser.get().getPhoneNumber());
  }


  SignupRequestDto signupRequestDto(){

    SignupRequestDto signupRequestDto = new SignupRequestDto();
    signupRequestDto.setId("userId");
    signupRequestDto.setName("userName");
    signupRequestDto.setPassword("password");
    signupRequestDto.setPhoneNumber("01012340000");
    signupRequestDto.setFcmToken("fcmToken");

    return signupRequestDto;
  }

}

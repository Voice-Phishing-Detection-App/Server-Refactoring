package PhishingUniv.Phinocchio.domain.Login.repository;

import PhishingUniv.Phinocchio.domain.User.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity,Long> {

    //Id : 실제 로그인시 사용하는 아이디
    Optional<UserEntity> findById(String id);

    Optional<UserEntity> findByFcmToken(String fcmToken);

    Optional<UserEntity> findByPhoneNumber(String phoneNumber);

    List<UserEntity> findAll();


}

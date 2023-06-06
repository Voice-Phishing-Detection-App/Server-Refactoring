package PhishingUniv.Phinocchio.domain.Login.repository;

import PhishingUniv.Phinocchio.domain.User.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity,Long> {


    Optional<UserEntity> findById(String id);
    Optional<UserEntity> findByName(String name);

    List<UserEntity> findAll();


}

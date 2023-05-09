package PhishingUniv.Phinocchio.Login.repository;

import PhishingUniv.Phinocchio.Login.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Long> {


    Optional<User> findById(String id);
    Optional<User> findByName(String name);

    List<User> findAll();


}

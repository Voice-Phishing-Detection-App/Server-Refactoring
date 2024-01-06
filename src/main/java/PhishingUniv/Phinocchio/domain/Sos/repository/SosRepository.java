package PhishingUniv.Phinocchio.domain.Sos.repository;

import PhishingUniv.Phinocchio.domain.Sos.entity.SosEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface SosRepository extends JpaRepository<SosEntity, Long> {

    SosEntity save(SosEntity sosEntity);

    Optional<SosEntity> findBySosId(Long sosId);

    void delete(SosEntity sosEntity);

    List<SosEntity> findByUserIdAndLevelGreaterThanEqual(Long userId, int level);

    List<SosEntity> findByUserId(Long userId);

}

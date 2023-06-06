package PhishingUniv.Phinocchio.domain.Doubt.repository;

import PhishingUniv.Phinocchio.domain.Doubt.entity.DoubtEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DoubtRepository extends JpaRepository<DoubtEntity, Long> {

    DoubtEntity save(DoubtEntity doubtEntity);

}

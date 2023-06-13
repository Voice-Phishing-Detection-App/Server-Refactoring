package PhishingUniv.Phinocchio.domain.Doubt.repository;

import PhishingUniv.Phinocchio.domain.Doubt.entity.DoubtEntity;
import PhishingUniv.Phinocchio.domain.Report.entity.ReportEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DoubtRepository extends JpaRepository<DoubtEntity, Long> {

    DoubtEntity save(DoubtEntity doubtEntity);
    List<DoubtEntity> findDoubtEntitiesByUserId(Long userid);
}

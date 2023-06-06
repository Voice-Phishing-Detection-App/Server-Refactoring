package PhishingUniv.Phinocchio.domain.Report.repository;

import PhishingUniv.Phinocchio.domain.Report.entity.ReportEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ReportRepository extends JpaRepository<ReportEntity, Long> {
    Optional<ReportEntity> findByUserId(Long id);

}

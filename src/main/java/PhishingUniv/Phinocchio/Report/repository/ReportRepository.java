package PhishingUniv.Phinocchio.Report.repository;

import PhishingUniv.Phinocchio.Report.entity.ReportEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ReportRepository extends JpaRepository<ReportEntity, Long> {
    Optional<ReportEntity> findByUserId(Long id);

}

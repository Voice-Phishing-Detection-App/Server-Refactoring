package PhishingUniv.Phinocchio.domain.Report.repository;

import PhishingUniv.Phinocchio.domain.Report.entity.ReportEntity;
import PhishingUniv.Phinocchio.domain.User.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ReportRepository extends JpaRepository<ReportEntity, Long> {

    ReportEntity save(ReportEntity reportEntity);

    Optional<ReportEntity> findByUser(UserEntity user);
    List<ReportEntity> findReportEntitiesByUser(UserEntity user);


    List<ReportEntity> findReportEntitiesByPhoneNumber(String phoneNumber);
    Long countByPhoneNumber(String phoneNumber);
}

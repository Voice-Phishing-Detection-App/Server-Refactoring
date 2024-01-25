package PhishingUniv.Phinocchio.domain.Report.service;

import PhishingUniv.Phinocchio.domain.Login.service.UserService;
import PhishingUniv.Phinocchio.domain.Report.dto.SearchResponseDto;
import PhishingUniv.Phinocchio.domain.Report.entity.ReportEntity;
import PhishingUniv.Phinocchio.domain.Report.entity.ReportType;
import PhishingUniv.Phinocchio.domain.Report.repository.ReportRepository;
import PhishingUniv.Phinocchio.exception.Login.InvalidJwtException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class SearchService {

    private final ReportRepository reportRepository;
    private final UserService userService;

    public SearchResponseDto getReportCount(String phoneNumber)throws InvalidJwtException{
        ensureUserAuthenticated();

        List<ReportEntity> reportList = findReportListByPhoneNumber(phoneNumber);
        List<ReportType> reportTypes = findReportTypes(reportList);
        Long reportCount = getCountByPhoneNumber(phoneNumber);

        if(reportCount == 0){
            reportTypes.add(ReportType.REPORT_TYPE_NONE);
        }

        return new SearchResponseDto(phoneNumber, reportCount, reportTypes);
    }

    public List<ReportEntity> getReportDetail(String phoneNumber)throws InvalidJwtException{
        ensureUserAuthenticated();

        return findReportListByPhoneNumber(phoneNumber);
    }

    private void ensureUserAuthenticated() throws InvalidJwtException {
        userService.getCurrentUser();
    }

    private List<ReportEntity> findReportListByPhoneNumber(String phoneNumber){
        return reportRepository.findReportEntitiesByPhoneNumber(phoneNumber);
    }

    private List<ReportType> findReportTypes(List<ReportEntity> reportList){
        return reportList.stream()
            .map(ReportEntity::getType)
            .distinct()
            .collect(Collectors.toList());
    }

    private Long getCountByPhoneNumber(String phoneNumber){
        return reportRepository.countByPhoneNumber(phoneNumber);
    }
}

package PhishingUniv.Phinocchio.domain.Report.service;

import PhishingUniv.Phinocchio.domain.Login.service.UserService;
import PhishingUniv.Phinocchio.domain.Report.dto.SearchResponseDto;
import PhishingUniv.Phinocchio.domain.Report.entity.ReportEntity;
import PhishingUniv.Phinocchio.domain.Report.entity.ReportType;
import PhishingUniv.Phinocchio.domain.Report.repository.ReportRepository;
import PhishingUniv.Phinocchio.exception.Login.InvalidJwtException;
import PhishingUniv.Phinocchio.exception.Report.ReportAppException;
import PhishingUniv.Phinocchio.exception.Report.ReportErrorCode;
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

        List<ReportEntity> reportList = reportRepository.findReportEntitiesByPhoneNumber(phoneNumber);
        List<ReportType> reportTypes = findReportTypes(reportList);
        Long reportCount = reportRepository.countByPhoneNumber(phoneNumber);

        if(reportCount == 0){
            reportTypes.add(ReportType.REPORT_TYPE_NONE);
        }

        return new SearchResponseDto(phoneNumber, reportCount, reportTypes);
    }

    public List<ReportEntity> getReportDetail(String phoneNumber)throws InvalidJwtException, ReportAppException{
        ensureUserAuthenticated();

        List<ReportEntity> reports = reportRepository.findReportEntitiesByPhoneNumber(phoneNumber);

        if(reports.isEmpty()){
            throw new ReportAppException(ReportErrorCode.REPORT_NOT_FOUND);
        }

        return reports;
    }

    private void ensureUserAuthenticated() throws InvalidJwtException {
        userService.getCurrentUser();
    }

    private List<ReportType> findReportTypes(List<ReportEntity> reportList){
        return reportList.stream()
            .map(ReportEntity::getType)
            .distinct()
            .collect(Collectors.toList());
    }

}

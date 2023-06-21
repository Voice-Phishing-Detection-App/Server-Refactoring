package PhishingUniv.Phinocchio.domain.Report.service;

import PhishingUniv.Phinocchio.domain.Report.dto.SearchResponseDto;
import PhishingUniv.Phinocchio.domain.Report.entity.ReportEntity;
import PhishingUniv.Phinocchio.domain.Report.entity.ReportType;
import PhishingUniv.Phinocchio.domain.Report.repository.ReportRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class SearchService {

    private final ReportRepository reportRepository;

    public SearchResponseDto getReportCount(String phoneNumber){
        List<ReportEntity> reportList = reportRepository.findReportEntitiesByPhoneNumber(phoneNumber);
        List<ReportType> reportTypes = reportList.stream()
                .map(ReportEntity::getType)
                .distinct()
                .collect(Collectors.toList());

        Long reportCount = reportRepository.countByPhoneNumber(phoneNumber);
        if(reportCount == 0){
            reportTypes.add(ReportType.REPORT_TYPE_NONE);
        }
        return new SearchResponseDto(phoneNumber, reportCount, reportTypes);
    }

    public List<ReportEntity> getReportDetail(String phoneNumber){
        return reportRepository.findReportEntitiesByPhoneNumber(phoneNumber);
    }
}

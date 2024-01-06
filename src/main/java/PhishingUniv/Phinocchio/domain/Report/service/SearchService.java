package PhishingUniv.Phinocchio.domain.Report.service;

import PhishingUniv.Phinocchio.domain.Login.repository.UserRepository;
import PhishingUniv.Phinocchio.domain.Report.dto.SearchResponseDto;
import PhishingUniv.Phinocchio.domain.Report.entity.ReportEntity;
import PhishingUniv.Phinocchio.domain.Report.entity.ReportType;
import PhishingUniv.Phinocchio.domain.Report.repository.ReportRepository;
import PhishingUniv.Phinocchio.domain.User.entity.UserEntity;
import PhishingUniv.Phinocchio.exception.Login.InvalidJwtException;
import PhishingUniv.Phinocchio.exception.Login.LoginErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class SearchService {

    private final ReportRepository reportRepository;
    private final UserRepository userRepository;

    public SearchResponseDto getReportCount(String phoneNumber)throws InvalidJwtException{
        String ID = SecurityContextHolder.getContext().getAuthentication().getName();
        UserEntity userEntity =userRepository.findById(ID).orElseThrow(
                ()->new InvalidJwtException(LoginErrorCode.JWT_USER_NOT_FOUND));

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

    public List<ReportEntity> getReportDetail(String phoneNumber)throws InvalidJwtException{
        String ID = SecurityContextHolder.getContext().getAuthentication().getName();
        UserEntity userEntity =userRepository.findById(ID).orElseThrow(
                ()->new InvalidJwtException(LoginErrorCode.JWT_USER_NOT_FOUND));

        return reportRepository.findReportEntitiesByPhoneNumber(phoneNumber);
    }
}

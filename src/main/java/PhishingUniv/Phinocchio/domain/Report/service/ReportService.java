package PhishingUniv.Phinocchio.domain.Report.service;


import PhishingUniv.Phinocchio.domain.Login.repository.UserRepository;
import PhishingUniv.Phinocchio.domain.Login.security.UserDetailsImpl;
import PhishingUniv.Phinocchio.domain.Report.dto.ReportDto;
import PhishingUniv.Phinocchio.domain.Report.entity.ReportEntity;
import PhishingUniv.Phinocchio.domain.Report.repository.ReportRepository;
import PhishingUniv.Phinocchio.domain.User.entity.UserEntity;
import PhishingUniv.Phinocchio.exception.Login.InvalidJwtException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@RequiredArgsConstructor
@Service
public class ReportService {

    private final ReportRepository reportRepository;

    //userId가져오기 위해서 임시로 만들어둔 것, 캐싱으로 나중에 수정해야하는 부분
    private final UserRepository userRepository;
    public ReportEntity addReport(ReportDto reportDto)
    {
        String ID = SecurityContextHolder.getContext().getAuthentication().getName();
        UserEntity userEntity =userRepository.findById(ID).orElseThrow(
                ()->new InvalidJwtException("addReport, User를 찾을 수 없음"));

        Long userId = userEntity.getUserId();

        ReportEntity reportEntity = new ReportEntity(reportDto.getType(), reportDto.getContent()
                                                ,reportDto.getPhoneNumber(),userId,reportDto.getVoiceId());

        reportRepository.save(reportEntity);
        return reportEntity;
    }

    public List<ReportEntity> getReports()
    {
        String ID = SecurityContextHolder.getContext().getAuthentication().getName();
        UserEntity userEntity =userRepository.findById(ID).orElseThrow(
                ()->new InvalidJwtException("addReport, User를 찾을 수 없음"));

        Long userId = userEntity.getUserId();
        List<ReportEntity> reportEntities = reportRepository.findReportEntitiesByUserId(userId);

        return reportEntities;


    }

}

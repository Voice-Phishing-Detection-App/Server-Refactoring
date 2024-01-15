package PhishingUniv.Phinocchio.domain.Report.service;


import PhishingUniv.Phinocchio.domain.Doubt.entity.DoubtEntity;
import PhishingUniv.Phinocchio.domain.Doubt.repository.DoubtRepository;
import PhishingUniv.Phinocchio.domain.Login.repository.UserRepository;
import PhishingUniv.Phinocchio.domain.Report.dto.ReportDto;
import PhishingUniv.Phinocchio.domain.Report.dto.ReportWithoutDoubtDto;
import PhishingUniv.Phinocchio.domain.Report.entity.ReportEntity;
import PhishingUniv.Phinocchio.domain.Report.repository.ReportRepository;
import PhishingUniv.Phinocchio.domain.User.entity.UserEntity;
import PhishingUniv.Phinocchio.domain.Voice.entity.VoiceEntity;
import PhishingUniv.Phinocchio.domain.Voice.repository.VoiceRepository;
import PhishingUniv.Phinocchio.exception.Doubt.DoubtAppException;
import PhishingUniv.Phinocchio.exception.Doubt.DoubtErrorCode;
import PhishingUniv.Phinocchio.exception.Login.InvalidJwtException;
import PhishingUniv.Phinocchio.exception.Login.LoginErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class ReportService {
    private final DoubtRepository doubtRepository;
    private final ReportRepository reportRepository;
    private final VoiceRepository voiceRepository;

    //userId 가져오기 위해서 임시로 만들어둔 것, 캐싱으로 나중에 수정해야하는 부분
    private final UserRepository userRepository;
    public ReportEntity addReport(ReportDto reportDto) {
        String ID = SecurityContextHolder.getContext().getAuthentication().getName();
        UserEntity userEntity =userRepository.findById(ID).orElseThrow(
                ()->new InvalidJwtException(LoginErrorCode.JWT_USER_NOT_FOUND));

        Optional<VoiceEntity> optionalVoiceEntity = voiceRepository.findByVoiceId(reportDto.getVoiceId());
        VoiceEntity voice = optionalVoiceEntity.orElseThrow(() -> new RuntimeException("해당 voice table이 존재하지 않습니다."));

        ReportEntity reportEntity = new ReportEntity(reportDto.getType(), reportDto.getTitle(), reportDto.getContent()
                                                ,reportDto.getPhoneNumber(),userEntity, voice);

        reportRepository.save(reportEntity);

        DoubtEntity doubtEntity = doubtRepository.findById(reportDto.getDoubtId()).orElseThrow(() -> new DoubtAppException(
                DoubtErrorCode.DOUBT_NOT_FOUND));
        doubtEntity.setReport(reportEntity);
        doubtRepository.save(doubtEntity);

        return reportEntity;
    }

    public ReportEntity addReportWithoutDoubt(ReportWithoutDoubtDto reportDto)throws InvalidJwtException
    {
        String ID = SecurityContextHolder.getContext().getAuthentication().getName();
        UserEntity userEntity =userRepository.findById(ID).orElseThrow(
                ()->new InvalidJwtException(LoginErrorCode.JWT_USER_NOT_FOUND));

        ReportEntity reportEntity = ReportEntity.builder()
                .type(reportDto.getType())
                .title(reportDto.getTitle())
                .content(reportDto.getContent())
                .phoneNumber(reportDto.getPhoneNumber())
                .user(userEntity).build();

        return reportRepository.save(reportEntity);
    }

    public List<ReportEntity> getReports() throws InvalidJwtException
    {
        String ID = SecurityContextHolder.getContext().getAuthentication().getName();
        UserEntity userEntity =userRepository.findById(ID).orElseThrow(
                ()->new InvalidJwtException(LoginErrorCode.JWT_USER_NOT_FOUND));

        List<ReportEntity> reportEntities = reportRepository.findReportEntitiesByUser(userEntity);

        return reportEntities;


    }

}

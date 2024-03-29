package PhishingUniv.Phinocchio.domain.Report.service;


import PhishingUniv.Phinocchio.domain.Doubt.entity.DoubtEntity;
import PhishingUniv.Phinocchio.domain.Doubt.service.DoubtService;
import PhishingUniv.Phinocchio.domain.Login.service.UserService;
import PhishingUniv.Phinocchio.domain.Report.dto.ReportDto;
import PhishingUniv.Phinocchio.domain.Report.dto.ReportWithoutDoubtDto;
import PhishingUniv.Phinocchio.domain.Report.entity.ReportEntity;
import PhishingUniv.Phinocchio.domain.Report.repository.ReportRepository;
import PhishingUniv.Phinocchio.domain.User.entity.UserEntity;
import PhishingUniv.Phinocchio.domain.Voice.entity.VoiceEntity;
import PhishingUniv.Phinocchio.domain.Voice.service.VoiceService;
import PhishingUniv.Phinocchio.exception.Doubt.DoubtAppException;
import PhishingUniv.Phinocchio.exception.Login.InvalidJwtException;
import PhishingUniv.Phinocchio.exception.Report.ReportAppException;
import PhishingUniv.Phinocchio.exception.Report.ReportErrorCode;
import PhishingUniv.Phinocchio.exception.Voice.VoiceAppException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class ReportService {
    private final DoubtService doubtService;
    private final ReportRepository reportRepository;
    private final VoiceService voiceService;
    private final UserService userService;

    public ReportEntity addReport(ReportDto reportDto) throws InvalidJwtException, DoubtAppException, VoiceAppException, ReportAppException {
        UserEntity userEntity = ensureUserAuthenticated();
        VoiceEntity voiceEntity = voiceService.findByVoiceId(reportDto.getVoiceId());
        ReportEntity reportEntity = createReportEntity(reportDto, userEntity, voiceEntity);

        ReportEntity savedReport = reportRepository.save(reportEntity);
        if(savedReport == null){
            throw new ReportAppException(ReportErrorCode.FAILED_TO_SAVE);
        }

        updateDoubtEntity(reportDto, reportEntity);

        return reportEntity;
    }

    public ReportEntity addReportWithoutDoubt(ReportWithoutDoubtDto reportDto)throws InvalidJwtException {
        UserEntity userEntity = ensureUserAuthenticated();
        ReportEntity reportEntity = createReportWithoutDoubtEntity(reportDto, userEntity);

        ReportEntity savedReport = reportRepository.save(reportEntity);
        if(savedReport == null){
            throw new ReportAppException(ReportErrorCode.FAILED_TO_SAVE);
        }

        return reportEntity;
    }

    public List<ReportEntity> getReports() throws InvalidJwtException, ReportAppException{
        UserEntity userEntity = ensureUserAuthenticated();

        List<ReportEntity> reports =  reportRepository.findReportEntitiesByUser(userEntity);

        if (reports.isEmpty()) {
            throw new ReportAppException(ReportErrorCode.REPORT_NOT_FOUND);
        }

        return reports;
    }


    private UserEntity ensureUserAuthenticated() throws InvalidJwtException {
        return userService.getCurrentUser();
    }

    private void updateDoubtEntity(ReportDto reportDto, ReportEntity reportEntity){
        DoubtEntity doubtEntity = doubtService.findByDoubtId(reportDto.getDoubtId());
        doubtEntity.setReport(reportEntity);
        doubtService.save(doubtEntity);
    }

    private ReportEntity createReportEntity(ReportDto reportDto, UserEntity userEntity, VoiceEntity voiceEntity) {
        return ReportEntity.builder()
            .type(reportDto.getType())
            .title(reportDto.getTitle())
            .content(reportDto.getContent())
            .phoneNumber(reportDto.getPhoneNumber())
            .user(userEntity)
            .voice(voiceEntity).build();
    }

    private ReportEntity createReportWithoutDoubtEntity(ReportWithoutDoubtDto reportDto, UserEntity userEntity){
        return ReportEntity.builder()
            .type(reportDto.getType())
            .title(reportDto.getTitle())
            .content(reportDto.getContent())
            .phoneNumber(reportDto.getPhoneNumber())
            .user(userEntity).build();
    }

}

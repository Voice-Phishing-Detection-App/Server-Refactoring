package PhishingUniv.Phinocchio.domain.Report.dto;


import PhishingUniv.Phinocchio.domain.Report.entity.ReportType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReportDto {

    private ReportType type;

    private String content;

    private String phoneNumber;

    private Long voiceId;

    private Long doubtId;
}


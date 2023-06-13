package PhishingUniv.Phinocchio.domain.Report.dto;

import PhishingUniv.Phinocchio.domain.Report.entity.ReportType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReportWithoutDoubtDto {
    private ReportType type;

    private String content;

    private String title;

    private String phoneNumber;


}

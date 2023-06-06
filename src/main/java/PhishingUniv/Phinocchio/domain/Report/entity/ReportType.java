package PhishingUniv.Phinocchio.domain.Report.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ReportType {

    REPORT_TYPE_FRAUD(0,"사기"),
    REPORT_TYPE_IMPERSONATING(1,"사칭"),
    REPORT_TYPE_INDUCE(2,"설치유도"),
    REPORT_TYPE_DISGUISE(3,"사고빙자");

    private Integer Code;
    private String reportType;
}

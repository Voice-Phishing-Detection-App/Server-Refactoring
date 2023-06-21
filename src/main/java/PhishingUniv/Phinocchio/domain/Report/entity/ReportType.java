package PhishingUniv.Phinocchio.domain.Report.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ReportType {

    REPORT_TYPE_NONE(0, "신고 내역 없음"),
    REPORT_TYPE_FRAUD(1,"사기"),
    REPORT_TYPE_IMPERSONATING(2,"사칭"),
    REPORT_TYPE_INDUCE(3,"설치유도"),
    REPORT_TYPE_DISGUISE(4,"사고빙자");

    private Integer Code;
    private String reportType;
}

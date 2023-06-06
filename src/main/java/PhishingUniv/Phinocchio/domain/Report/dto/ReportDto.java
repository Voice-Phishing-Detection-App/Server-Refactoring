package PhishingUniv.Phinocchio.domain.Report.dto;


import lombok.Getter;

@Getter
public class ReportDto {

    private String type;

    private String content;

    private String phoneNumber;

    private Long voiceId;
}


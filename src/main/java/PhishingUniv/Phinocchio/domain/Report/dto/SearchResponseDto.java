package PhishingUniv.Phinocchio.domain.Report.dto;

import PhishingUniv.Phinocchio.domain.Report.entity.ReportType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SearchResponseDto {

    private String phoneNumber;

    private Long reportCount;

    private List<ReportType> type;
}

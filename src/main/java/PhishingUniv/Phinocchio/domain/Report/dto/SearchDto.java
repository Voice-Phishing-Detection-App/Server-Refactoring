package PhishingUniv.Phinocchio.domain.Report.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SearchDto {
    private String phoneNumber;

    private List<String> type;

    private List<String> content;
}

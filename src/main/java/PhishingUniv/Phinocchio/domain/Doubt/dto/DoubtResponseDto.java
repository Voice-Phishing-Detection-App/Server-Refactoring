package PhishingUniv.Phinocchio.domain.Doubt.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DoubtResponseDto {

  private int level;

  private Boolean phishing;

}

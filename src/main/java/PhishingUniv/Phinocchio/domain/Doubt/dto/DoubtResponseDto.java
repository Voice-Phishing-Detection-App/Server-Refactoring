package PhishingUniv.Phinocchio.domain.Doubt.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class DoubtResponseDto {

  private int level;

  private Boolean phishing;

}

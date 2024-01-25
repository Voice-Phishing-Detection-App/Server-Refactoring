package PhishingUniv.Phinocchio.domain.Doubt.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class AnalyzedVoicePhishingDto {

  private String phoneNumber;
  private String text;

  private int level;

  private Boolean phishing;

}

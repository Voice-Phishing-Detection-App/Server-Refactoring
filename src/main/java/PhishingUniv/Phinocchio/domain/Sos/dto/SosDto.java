package PhishingUniv.Phinocchio.domain.Sos.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class SosDto {

    private Long userId;

    private String phoneNumber;

    private String relation;

    private int level;

}

package PhishingUniv.Phinocchio.domain.Sos.dto;

import lombok.Getter;

@Getter
public class SosUpdateDto {

    private Long sosId;

    private Long userId;

    private String phoneNumber;

    private String relation;

    private int level;

}

package PhishingUniv.Phinocchio.domain.Doubt.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class DoubtRequestDto {
    private String phoneNumber;

    private String text;

}

package PhishingUniv.Phinocchio.domain.Sos.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class MessageDTO {
    private  String to;
    private String content;
}

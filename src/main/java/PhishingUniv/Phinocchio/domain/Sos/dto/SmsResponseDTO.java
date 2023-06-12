package PhishingUniv.Phinocchio.domain.Sos.dto;

import lombok.*;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class SmsResponseDTO {
    private String requestId;
    private LocalDateTime requestTime;
    private String statusCode;
    private String statusName;
}

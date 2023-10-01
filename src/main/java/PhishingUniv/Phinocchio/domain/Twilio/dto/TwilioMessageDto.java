package PhishingUniv.Phinocchio.domain.Twilio.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TwilioMessageDto {

    private String AccountSid;
    private String CallSid;
    private String CallToken;
    private String To;

}

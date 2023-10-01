package PhishingUniv.Phinocchio.domain.Twilio.dto;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TwilioTokenDto {

    private String twilioToken;

    public TwilioTokenDto (String twilioToken) {
        this.twilioToken = twilioToken;
    }

}

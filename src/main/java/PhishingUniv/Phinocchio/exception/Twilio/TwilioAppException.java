package PhishingUniv.Phinocchio.exception.Twilio;


import PhishingUniv.Phinocchio.exception.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class TwilioAppException extends RuntimeException{
    private final ErrorCode errorCode;
}

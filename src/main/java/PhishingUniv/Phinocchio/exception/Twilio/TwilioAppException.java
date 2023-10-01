package PhishingUniv.Phinocchio.exception.Twilio;


import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class TwilioAppException extends RuntimeException{
    private TwilioErrorCode errorCode;
    private String message;


}

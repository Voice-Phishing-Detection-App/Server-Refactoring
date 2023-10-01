package PhishingUniv.Phinocchio.exception.Twilio;


import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
public enum TwilioErrorCode {
    TWILIO_ERROR(HttpStatus.CONFLICT, "");

    private HttpStatus httpStatus;
    private String message;


}

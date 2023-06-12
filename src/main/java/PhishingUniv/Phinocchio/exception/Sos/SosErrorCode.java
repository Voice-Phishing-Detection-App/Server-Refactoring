package PhishingUniv.Phinocchio.exception.Sos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;


@AllArgsConstructor
@Getter
public enum SosErrorCode {

    SOS_NOT_FOUND(HttpStatus.NOT_FOUND, ""),

    FAILED_TO_SEND_SMS(HttpStatus.INTERNAL_SERVER_ERROR, "");

    private HttpStatus httpStatus;
    private String message;
}

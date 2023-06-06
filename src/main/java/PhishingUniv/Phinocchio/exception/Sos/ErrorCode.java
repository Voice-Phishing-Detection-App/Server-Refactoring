package PhishingUniv.Phinocchio.exception.Sos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;


@AllArgsConstructor
@Getter
public enum ErrorCode {
    SOS_NOT_FOUND(HttpStatus.CONFLICT, "");

    private HttpStatus httpStatus;
    private String message;
}
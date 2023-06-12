package PhishingUniv.Phinocchio.exception.Voice;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;


@AllArgsConstructor
@Getter
public enum VoiceErrorCode {

    FAILED_TO_SAVE(HttpStatus.INTERNAL_SERVER_ERROR, "");

    private HttpStatus httpStatus;
    private String message;
}

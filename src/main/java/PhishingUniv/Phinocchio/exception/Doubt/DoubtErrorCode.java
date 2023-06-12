package PhishingUniv.Phinocchio.exception.Doubt;


import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
public enum DoubtErrorCode {

    FAILED_TO_SAVE(HttpStatus.INTERNAL_SERVER_ERROR, ""),

    DISCONNCECTED_TO_MLSERVER(HttpStatus.INTERNAL_SERVER_ERROR, "");

    private HttpStatus httpStatus;
    private String message;


}

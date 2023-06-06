package PhishingUniv.Phinocchio.exception.Sos;

import PhishingUniv.Phinocchio.exception.Sos.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class AppException extends RuntimeException{

    private ErrorCode errorCode;
    private String message;

}

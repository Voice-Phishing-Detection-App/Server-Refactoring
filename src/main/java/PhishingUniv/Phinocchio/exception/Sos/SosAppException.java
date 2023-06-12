package PhishingUniv.Phinocchio.exception.Sos;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class SosAppException extends RuntimeException{

    private SosErrorCode errorCode;
    private String message;

}

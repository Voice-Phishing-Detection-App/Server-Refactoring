package PhishingUniv.Phinocchio.exception.Sos;

import PhishingUniv.Phinocchio.exception.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class SosAppException extends RuntimeException{
    private final ErrorCode errorCode;
}

package PhishingUniv.Phinocchio.exception.GoogleSTT;

import PhishingUniv.Phinocchio.exception.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class STTAppException extends RuntimeException {

    private final ErrorCode errorCode;

}

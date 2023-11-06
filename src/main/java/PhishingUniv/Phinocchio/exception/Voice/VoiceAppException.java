package PhishingUniv.Phinocchio.exception.Voice;

import PhishingUniv.Phinocchio.exception.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class VoiceAppException extends RuntimeException{
    private final ErrorCode errorCode;
}

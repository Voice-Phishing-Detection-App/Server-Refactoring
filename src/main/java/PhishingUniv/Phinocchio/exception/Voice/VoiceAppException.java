package PhishingUniv.Phinocchio.exception.Voice;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class VoiceAppException extends RuntimeException{

    private VoiceErrorCode errorCode;
    private String message;

}

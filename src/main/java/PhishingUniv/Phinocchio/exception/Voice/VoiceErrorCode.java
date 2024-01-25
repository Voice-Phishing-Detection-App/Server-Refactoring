package PhishingUniv.Phinocchio.exception.Voice;

import PhishingUniv.Phinocchio.exception.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;


@AllArgsConstructor
@Getter
public enum VoiceErrorCode implements ErrorCode {

    FAILED_TO_SAVE(HttpStatus.INTERNAL_SERVER_ERROR, "목소리 저장에 실패하였습니다."),
    VOICE_NOT_FOUND(HttpStatus.NOT_FOUND, "존재하지 않는 목소리 정보 입니다.");

    private HttpStatus httpStatus;
    private String message;
}

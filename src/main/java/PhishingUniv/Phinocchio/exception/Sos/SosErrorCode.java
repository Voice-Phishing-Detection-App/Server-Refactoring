package PhishingUniv.Phinocchio.exception.Sos;

import PhishingUniv.Phinocchio.exception.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;


@AllArgsConstructor
@Getter
public enum SosErrorCode implements ErrorCode {

    SOS_NOT_FOUND(HttpStatus.NOT_FOUND, "존재하지 않는 긴급연락처입니다."),

    FAILED_TO_SEND_SMS(HttpStatus.INTERNAL_SERVER_ERROR, "메세지 전송 실패");

    private HttpStatus httpStatus;
    private String message;
}

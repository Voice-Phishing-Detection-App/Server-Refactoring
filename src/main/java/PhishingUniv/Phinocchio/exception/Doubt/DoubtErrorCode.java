package PhishingUniv.Phinocchio.exception.Doubt;


import PhishingUniv.Phinocchio.exception.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
public enum DoubtErrorCode implements ErrorCode {

    DOUBT_NOT_FOUND(HttpStatus.NOT_FOUND, "존재하지 않는 의심 내역입니다."),

    FAILED_TO_SAVE(HttpStatus.INTERNAL_SERVER_ERROR, "의심내역 저장에 실패하였습니다."),

    DISCONNCECTED_TO_MLSERVER(HttpStatus.INTERNAL_SERVER_ERROR, "머신러닝 서버와 연결이 되지 않습니다.");

    private final HttpStatus httpStatus;
    private final String message;


}

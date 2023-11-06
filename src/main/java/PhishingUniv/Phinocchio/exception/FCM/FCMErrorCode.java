package PhishingUniv.Phinocchio.exception.FCM;


import PhishingUniv.Phinocchio.exception.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum FCMErrorCode implements ErrorCode {
    FCM_ERROR(HttpStatus.CONFLICT, "FCM 관련 오류입니다."),
    FCM_NOT_FOUND(HttpStatus.NOT_FOUND, "사용자의 fcm token과 일치하지 않습니다.");

    private final HttpStatus httpStatus;
    private final String message;

}

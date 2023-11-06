package PhishingUniv.Phinocchio.exception.Twilio;


import PhishingUniv.Phinocchio.exception.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum TwilioErrorCode implements ErrorCode {
    TWILIO_WEBHOOK_ERROR(HttpStatus.CONFLICT, "Twilio webhook 관련 오류입니다."),
    TWILIO_PHONE_ERROR(HttpStatus.CONFLICT, "수신 전화번호를 받아올 수 없습니다.");

    private final HttpStatus httpStatus;
    private final String message;


}

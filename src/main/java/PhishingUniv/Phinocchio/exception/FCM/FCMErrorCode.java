package PhishingUniv.Phinocchio.exception.FCM;


import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
public enum FCMErrorCode {
    FCM_ERROR(HttpStatus.CONFLICT, ""),
    FCM_NOT_FOUND(HttpStatus.NOT_FOUND, "");

    private HttpStatus httpStatus;
    private String message;


}

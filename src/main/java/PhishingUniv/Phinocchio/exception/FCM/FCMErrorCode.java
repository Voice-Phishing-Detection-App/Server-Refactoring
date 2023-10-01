package PhishingUniv.Phinocchio.exception.FCM;


import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
public enum FCMErrorCode {
    FCM_ERROR(HttpStatus.CONFLICT, "");

    private HttpStatus httpStatus;
    private String message;


}

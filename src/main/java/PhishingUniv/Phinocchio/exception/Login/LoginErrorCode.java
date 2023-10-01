package PhishingUniv.Phinocchio.exception.Login;


import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
public enum LoginErrorCode {
    USERNAME_DUPLICATED(HttpStatus.CONFLICT, ""),
    USERNAME_NOT_FOUND(HttpStatus.NOT_FOUND,""),
    INVALID_PASSWORD(HttpStatus.UNAUTHORIZED,""),
    DEVICE_DUPLICATED(HttpStatus.CONFLICT, "");

    private HttpStatus httpStatus;
    private String message;


}

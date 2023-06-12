package PhishingUniv.Phinocchio.exception.Login;


import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class LoginAppException extends RuntimeException{
    private LoginErrorCode errorCode;
    private String message;


}

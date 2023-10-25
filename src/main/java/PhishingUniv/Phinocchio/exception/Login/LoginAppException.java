package PhishingUniv.Phinocchio.exception.Login;


import PhishingUniv.Phinocchio.exception.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class LoginAppException extends RuntimeException{
    private final ErrorCode errorCode;
}

package PhishingUniv.Phinocchio.exception.Login;


import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class AppException extends RuntimeException{
    private ErrorCode errorCode;
    private String message;


}

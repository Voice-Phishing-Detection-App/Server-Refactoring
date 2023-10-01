package PhishingUniv.Phinocchio.exception.FCM;


import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class FCMAppException extends RuntimeException{
    private FCMErrorCode errorCode;
    private String message;


}

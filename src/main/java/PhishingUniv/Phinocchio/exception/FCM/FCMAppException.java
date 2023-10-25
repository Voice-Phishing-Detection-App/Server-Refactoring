package PhishingUniv.Phinocchio.exception.FCM;


import PhishingUniv.Phinocchio.exception.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class FCMAppException extends RuntimeException{
    private final ErrorCode errorCode;

}

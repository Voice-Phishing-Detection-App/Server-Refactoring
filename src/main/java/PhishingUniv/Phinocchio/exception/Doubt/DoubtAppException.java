package PhishingUniv.Phinocchio.exception.Doubt;


import PhishingUniv.Phinocchio.exception.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class DoubtAppException extends RuntimeException{
    private final ErrorCode errorCode;

}

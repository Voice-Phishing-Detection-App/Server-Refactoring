package PhishingUniv.Phinocchio.exception.Doubt;


import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class DoubtAppException extends RuntimeException{

    private DoubtErrorCode errorCode;
    private String message;


}

package PhishingUniv.Phinocchio.exception;


import PhishingUniv.Phinocchio.exception.Login.AppException;
import PhishingUniv.Phinocchio.exception.Login.InvalidJwtException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


//@RestControllerAdvice는 App에서 발생하는 예외를 중앙에서 처리한다는 의미
@RestControllerAdvice
public class ExceptionManager {

    //AppException이 발생하면 아래 메서드가 호출되어 예외를 처리함

    @ExceptionHandler(AppException.class)
    public ResponseEntity<?> appExceptionHandler(AppException e)
    {

        //예외 처리 결과를 ResponseEntity를 이용하여 Http로 반환함
        return ResponseEntity.status(e.getErrorCode().getHttpStatus())
                .body(e.getErrorCode().name()+" "+e.getMessage());//e.getErrorCode.name()
    }




    @ExceptionHandler(InvalidJwtException.class)
    public ResponseEntity<?> runtimeExceptionHandler(InvalidJwtException e)
    {

        //HttpStatus enum 형식의 열거형 상수 클래스
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(e.getMessage());
    }

}

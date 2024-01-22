package PhishingUniv.Phinocchio.exception;


import PhishingUniv.Phinocchio.exception.Doubt.DoubtAppException;
import PhishingUniv.Phinocchio.exception.FCM.FCMAppException;
import PhishingUniv.Phinocchio.exception.GoogleSTT.STTAppException;
import PhishingUniv.Phinocchio.exception.Login.InvalidJwtException;
import PhishingUniv.Phinocchio.exception.Login.LoginAppException;
import PhishingUniv.Phinocchio.exception.Setting.SettingAppException;
import PhishingUniv.Phinocchio.exception.Sos.SosAppException;
import PhishingUniv.Phinocchio.exception.Twilio.TwilioAppException;
import PhishingUniv.Phinocchio.exception.Voice.VoiceAppException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;


//App에서 발생하는 예외를 중앙 처리
@RestControllerAdvice
@Slf4j
public class ExceptionManager extends ResponseEntityExceptionHandler {

  @ExceptionHandler(DoubtAppException.class)
  public ResponseEntity<?> doubtAppExceptionHandler(DoubtAppException e) {
    ErrorCode errorCode = e.getErrorCode();
    return handleExceptionInternal(errorCode);
  }

  @ExceptionHandler(VoiceAppException.class)
  public ResponseEntity<?> voiceAppExceptionHandler(VoiceAppException e) {
    ErrorCode errorCode = e.getErrorCode();
    return handleExceptionInternal(errorCode);
  }

  @ExceptionHandler(STTAppException.class)
  public ResponseEntity<?> sttAppExceptionHandler(STTAppException e) {
    ErrorCode errorCode = e.getErrorCode();
    return handleExceptionInternal(errorCode);
  }

  @ExceptionHandler(FCMAppException.class)
  public ResponseEntity<?> fcmAppExceptionHandler(FCMAppException e) {
    ErrorCode errorCode = e.getErrorCode();
    return handleExceptionInternal(errorCode);
  }

  @ExceptionHandler(InvalidJwtException.class)
  public ResponseEntity<?> invalidJwtExceptionHandler(InvalidJwtException e) {
    ErrorCode errorCode = e.getErrorCode();
    return handleExceptionInternal(errorCode);
  }

  @ExceptionHandler(LoginAppException.class)
  public ResponseEntity<?> loginAppExceptionHandler(LoginAppException e) {
    ErrorCode errorCode = e.getErrorCode();
    return handleExceptionInternal(errorCode);
  }

  @ExceptionHandler(SettingAppException.class)
  public ResponseEntity<?> settingAppExceptionHandler(SettingAppException e) {
    ErrorCode errorCode = e.getErrorCode();
    return handleExceptionInternal(errorCode);
  }

  @ExceptionHandler(SosAppException.class)
  public ResponseEntity<?> sosAppExceptionHandler(SosAppException e) {
    ErrorCode errorCode = e.getErrorCode();
    return handleExceptionInternal(errorCode);
  }

  @ExceptionHandler(TwilioAppException.class)
  public ResponseEntity<?> twilioAppExceptionHandler(TwilioAppException e) {
    ErrorCode errorCode = e.getErrorCode();
    return handleExceptionInternal(errorCode);
  }

  private ResponseEntity<?> handleExceptionInternal(ErrorCode errorCode) {
    return ResponseEntity.status(errorCode.getHttpStatus())
        .body(makeErrorResponse(errorCode));
  }

  private Object makeErrorResponse(ErrorCode errorCode) {
    return ErrorResponse.builder()
        .error(errorCode.name())
        .message(errorCode.getMessage())
        .build();
  }

}

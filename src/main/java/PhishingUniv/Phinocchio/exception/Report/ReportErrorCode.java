package PhishingUniv.Phinocchio.exception.Report;

import PhishingUniv.Phinocchio.exception.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
public enum ReportErrorCode implements ErrorCode {

  FAILED_TO_SAVE(HttpStatus.INTERNAL_SERVER_ERROR, "신고내역 저장에 실패하였습니다."),
  REPORT_NOT_FOUND(HttpStatus.NOT_FOUND, "신고내역이 존재하지 않습니다.");


  private final HttpStatus httpStatus;
  private final String message;


}

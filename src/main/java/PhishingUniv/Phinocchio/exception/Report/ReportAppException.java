package PhishingUniv.Phinocchio.exception.Report;

import PhishingUniv.Phinocchio.exception.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class ReportAppException extends RuntimeException{
  private final ErrorCode errorCode;
}

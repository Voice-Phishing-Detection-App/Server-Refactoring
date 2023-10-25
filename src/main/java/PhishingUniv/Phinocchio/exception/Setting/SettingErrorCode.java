package PhishingUniv.Phinocchio.exception.Setting;

import PhishingUniv.Phinocchio.exception.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
public enum SettingErrorCode implements ErrorCode {

    SETTING_NOT_FOUND(HttpStatus.NOT_FOUND, "설정을 불러올 수 없습니다.");

    private HttpStatus httpStatus;
    private String message;

}

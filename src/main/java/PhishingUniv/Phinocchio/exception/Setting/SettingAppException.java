package PhishingUniv.Phinocchio.exception.Setting;

import PhishingUniv.Phinocchio.exception.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class SettingAppException extends RuntimeException{
    private final ErrorCode errorCode;
}

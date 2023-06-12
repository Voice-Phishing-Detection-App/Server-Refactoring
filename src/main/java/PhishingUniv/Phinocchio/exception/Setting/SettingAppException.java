package PhishingUniv.Phinocchio.exception.Setting;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class SettingAppException extends RuntimeException{

    private SettingErrorCode settingErrorCode;
    private String message;

}

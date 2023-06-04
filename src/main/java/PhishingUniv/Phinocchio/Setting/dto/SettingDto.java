package PhishingUniv.Phinocchio.Setting.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SettingDto {

    private Long settingId;

    private Long userId;

    private Boolean alram;

    private Boolean sosAlram;

    private int sosLevel;

}

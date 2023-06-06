package PhishingUniv.Phinocchio.domain.Setting.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(name = "setting")
public class SettingEntity {
    @Id
    @Column(name = "setting_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long settingId;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "alram", nullable = false)
    private Boolean alram;

    @Column(name = "detect_alram", nullable = false)
    private Boolean detectAlram;

    @Column(name = "sos_alram", nullable = false)
    private Boolean sosAlram;

    @Column(name = "sos_level", nullable = false)
    private int sosLevel;

}

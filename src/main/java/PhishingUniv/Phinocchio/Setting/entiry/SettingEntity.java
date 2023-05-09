package PhishingUniv.Phinocchio.Setting.entiry;

import javax.persistence.*;

@Entity
@Table(name = "setting")
public class SettingEntity {
    @Id
    @Column(name = "setting_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long setting_id;

    @Column(name = "user_id", nullable = false)
    private Long user_id;

    @Column(name = "alram", nullable = false)
    private boolean alram;

    @Column(name = "sos_alram", nullable = false)
    private boolean sos_alram;

    @Column(name = "sos_level", nullable = false)
    private int sos_level;

}

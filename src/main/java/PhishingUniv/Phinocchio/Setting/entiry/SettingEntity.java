package PhishingUniv.Phinocchio.Setting.entiry;

import javax.persistence.*;

@Entity
@Table(name = "setting")
public class SettingEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long setting_id;

    @Column(nullable = false)
    private Long user_id;

    @Column(nullable = false)
    private boolean alram;

    @Column(nullable = false)
    private boolean sos_alram;

    @Column(nullable = false)
    private int sos_level;

}

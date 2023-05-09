package PhishingUniv.Phinocchio.Doubt.entity;

import javax.persistence.*;

@Entity
@Table(name = "doubt")
public class DoubtEntity {

    @Id
    @Column(name = "doubt_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long doubt_id;

    @Column(name = "phone_number", nullable = false, length = 20)
    private String phone_number;

    @Column(name = "user_id", nullable = false)
    private Long user_id;

    @Column(name = "voice_id", nullable = false)
    private Long voice_id;

    @Column(name = "report_id", nullable = false)
    private Long report_id;
}
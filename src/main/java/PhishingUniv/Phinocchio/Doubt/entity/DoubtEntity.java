package PhishingUniv.Phinocchio.Doubt.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "doubt")
@Getter
@Setter
public class DoubtEntity {

    @Id
    @Column(name = "doubt_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long doubtId;

    @Column(name = "phone_number", nullable = false, length = 20)
    private String phoneNumber;

    @Column(name = "level", nullable = false)
    private int level;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "voiceId", nullable = false)
    private Long voice_id;

    @Column(name = "reportId")
    private Long report_id;
}
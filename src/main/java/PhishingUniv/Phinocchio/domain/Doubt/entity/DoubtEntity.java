package PhishingUniv.Phinocchio.domain.Doubt.entity;

import PhishingUniv.Phinocchio.domain.User.entity.UserEntity;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "doubt")
@Getter
@Setter
public class DoubtEntity extends Timestamp {

    @Id
    @Column(name = "doubt_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long doubtId;

    @Column(name = "phone_number", nullable = false, length = 20)
    private String phoneNumber;

    @Column(name = "level", nullable = false)
    private int level;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonIgnoreProperties({"doubtList"})
    private UserEntity user;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "voiceId", nullable = false)
    private Long voice_id;

    @Column(name = "reportId")
    private Long report_id;
}
package PhishingUniv.Phinocchio.domain.Report.entity;

import PhishingUniv.Phinocchio.domain.User.entity.UserEntity;
import PhishingUniv.Phinocchio.domain.Voice.entity.VoiceEntity;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "report")
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor //이게 있어야 findReportEntitiesById가 작동함
public class ReportEntity extends Timestamp {
    @Id
    @Column(name = "report_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long reportId;

    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false, length = 50)
    private ReportType type;

    @Column(name="title", nullable = false, length = 50)
    private String title;

    @Column(name = "content", nullable = false)
    private String content;

    @Column(name = "phone_number", nullable = false, length = 20)
    private String phoneNumber;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
    @JsonIgnoreProperties({"sosList"})
    private UserEntity user;

    @OneToOne
    @JoinColumn(name="voice_id")
    private VoiceEntity voice;

    public ReportEntity(ReportType reportType, String title, String content, String phoneNumber, UserEntity user, VoiceEntity voice)
    {
        this.title = title;
        this.type = reportType;
        this.content = content;
        this.phoneNumber = phoneNumber;
        this.user = user;
        this.voice = voice;
    }
}
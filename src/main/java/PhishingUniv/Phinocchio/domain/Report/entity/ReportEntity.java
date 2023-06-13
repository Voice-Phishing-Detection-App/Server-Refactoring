package PhishingUniv.Phinocchio.domain.Report.entity;

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

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "voice_id")
    private Long voiceId;

    public ReportEntity(ReportType reportType, String title, String content, String phoneNumber, Long userId, Long voiceId)
    {
        this.title = title;
        this.type = reportType;
        this.content = content;
        this.phoneNumber = phoneNumber;
        this.userId = userId;
        this.voiceId = voiceId;
    }
}
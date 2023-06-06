package PhishingUniv.Phinocchio.domain.Report.entity;

import javax.persistence.*;

@Entity
@Table(name = "report")
public class ReportEntity extends Timestamp {
    @Id
    @Column(name = "report_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long reportId;

    @Column(name = "type", nullable = false, length = 50)
    private String type;

    @Column(name = "content", nullable = false)
    private String content;

    @Column(name = "phone_number", nullable = false, length = 20)
    private String phoneNumber;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "voice_id", nullable = false)
    private Long voiceId;
}
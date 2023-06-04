package PhishingUniv.Phinocchio.Report.entity;

import javax.persistence.*;

@Entity
@Table(name = "report")
public class ReportEntity {
    @Id
    @Column(name = "report_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long reportId;

    @Column(name = "reason", nullable = false, length = 50)
    private String reason;

    @Column(name = "content", nullable = false)
    private String content;

    @Column(name = "phone_number", nullable = false, length = 20)
    private String phoneNumber;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "voice_id", nullable = false)
    private Long voiceId;
}
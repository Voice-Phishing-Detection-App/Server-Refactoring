package PhishingUniv.Phinocchio.Report.entity;

import javax.persistence.*;

@Entity
@Table(name = "report")
public class ReportEntity {
    @Id
    @Column(name = "report_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long report_id;

    @Column(name = "reason", nullable = false)
    private String reason;

    @Column(name = "content", nullable = false)
    private String content;

    @Column(name = "phone_number", nullable = false, length = 20)
    private String phone_number;

    @Column(name = "user_id", nullable = false)
    private Long user_id;

    @Column(name = "voice_id", nullable = false)
    private Long voice_id;
}
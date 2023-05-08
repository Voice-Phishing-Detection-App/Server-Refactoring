package PhishingUniv.Phinocchio.Report.entity;

import javax.persistence.*;

@Entity
@Table(name = "report")
public class ReportEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long report_id;

    @Column(nullable = false)
    private String reason;

    @Column(nullable = false)
    private String content;

    @Column(nullable = false, length = 20)
    private String phone_number;

    @Column
    private Long user_id;

    @Column
    private Long voice_id;
}
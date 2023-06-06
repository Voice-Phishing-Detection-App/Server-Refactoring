package PhishingUniv.Phinocchio.domain.Inquiry.entity;

import javax.persistence.*;

@Entity
@Table(name = "inquiry")
public class InquiryEntity {
    @Id
    @Column(name = "inquiry_id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long inquiryId;

    @Column(name = "type", nullable = false, length = 50)
    private String type;

    @Column(name = "content", nullable = false)
    private String content;

    @Column(name = "processed", nullable = false)
    private boolean processed;
}

package PhishingUniv.Phinocchio.Inquiry.entity;

import javax.persistence.*;

@Entity
@Table(name = "inquiry")
public class InquiryEntity {
    @Id
    @Column(name = "inquiry_id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long inquiry_id;

    @Column(name = "type", nullable = false)
    private String type;
}

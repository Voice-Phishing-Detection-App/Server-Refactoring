package PhishingUniv.Phinocchio.Inquiry.entity;

import javax.persistence.*;

@Entity
@Table(name = "inquiry")
public class InquiryEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long inquiry_id;

    @Column(nullable = false)
    private String type;
}

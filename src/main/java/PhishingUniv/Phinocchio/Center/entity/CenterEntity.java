package PhishingUniv.Phinocchio.Center.entity;

import javax.persistence.*;

@Entity
@Table(name = "center")
public class CenterEntity {
    @Id
    @Column(name = "center_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long centerId;

    @Column(name = "name", nullable = false, length = 50)
    private String name;

    @Column(name = "content", nullable = false)
    private String content;

    @Column(name = "phone_number", nullable = false, length = 20)
    private String phoneNumber;

}

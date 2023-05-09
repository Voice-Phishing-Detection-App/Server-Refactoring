package PhishingUniv.Phinocchio.Sos.entity;

import javax.persistence.*;

@Entity
@Table(name = "sos")
public class SosEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long sos_id;

    @Column(nullable = false)
    private Long user_id;

    @Column(nullable = false, length=20)
    private String phone_number;

    @Column(nullable = false)
    private String relation;

    @Column(nullable = false)
    private int level;

}

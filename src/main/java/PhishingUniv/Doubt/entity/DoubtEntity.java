package PhishingUniv.Doubt.entity;

import javax.persistence.*;

@Entity
@Table(name = "doubt")
public class DoubtEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long doubt_id;

    @Column(length = 20)
    private String phone_number;

    @Column
    private Long user_id;

    @Column
    private Long voice_id;

    @Column
    private Long report_id;
}
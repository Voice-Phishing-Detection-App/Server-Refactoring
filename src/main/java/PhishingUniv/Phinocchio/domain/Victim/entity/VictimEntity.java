package PhishingUniv.Phinocchio.domain.Victim.entity;

import javax.persistence.*;

@Entity
@Table(name = "victim")
public class VictimEntity {
    @Id
    @Column(name = "victime_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long victimId;

    @Column(name = "title", nullable = false, length = 50)
    private String title;

    @Column(name = "content", nullable = false)
    private String content;
}

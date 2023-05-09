package PhishingUniv.Phinocchio.Voice.entity;

import javax.persistence.*;

@Entity
@Table(name = "voice")
public class VoiceEntity {
    @Id
    @Column(name = "voice_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long voice_id;

    @Column(name = "link", nullable = false, length = 100)
    private String link;
}
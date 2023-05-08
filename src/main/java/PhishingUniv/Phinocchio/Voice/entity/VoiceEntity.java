package PhishingUniv.Phinocchio.Voice.entity;

import javax.persistence.*;

@Entity
@Table(name = "voice")
public class VoiceEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long voice_id;

    @Column
    private String link;
}
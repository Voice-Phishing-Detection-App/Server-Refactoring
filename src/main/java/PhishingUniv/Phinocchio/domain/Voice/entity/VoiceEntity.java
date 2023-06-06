package PhishingUniv.Phinocchio.domain.Voice.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "voice")
@Getter
@Setter
public class VoiceEntity {
    @Id
    @Column(name = "voice_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long voiceId;

    @Lob
    @Column(name = "text", nullable = false)
    private String text;
}
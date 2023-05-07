package PhishingUniv.Voice.entity;

import javax.persistence.*;

@Entity
@Table(name = "voice_entity")
public class VoiceEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long voice_id;

    @Column
    private String link;
}
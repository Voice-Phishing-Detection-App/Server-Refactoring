package PhishingUniv.Phinocchio.domain.Voice.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "voice")
@Getter
@Setter
@Builder
public class VoiceEntity {

  @Id
  @Column(name = "voice_id")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long voiceId;

  @Lob
  @Column(name = "text", nullable = false)
  private String text;

  public VoiceEntity(Long voiceId, String text) {
    this.voiceId = voiceId;
    this.text = text;
  }

  public VoiceEntity(String text) {
    this.text = text;
  }
}
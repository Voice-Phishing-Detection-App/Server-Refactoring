package PhishingUniv.Phinocchio.domain.Doubt.entity;

import PhishingUniv.Phinocchio.domain.Report.entity.ReportEntity;
import PhishingUniv.Phinocchio.domain.User.entity.UserEntity;
import PhishingUniv.Phinocchio.domain.Voice.entity.VoiceEntity;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "doubt")
@Getter
@Setter
public class DoubtEntity extends Timestamp implements Serializable {

  @Id
  @Column(name = "doubt_id")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long doubtId;

  @Column(name = "phone_number", nullable = false, length = 20)
  private String phoneNumber;

  @Column(name = "level", nullable = false)
  private int level;

  @ManyToOne
  @JoinColumn(name = "user_id", referencedColumnName = "user_id")
  @JsonIgnoreProperties({"doubtList", "sosList"})
  private UserEntity user;

  @Column(name = "title", nullable = false)
  private String title;

  @OneToOne
  @JoinColumn(name = "voice_id")
  private VoiceEntity voice;

  @OneToOne
  @JoinColumn(name = "report_id")
  private ReportEntity report;
}
package PhishingUniv.Phinocchio.domain.User.entity;


import PhishingUniv.Phinocchio.domain.Doubt.entity.DoubtEntity;
import PhishingUniv.Phinocchio.domain.Login.dto.SignupRequestDto;
import PhishingUniv.Phinocchio.domain.Sos.entity.SosEntity;
import java.io.Serializable;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;


@Entity
@Table(name = "`user`")
@Getter
@Builder
@AllArgsConstructor
public class UserEntity extends Timestamp implements Serializable {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "user_id")
  private Long userId;

  @Column(name = "name", nullable = false, length = 50)
  private String name;

  //실제 id
  @Column(name = "id", nullable = false, length = 50)
  private String id;

  @Column(name = "password", nullable = false, length = 100)
  private String password;

  @Column(name = "phone_number", nullable = false, length = 20)
  private String phoneNumber;

  @Column(name = "fcm_token", nullable = false)
  private String fcmToken;

  @OneToMany(mappedBy = "user")
  private List<DoubtEntity> doubtList;

  @OneToMany(mappedBy = "user")
  private List<SosEntity> sosList;

    /*@Column(name = "registration_date", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date registration_date;


    @Column(name = "agree_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date agree_date;
*/

  public UserEntity() {
  }

  public UserEntity(SignupRequestDto signupRequestDto) {
    this.id = signupRequestDto.getId();
    this.name = signupRequestDto.getName();
    this.password = signupRequestDto.getPassword();
    this.phoneNumber = signupRequestDto.getPhoneNumber();
    this.fcmToken = signupRequestDto.getFcmToken();
  }
}
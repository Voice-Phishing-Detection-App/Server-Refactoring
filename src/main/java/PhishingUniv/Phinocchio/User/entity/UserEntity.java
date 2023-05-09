package PhishingUniv.Phinocchio.User.entity;



import PhishingUniv.Phinocchio.Login.dto.SignupRequestDto;
import javax.persistence.*;
import lombok.*;

import java.util.Date;


@Entity
@Table(name = "`user`")
@Getter
@Setter
@Builder
@AllArgsConstructor

public class UserEntity {

    @Id
    @GeneratedValue
    @Column(name = "user_id")
    private Long user_id;

    @Column(name = "name", nullable = false, length = 50)
    private String name;

    //실제 id
    @Column(name = "id", nullable = false, length = 50)
    private String id;

    @Column(name = "password", nullable = false, length = 100)
    private String password;

    @Column(name = "phone_number", nullable = false, length = 20)
    private String phone_number;

    @Column(name = "registration_date", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date registration_date;


    @Column(name = "agree_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date agree_date;


    public UserEntity()
    {}
    public UserEntity(SignupRequestDto signupRequestDto)
    {
        this.id = signupRequestDto.getId();
        this.name=signupRequestDto.getName();
        this.password = signupRequestDto.getPassword();
        this.phone_number= signupRequestDto.getPhone_number();
    }
}

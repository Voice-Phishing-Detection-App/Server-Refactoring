package PhishingUniv.Phinocchio.Login.entity;



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

public class User {

    @Id
    @GeneratedValue
    @Column(name = "user_id")
    private Long user_id;

    @Column(name = "name",length = 50)
    private String name;

    //실제 id
    @Column(name = "id", length = 50)
    private String id;

    @Column(name = "password", length = 100)
    private String password;

    @Column(name = "phone_number", length = 20)
    private String phone_number;

    @Column(name = "registration_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date registration_date;


    @Column(name = "agree_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date agree_date;


    public User()
    {}
    public User(SignupRequestDto signupRequestDto)
    {
        this.id = signupRequestDto.getId();
        this.name=signupRequestDto.getName();
        this.password = signupRequestDto.getPassword();
        this.phone_number= signupRequestDto.getPhone_number();
    }
}

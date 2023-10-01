package PhishingUniv.Phinocchio.domain.Login.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignupRequestDto {
    //이름
    private String name;

    //전화번호
    private String phoneNumber;

    //아이디
    private String id;

    //비밀번호
    private String password;

    // fcm token
    private String fcmToken;

}

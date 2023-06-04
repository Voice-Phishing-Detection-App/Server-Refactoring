package PhishingUniv.Phinocchio.Login.dto;

public class SignupRequestDto {
    //이름
    private String name;


    //전화번호
    private String phoneNumber;

    //아이디
    private String id;

    //비밀번호
    private String password;



    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhone_number(String phone_number) {
        this.phoneNumber = phone_number;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }




}

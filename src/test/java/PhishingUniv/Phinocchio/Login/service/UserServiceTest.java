package PhishingUniv.Phinocchio.Login.service;

import PhishingUniv.Phinocchio.Login.dto.SignupRequestDto;


import PhishingUniv.Phinocchio.Login.repository.UserRepository;
import org.assertj.core.api.Assertions;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;


import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;


@ExtendWith(SpringExtension.class)
@DataJpaTest
class UserServiceTest {


    private final UserService userService;

    @Autowired
    private UserRepository userRepository;

    UserServiceTest(UserService userService)
    {
        this.userService = userService;
    }
/*
    @BeforeEach
    public void beforeEach(){
        userRepository = new MemoryUserRepository();
        userService = new UserService(userRepository);

    }*/
/*
    @AfterEach
    public void afterEach(){
        userRepository.clearStore();
    }*/
  /*  UserServiceTest(UserService userService)
    {
        this.userService = userService;
    }*/

    /*
    @Test
    void 회원가입() {
        //given
        User user = new User();
        user.setName("hello");
        //when
        String saveId = userService.registerUser(user);
        //then
        User findUser = userService.findOne(saveId).get();
        Assertions.assertThat(user.getName()).isEqualTo(findUser.getName());


    }*/

    @Test
    public void 중복_회원_예외(){
        //given
       SignupRequestDto user1 = new SignupRequestDto();

        user1.setName("spring");

        SignupRequestDto user2 = new SignupRequestDto();
        user2.setName("spring");

        //when
        userService.registerUser(user1);

        //try-catch

        /*
        try{
            userService.join(user2);
            fail();
        } catch(IllegalStateException e)
        {
            Assertions.assertThat(e.getMessage()).isEqualTo("이미 존재하는 회원입니다.");
        }



*/
        //then
        IllegalStateException e = assertThrows(IllegalStateException.class, () -> userService.registerUser(user2));

        Assertions.assertThat(e.getMessage()).isEqualTo("이미 존재하는 회원입니다.");

    }

    @Test
    void findUsers() {
    }

    @Test
    void findOne() {
    }
}
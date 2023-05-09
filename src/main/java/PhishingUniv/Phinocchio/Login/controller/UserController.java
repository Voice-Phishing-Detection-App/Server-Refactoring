package PhishingUniv.Phinocchio.Login.controller;

import PhishingUniv.Phinocchio.Login.dto.LoginDto;
import PhishingUniv.Phinocchio.Login.dto.SignupRequestDto;
import PhishingUniv.Phinocchio.User.entity.UserEntity;
import PhishingUniv.Phinocchio.Login.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService)
    {
        this.userService = userService;
    }


    @PostMapping("/login")
    public UserEntity login(@RequestBody LoginDto loginDto) {

          return userService.Login(loginDto);
    }

    @PostMapping("/signup")
    public String signup(@RequestBody SignupRequestDto requestDto) {

        return userService.registerUser(requestDto);


    }




}

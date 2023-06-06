package PhishingUniv.Phinocchio.Login.controller;

import PhishingUniv.Phinocchio.Login.dto.LoginDto;
import PhishingUniv.Phinocchio.Login.dto.SignupRequestDto;
import PhishingUniv.Phinocchio.Login.dto.TokenDto;
import PhishingUniv.Phinocchio.User.entity.UserEntity;
import PhishingUniv.Phinocchio.Login.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;


    @PostMapping("/login")
    public ResponseEntity<TokenDto> login(@RequestBody LoginDto loginDto) {
        String token= userService.login(loginDto);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Authorization", "Bearer " + token);
        return ResponseEntity.ok().headers(httpHeaders).body(new TokenDto(token));
          //return ResponseEntity.ok(new TokenDto());
    }

    @PostMapping("/signup")
    public String signup(@RequestBody SignupRequestDto requestDto) {

        return userService.registerUser(requestDto);


    }

    @GetMapping("/hello")
    public String hello()
    {
        return "Hello";
    }

    @GetMapping("/securityTest")
    public String securityTest()
    {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        // 사용자 식별
        return authentication.getName();

    }


}

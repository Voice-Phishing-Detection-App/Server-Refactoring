package PhishingUniv.Phinocchio.domain.Login.controller;

import PhishingUniv.Phinocchio.domain.Login.dto.LoginDto;
import PhishingUniv.Phinocchio.domain.Login.dto.SignupRequestDto;
import PhishingUniv.Phinocchio.domain.Login.dto.TokenDto;
import PhishingUniv.Phinocchio.domain.Login.security.UserDetailsImpl;
import PhishingUniv.Phinocchio.domain.Login.service.UserService;
import PhishingUniv.Phinocchio.exception.Login.InvalidJwtException;
import lombok.RequiredArgsConstructor;
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

import java.security.Security;

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

    /*
    @GetMapping("/securityTest")
    public String securityTest()
    {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Object principal = authentication.getPrincipal();
        UserDetailsImpl userDetails ;

        // 사용자 식별
        if (principal instanceof UserDetailsImpl) {
            userDetails = (UserDetailsImpl) principal;
            return userDetails.getUserId();
        } else {
            // principal이 UserDetailsImpl 타입이 아닌 경우에 대한 처리
            throw new InvalidJwtException("Unexpected principal type: " + principal.getClass().getName());
        }


    }*/


}

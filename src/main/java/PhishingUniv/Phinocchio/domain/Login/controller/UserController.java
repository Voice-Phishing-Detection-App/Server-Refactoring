package PhishingUniv.Phinocchio.domain.Login.controller;

import PhishingUniv.Phinocchio.domain.Login.dto.LoginDto;
import PhishingUniv.Phinocchio.domain.Login.dto.SignupRequestDto;
import PhishingUniv.Phinocchio.domain.Login.dto.SignupResponseDto;
import PhishingUniv.Phinocchio.domain.Login.dto.TokenDto;
import PhishingUniv.Phinocchio.domain.Login.security.UserDetailsImpl;
import PhishingUniv.Phinocchio.domain.Login.service.UserService;
import PhishingUniv.Phinocchio.exception.Login.InvalidJwtException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "UserController", description = "로그인, 회원가입을 하는 컨트롤러")
public class UserController {

    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;


    @PostMapping("/login")
    @Operation(summary = "로그인을 하는 컨트롤러", description = "로그인을 하는 컨트롤러입니다.")
    public ResponseEntity<TokenDto> login(@RequestBody LoginDto loginDto) {
        String token= userService.login(loginDto);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Authorization", "Bearer " + token);

        return ResponseEntity.ok().headers(httpHeaders).body(new TokenDto(token));
    }

    @PostMapping("/signup")
    @Operation(summary = "회원가입을 하는 컨트롤러", description = "회원가입을 하는 컨트롤러입니다.")
    public ResponseEntity<?> signup(@RequestBody SignupRequestDto requestDto) {
        SignupResponseDto signupResponseDto = userService.registerUser(requestDto);

        return ResponseEntity.ok(signupResponseDto);
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

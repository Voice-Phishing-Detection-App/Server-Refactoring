package PhishingUniv.Phinocchio.Login.service;

import PhishingUniv.Phinocchio.Login.dto.LoginDto;
import PhishingUniv.Phinocchio.Login.dto.SignupRequestDto;
import PhishingUniv.Phinocchio.Login.dto.TokenDto;
import PhishingUniv.Phinocchio.Login.exception.AppException;
import PhishingUniv.Phinocchio.Login.exception.ErrorCode;
import PhishingUniv.Phinocchio.Login.security.JwtAuthenticationFilter;
import PhishingUniv.Phinocchio.Login.security.JwtGenerator;
import PhishingUniv.Phinocchio.User.entity.UserEntity;

import PhishingUniv.Phinocchio.Login.repository.UserRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    private final JwtGenerator jwtGenerator;

    private void validateDuplicateUser(SignupRequestDto requestDto)
    {
        userRepository.findById(requestDto.getId())
                .ifPresent(m ->{
                    throw new AppException(ErrorCode.USERNAME_DUPLICATED,"이미 존재하는 아이디입니다.");
                });

    }

    public String login(LoginDto loginDto)
    {




        Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginDto.getId(),
                            loginDto.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);

        return jwtGenerator.generateToken(authentication);


       // return ResponseEntity.ok().headers(httpHeaders).body(tokenDto);
/*


        Optional<UserEntity> found = userRepository.findById(loginDto.getId());
        if(!found.isPresent())
        {
            throw new AppException(ErrorCode.USERNAME_NOT_FOUND,"찾을 수 없는 회원입니다.");

        }



        if(!passwordEncoder.matches(found.get().getPassword(), loginDto.getPassword()))
        {
            throw new AppException(ErrorCode.INVALID_PASSWORD, "패스워드를 잘못 입력했습니다.");



        }

        //password 검증 과정 안 들어감 아직


        else
        {


        }
*/
    }

    public String registerUser(SignupRequestDto requestDto)
    {
        //같은 id를 가지는 중복 회원 X
        validateDuplicateUser(requestDto);
        requestDto.setPassword(passwordEncoder.encode(requestDto.getPassword()));
        UserEntity user = new UserEntity(requestDto);
        userRepository.save(user);
        return user.getId();





    }



    public List<UserEntity> findUsers()
    {
        return userRepository.findAll();
    }

    public Optional<UserEntity> findOne(String userId) {
        return userRepository.findById(userId);
    }
}

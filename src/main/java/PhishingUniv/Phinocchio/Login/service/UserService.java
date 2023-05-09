package PhishingUniv.Phinocchio.Login.service;

import PhishingUniv.Phinocchio.Login.dto.LoginDto;
import PhishingUniv.Phinocchio.Login.dto.SignupRequestDto;
import PhishingUniv.Phinocchio.Login.entity.User;

import PhishingUniv.Phinocchio.Login.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class UserService {
    private final UserRepository userRepository;
    //private final PasswordEncoder passwordEncoder;
    @Autowired
    public UserService(UserRepository userRepository/*, PasswordEncoder passwordEncoder*/)
    {
        this.userRepository = userRepository;
        /*this.PasswordEncoder = passwordEncoder;*/
    }
    private void validateDuplicateUser(SignupRequestDto requestDto)
    {
        userRepository.findById(requestDto.getId())
                .ifPresent(m ->{
                    throw new IllegalStateException("이미 존재하는 회원입니다.");
                });

    }

    public User Login(LoginDto loginDto)
    {
        Optional<User> found = userRepository.findById(loginDto.getId());
        if(!found.isPresent())
        {
            throw new IllegalStateException();
        }
        else
            return found.get();
    }

    public String registerUser(SignupRequestDto requestDto)
    {
        //같은 id를 가지는 중복 회원 X
        validateDuplicateUser(requestDto);
        User user = new User(requestDto);
        userRepository.save(user);
        return user.getId();





    }



    public List<User> findUsers()
    {
        return userRepository.findAll();
    }

    public Optional<User> findOne(String userId) {
        return userRepository.findById(userId);
    }
}

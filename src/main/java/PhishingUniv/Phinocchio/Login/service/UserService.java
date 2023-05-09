package PhishingUniv.Phinocchio.Login.service;

import PhishingUniv.Phinocchio.Login.dto.LoginDto;
import PhishingUniv.Phinocchio.Login.dto.SignupRequestDto;
import PhishingUniv.Phinocchio.User.entity.UserEntity;

import PhishingUniv.Phinocchio.Login.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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

    public UserEntity Login(LoginDto loginDto)
    {
        Optional<UserEntity> found = userRepository.findById(loginDto.getId());
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

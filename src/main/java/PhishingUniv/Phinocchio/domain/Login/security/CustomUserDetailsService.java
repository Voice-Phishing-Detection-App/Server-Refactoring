package PhishingUniv.Phinocchio.domain.Login.security;

import PhishingUniv.Phinocchio.exception.Login.AppException;
import PhishingUniv.Phinocchio.exception.Login.ErrorCode;
import PhishingUniv.Phinocchio.domain.Login.repository.UserRepository;
import PhishingUniv.Phinocchio.domain.User.entity.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {



    private final UserRepository userRepository;
    @Override
    public UserDetailsImpl loadUserByUsername(String Id) throws UsernameNotFoundException {
        UserEntity user = userRepository.findById(Id).orElseThrow(()-> new AppException(ErrorCode.USERNAME_NOT_FOUND,"Username not found"));



        return new UserDetailsImpl(user);
    }
}

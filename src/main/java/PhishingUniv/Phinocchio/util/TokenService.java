package PhishingUniv.Phinocchio.util;

import PhishingUniv.Phinocchio.domain.Login.repository.UserRepository;
import PhishingUniv.Phinocchio.domain.Login.security.JwtAuthenticationFilter;
import PhishingUniv.Phinocchio.domain.Login.security.JwtGenerator;
import PhishingUniv.Phinocchio.domain.User.entity.UserEntity;
import PhishingUniv.Phinocchio.exception.Login.LoginAppException;
import PhishingUniv.Phinocchio.exception.Login.LoginErrorCode;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

@Service
@AllArgsConstructor
public class TokenService {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    private final JwtGenerator jwtGenerator;

    private final UserRepository userRepository;

    public Long getUserIdByRequest(HttpServletRequest request) {
        // 토큰 추출
        String token = jwtAuthenticationFilter.getJwtFromRequest(request);
        // 아이디 추출
        String id = jwtGenerator.getIdFromJWT(token);
        // userId 추출
        UserEntity user = userRepository.findById(id)
                .orElseThrow(() -> new LoginAppException(LoginErrorCode.USERNAME_NOT_FOUND, "사용자를 찾을 수 없습니다."));
        return user.getUserId();
    }


}

package PhishingUniv.Phinocchio.domain.Login.security;


import PhishingUniv.Phinocchio.exception.Login.InvalidJwtException;
import io.jsonwebtoken.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtGenerator {

    private final Logger logger = LoggerFactory.getLogger(JwtGenerator.class);

    public String generateToken(Authentication authentication){
        String id = authentication.getName();
/*

        UserDetailsImpl userDetails = null;
        Object principal = authentication.getPrincipal();
        if (principal instanceof UserDetailsImpl) {
            userDetails = (UserDetailsImpl) principal;
            // userDetails 객체를 사용하여 추가 작업 수행
        } else {
            throw new InvalidJwtException("Invalid JWT");
        }
*/
        Date currentDate = new Date();
        Date expireDate = new Date(currentDate.getTime() + SecurityConstants.JWT_EXPIRATION);

        //토큰 생성하여 반환
        return Jwts.builder()
                //.claim("userId", userDetails.getUserId())
                .setSubject(id)
                .setIssuedAt(currentDate)
                .setExpiration(expireDate)
                .signWith(SecurityConstants.JWT_KEY,SignatureAlgorithm.HS512)
                .compact();


    }
    public String getIdFromJWT(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(SecurityConstants.JWT_KEY)
                .build()
                .parseClaimsJws(token)
                .getBody();
        return claims.getSubject();
    }
    /*public String getUserIdFromJWT(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(SecurityConstants.JWT_KEY)
                .build()
                .parseClaimsJws(token)
                .getBody();
            return claims.get("userId",String.class);
    }*/

    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(SecurityConstants.JWT_KEY)
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (io.jsonwebtoken.security.SecurityException | MalformedJwtException e) {
            logger.info("잘못된 JWT 서명");
        } catch (ExpiredJwtException e) {
            logger.info("만료된 JWT 토큰");
        } catch (UnsupportedJwtException e) {
            logger.info("지원되지 않는 토큰");
        } catch (IllegalArgumentException e) {
            logger.info("JWT 오류");
        }
        System.out.println("token 오류");
        return false;
    }
}

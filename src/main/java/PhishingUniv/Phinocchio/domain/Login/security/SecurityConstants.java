package PhishingUniv.Phinocchio.domain.Login.security;

import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import java.security.Key;

public class SecurityConstants {
    public static final long JWT_EXPIRATION = 70000;


    public static final Key JWT_KEY =Keys.secretKeyFor(SignatureAlgorithm.HS512);
            //Keys.hmacShaKeyFor(Decoders.BASE64.decode(JWT_SECRET));

}

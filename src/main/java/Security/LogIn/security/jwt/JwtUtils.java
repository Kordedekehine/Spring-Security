package Security.LogIn.security.jwt;


import Security.LogIn.security.services.UserDetailsImpl;
import io.jsonwebtoken.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtUtils {

    //ALL WE WANT TO ACHIEVE WITH THIS CLASS
//    generate a JWT from username, date, expiration, secret
//    get username from JWT
//    validate a JWT

    private static final Logger logger = LoggerFactory.getLogger(JwtUtils.class);

    @Value("${student.jwtSecret}")
    private String jwtSecret;

    @Value("${student.jwtExpirationMs}")
    private int jwtExpirationMs;

    public String generateJwtToken(Authentication authentication){
        UserDetailsImpl userPrincipal = (UserDetailsImpl) authentication.getPrincipal();
        return Jwts.builder().setSubject(userPrincipal.getUsername()).setIssuedAt(new Date())
                .setExpiration(new Date((new Date()).getTime() + jwtExpirationMs))
                .signWith(SignatureAlgorithm.ES512,jwtSecret).compact();
    }

    public String getUsernameFromJwtToken(String token){
        return Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody().getSubject();
    }

    public boolean validateJwtToken(String authToken){
        try {
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(authToken);
            return true;
        }catch (SignatureException e){
            logger.error("Invalid JWT Signature: {} ",e.getMessage());
        }catch (MalformedJwtException e){
            logger.error("Invalid JWT Token: {} ",e.getMessage());
        }catch (ExpiredJwtException e){
            logger.error("JWT Token is expired: {} ",e.getMessage());
        }catch (UnsupportedJwtException e){
            logger.error("JWT token is unsupported: {} ",e.getMessage());
        }catch (IllegalArgumentException e){
            logger.error("JWT claims string is empty: {}",e.getMessage());
        }
        return false;
    }
}

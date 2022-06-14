package mysqlSecurity.secureDemo.jwt;

import io.jsonwebtoken.*;
import mysqlSecurity.secureDemo.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtTokenUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(JwtTokenUtil.class);
    private static final long EXPIRE_DURATION= 24 * 60 * 60 * 1000 ; //24HRS

    @Value("${app.jwt.Secret}")
    private String secretKey;

public String generateAccessTokenUser(User user){

    return Jwts.builder()
            .setSubject(user.getId() + ", " + user.getEmail())
            .setIssuer("Korede")
            .setIssuedAt(new Date())
            .setExpiration(new Date(System.currentTimeMillis() + EXPIRE_DURATION))
            .signWith(SignatureAlgorithm.HS512,secretKey).compact();
}

public boolean validateAccessToken(String token){
    try {
        Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
        }catch (ExpiredJwtException e){
         LOGGER.error("JWT expired", e);
    }catch (IllegalArgumentException e){
        LOGGER.error("Token is null",e);
    }catch (MalformedJwtException e){
        LOGGER.error("Jwt is invalid",e);
    }catch (UnsupportedJwtException e){
        LOGGER.error("Jwt is not supported",e);
    }catch (SignatureException e){
        LOGGER.error("Signature validation failed",e);
    }
    return false;
}

public String getSubject(String token){
    return parseClaims(token).getSubject();
}

private Claims parseClaims(String token){
    return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody();
}
}

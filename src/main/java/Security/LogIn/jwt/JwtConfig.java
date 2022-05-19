package Security.LogIn.jwt;

import com.google.common.net.HttpHeaders;
import io.jsonwebtoken.security.Keys;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.crypto.SecretKey;

@ConfigurationProperties(prefix = "application.jwt")
@Configuration
public class JwtConfig { //some major changes are in the application properties

    //we are tired of hardcoding the secret key so we create this class,add it in the app.properties,and sync it here
    //then we can inject this class and use it
private String secretKey;
private String tokenPrefix;
private Integer tokenExpirationAfterDays;

    public JwtConfig() {
    }

    public String getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }

    public String getTokenPrefix() {
        return tokenPrefix;
    }

    public void setTokenPrefix(String tokenPrefix) {
        this.tokenPrefix = tokenPrefix;
    }

    public Integer getTokenExpirationAfterDays() {
        return tokenExpirationAfterDays;
    }

    public void setTokenExpirationAfterDays(Integer tokenExpirationAfterDays) {
        this.tokenExpirationAfterDays = tokenExpirationAfterDays;
    }


    @Bean
     public SecretKey secretKey(){
        return Keys.hmacShaKeyFor(secretKey.getBytes());
     }

     //then we authorize it
    public String getAuthorizationHeader(){
        return HttpHeaders.AUTHORIZATION;
    }
}

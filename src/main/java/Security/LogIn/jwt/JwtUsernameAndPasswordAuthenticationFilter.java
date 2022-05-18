package Security.LogIn.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Date;

public class JwtUsernameAndPasswordAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    //this class is basically for validating the details --generating token

    private final AuthenticationManager authenticationManager;

    public JwtUsernameAndPasswordAuthenticationFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request,
                                                HttpServletResponse response) throws AuthenticationException {
        try {
            UsernameAndPasswordAuthenticationRequest authenticationRequest =
            //note we want to get input streams into our username and password empty boxes
            new ObjectMapper().readValue(request.getInputStream(), UsernameAndPasswordAuthenticationRequest.class);

            Authentication authentication = new UsernamePasswordAuthenticationToken(
                    authenticationRequest.getUsername(), //the username is the principle
                    authenticationRequest.getPassword() //the password is the credential
            );
            //this authentication manager if the user exists,if it does it validate the password,then confirm
         Authentication authenticate =  authenticationManager.authenticate(authentication);
         return authenticate;

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        //this only validates credentials and principles
    }

    //Clearly if the authentication above fails,the below successful authentication cannot display
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response,
                                            FilterChain chain, Authentication authResult) throws IOException,
            ServletException {

      String token =  Jwts.builder() //here we generate the token
                .setSubject(authResult.getName())
                .claim("authorities",authResult.getAuthorities())
                .setIssuedAt(new Date())
                .setExpiration(java.sql.Date.valueOf(LocalDate.now().plusWeeks(2)))
                //to make it very secure, the secure must be long and much
        .signWith(Keys.hmacShaKeyFor("securesecuresecuresecuresecuresecuresecuresecuresecuresecure".getBytes()))
                .compact();

        response.addHeader("Authorization","Bearer " + token);

        //here we complete everything that can help us send the token
    }
}

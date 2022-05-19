package Security.LogIn.jwt;

import com.google.common.base.Strings;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;


import javax.crypto.SecretKey;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class JwtTokenVerifier extends OncePerRequestFilter {
//we basically want the filter to be executed once per request

    private final SecretKey secretKey;
    private final JwtConfig jwtConfig;

    public JwtTokenVerifier(SecretKey secretKey, JwtConfig jwtConfig) {
        this.secretKey = secretKey;
        this.jwtConfig = jwtConfig;
    }


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        //Before
   // String authorizationHeader = request.getHeader("Authorization");
        //Now
        String authorizationHeader = request.getHeader(jwtConfig.getAuthorizationHeader());

    if (Strings.isNullOrEmpty(jwtConfig.getAuthorizationHeader()) || !authorizationHeader.startsWith(jwtConfig.getTokenPrefix())){
        filterChain.doFilter(request,response);
        return;
    }

        String token = authorizationHeader.replace(jwtConfig.getTokenPrefix(), "");

    try{
//        String secretKey = "securesecuresecuresecuresecuresecuresecuresecuresecuresecure";

        Jws<Claims> claimsJws = Jwts.parser()
                .setSigningKey(secretKey)
                //jws is a signed jwt
                .parseClaimsJws(token);
        //the details
        Claims body = claimsJws.getBody();

        String username = body.getSubject();

    var authorities = (List<Map<String,String>>) body.get("authorities");

           Set<SimpleGrantedAuthority> simpleGrantedAuthorities =  authorities.stream()
            .map(m -> new SimpleGrantedAuthority(m.get("authority")))
            .collect(Collectors.toSet());

        Authentication authentication = new UsernamePasswordAuthenticationToken(
                username,
                null,
       //instead of this conventional (authorities)..we use the simpleGranted
                simpleGrantedAuthorities
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
        //ALL THIS HAS TO DO FOR US IS TO VALIDATE THE TOKEN

    }catch (JwtException e){ //IF TOKEN CANNOT BE VALIDATED,THEN PRINT
        throw new IllegalStateException(String.format(" Token %s cannot be true", token ));
    }
       filterChain.doFilter(request,response);
    }
}

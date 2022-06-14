package mysqlSecurity.secureDemo.jwt;

import mysqlSecurity.secureDemo.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.security.auth.Subject;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JwtTokenFilter extends OncePerRequestFilter {

    @Autowired
    JwtTokenUtil jwtTokenUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        String header = request.getHeader("Authorization");
        System.out.println("Authorization header :" + header);

        if (!hasAuthorizationHeader(request)){
            filterChain.doFilter(request,response);
            return;
        }
       String accessToken = getAccessToken(request);

        if (!jwtTokenUtil.validateAccessToken(accessToken)){
            filterChain.doFilter(request,response);
            return;
        }

        setAuthenticationContext(accessToken,request);


    }

    private void setAuthenticationContext(String accessToken, HttpServletRequest servletRequest){
        UserDetails userDetails = getUserDetails(accessToken);

        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                userDetails,null,null);

        authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(servletRequest));

        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
    }

    private UserDetails getUserDetails(String accessToken){
        User user = new User();
      String[] subjectArray =  jwtTokenUtil.getSubject(accessToken).split(", ");

      user.setId(Integer.parseInt(subjectArray[0]));
      user.setEmail(subjectArray[1]);

      return user;
    }
    private Boolean hasAuthorizationHeader(HttpServletRequest servletRequest){
  String header = servletRequest.getHeader("Authorization");
        System.out.println("Authorization Header " + header);

        if (ObjectUtils.isEmpty(header) || !header.startsWith("Bearer")){
            return false;
        }
        return true;
    }

    private String getAccessToken(HttpServletRequest servletRequest){
        String header = servletRequest.getHeader("Authorization");
        String token = header.split(" ")[1].trim();
        System.out.println("Access Token: " + token);
        return token;
    }
}

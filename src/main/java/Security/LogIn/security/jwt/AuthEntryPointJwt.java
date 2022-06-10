package Security.LogIn.security.jwt;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class AuthEntryPointJwt implements AuthenticationEntryPoint {

    //ALL WE HAVE TO DO IN THIS CLASS IS

    //1. implements AuthenticationEntryPoint interface
    //2. Then we override the commence() method, his method will be triggered anytime  unauthenticated User requests
    //  a secured HTTP resource and an AuthenticationException is thrown


    private static final Logger logger = LoggerFactory.getLogger(AuthEntryPointJwt.class);
    //Note the Logger is meant for easy debugging(Error notation)


    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
                         AuthenticationException authException) throws IOException, ServletException {
       logger.error("Unauthorized error: {}",authException.getMessage());
       response.sendError(HttpServletResponse.SC_UNAUTHORIZED,"Error: Unauthorized");
       //Note: HttpServletResponse.SC_UNAUTHORIZED is the 401 Status code,You get?
    }
}

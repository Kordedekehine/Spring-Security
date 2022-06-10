package Security.LogIn.security;
import Security.LogIn.security.jwt.AuthEntryPointJwt;
import Security.LogIn.security.jwt.AuthToken;
import Security.LogIn.security.services.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    UserDetailsServiceImpl userDetailsService;
    //Spring Security will load User details to perform authentication & authorization.
    // So it has UserDetailsService interface that we need to implement.

    //Also, The implementation of UserDetailsService will be used for configuring DaoAuthenticationProvider
    //  by AuthenticationManagerBuilder.userDetailsService() method.

    @Autowired
    private AuthEntryPointJwt unauthorizedHandler;
    @Bean
    public AuthToken authenticationJwtTokenFilter() {
        return new AuthToken();
    }
    @Override
    public void configure(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
        authenticationManagerBuilder.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }
    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
    @Bean
    public PasswordEncoder passwordEncoder() {
        //Note: We also need a PasswordEncoder for the DaoAuthenticationProvider. If we don’t specify,
        // it will use plain text
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //override the configure(HttpSecurity http) method from WebSecurityConfigurerAdapter interface
        //1.It tells Spring Security how we configure CORS and CSRF, when we want to require all users to be
        // authenticated or not, which filter (AuthTokenFilter) and when we want it to work (filter
        // beforeUsernamePasswordAuthenticationFilter), which Exception Handler is chosen (AuthEntryPointJwt).
        http.cors().and().csrf().disable()
                .exceptionHandling().authenticationEntryPoint(unauthorizedHandler).and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
                .authorizeRequests().antMatchers("/api/auth/**").permitAll()
                .antMatchers("/api/test/**").permitAll()
                .anyRequest().authenticated();
        http.addFilterBefore(authenticationJwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);
    }
}

package Security.LogIn.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

import static Security.LogIn.security.ApplicationUserPermission.COURSE_WRITE;
import static Security.LogIn.security.ApplicationUserRole.*;

@Configuration
@EnableWebSecurity
public class ApplicationSecurityConfig extends WebSecurityConfigurerAdapter {
//we extend here basically to do the basic authentication

    private final PasswordEncoder passwordEncoder;

    @Autowired
    public ApplicationSecurityConfig(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
              .csrf().disable()
                .authorizeRequests()
                .antMatchers("/","index","/css/*","/js/*").permitAll()
                .antMatchers("/api/**").hasRole(STUDENT.name()) //this is to insert that only student can penetrate the api
                .antMatchers(HttpMethod.DELETE,"/management/api/**").hasAuthority(COURSE_WRITE.getPermission()) //GIVE STUDENT IN ADMIN ROLE PERMISSION TO DELETE
                .antMatchers(HttpMethod.POST,"/management/api/**").hasAuthority(COURSE_WRITE.getPermission()) //GIVE STUDENT IN ADMIN ROLE PERMISSION TO POST
                .antMatchers(HttpMethod.PUT,"/management/api/**").hasAuthority(COURSE_WRITE.getPermission()) //GIVE STUDENT IN ADMIN ROLE PERMISSION TO UPDATE
                .antMatchers(HttpMethod.GET,"/management/api/**").hasAnyRole(ADMIN.name(), ADMINTRAINEE.name())
                .anyRequest()
                .authenticated()
                .and()
                .httpBasic();
    }

    @Override
    @Bean
    protected UserDetailsService userDetailsService() {
        //user details help us retrieve user from the databases
        //the user Details is an interface
              UserDetails jibolaPasumaUser = User.builder()
                .username("Jibola Pasuma")
                .password(passwordEncoder.encode("password"))
         //       .roles(STUDENT.name()) //ROLE_STUDENT
                      .authorities(STUDENT.getGrantedAuthorities())
                      .build();

              UserDetails lindaUser = User.builder()
                      .username("linda")
                      .password(passwordEncoder.encode("password123"))
               //       .roles(ADMIN.name()) //ROLE_ADMIN
                      .authorities(ADMIN.getGrantedAuthorities())
                              .build();

        UserDetails tomUser = User.builder()
                .username("tom")
                .password(passwordEncoder.encode("password123"))
         //       .roles(ADMINTRAINEE.name()) //ROLE_ADMINTRAINEE
                .authorities(ADMINTRAINEE.getGrantedAuthorities())
                .build();

              return new InMemoryUserDetailsManager(jibolaPasumaUser,lindaUser,tomUser);
    }


}

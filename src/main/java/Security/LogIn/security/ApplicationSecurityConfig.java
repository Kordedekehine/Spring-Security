package Security.LogIn.security;

import Security.LogIn.auth.ApplicationUserService;
import Security.LogIn.jwt.JwtConfig;
import Security.LogIn.jwt.JwtTokenVerifier;
import Security.LogIn.jwt.JwtUsernameAndPasswordAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.crypto.SecretKey;

import static Security.LogIn.security.ApplicationUserRole.*;


@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class ApplicationSecurityConfig extends WebSecurityConfigurerAdapter {

    private final PasswordEncoder passwordEncoder;
    private final ApplicationUserService applicationUserService;
    private final JwtConfig jwtConfig;
    private final SecretKey secretKey;

    @Autowired
    public ApplicationSecurityConfig(PasswordEncoder passwordEncoder, ApplicationUserService applicationUserService, JwtConfig jwtConfig, SecretKey secretKey) {
        this.passwordEncoder = passwordEncoder;
        this.applicationUserService = applicationUserService;
        this.jwtConfig = jwtConfig;
        this.secretKey = secretKey;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                //TO MAKE OUR JWT STATELESS..BEFORE THE FILTER
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                //TO SWITCH, WE ADD THE FILTER
                .addFilter(new JwtUsernameAndPasswordAuthenticationFilter(authenticationManager(), jwtConfig, secretKey))
                //add filter after we have verified token
                .addFilterAfter(new JwtTokenVerifier(secretKey,jwtConfig),JwtUsernameAndPasswordAuthenticationFilter.class)
                .authorizeRequests()
                .antMatchers("/", "index", "/css/*", "/js/*").permitAll()
                .antMatchers("/api/**").hasRole(STUDENT.name())
                .anyRequest()
                .authenticated();
        //WE SWITCHED FROM FORM BASED PERMISSION TO JWT
//                .and()
//                .formLogin()
//                .loginPage("/login").permitAll()
//                .defaultSuccessUrl("/courses",true)
//                .passwordParameter("password")
//                .usernameParameter("username")
//                .and()
//                .rememberMe()
//                .tokenValiditySeconds((int) TimeUnit.DAYS.toSeconds(21)) //can only last for 21 days
//                .key("somethingverysecured")
//                .rememberMeParameter("remember-me") //this is to save the remember me details
//                .and()
//                .logout()
//                .logoutUrl("/logout")
//                //Note we use the below function because the csrf is disabled
//                .logoutRequestMatcher(new AntPathRequestMatcher("/logout","GET"))
//                .clearAuthentication(true)
//                .invalidateHttpSession(true)
//                .deleteCookies("JSESSIONID","remember-me")
//                .logoutSuccessUrl("/login");
    }

    @Override //to see through the authentication provider step,we override to configure based on our class extends..
    //then we authenticate what we have
    protected void configure(AuthenticationManagerBuilder auth) throws Exception { //How we wire things up
        auth.authenticationProvider(daoAuthenticationProvider());
    }

    @Bean //The PROVIDER
    public DaoAuthenticationProvider daoAuthenticationProvider(){
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(passwordEncoder);
        //This line actually allows password to be decoded
        provider.setUserDetailsService(applicationUserService);
        return provider;
    }


    //WE DELETE OUR FIRST CONFIGURATION.NOTE:: WE HAVE OUR OWN DIFFERENT CONFIGURATION AT AppUserService
////    @Override
////    @Bean
////    protected UserDetailsService userDetailsService() {
////        UserDetails annaSmithUser = User.builder()
////                .username("annasmith")
////                .password(passwordEncoder.encode("password"))
//////                .roles(STUDENT.name()) // ROLE_STUDENT
////                .authorities(STUDENT.getGrantedAuthorities())
////                .build();
////
////        UserDetails lindaUser = User.builder()
////                .username("linda")
////                .password(passwordEncoder.encode("password123"))
//////                .roles(ADMIN.name()) // ROLE_ADMIN
////                .authorities(ADMIN.getGrantedAuthorities())
////                .build();
////
////        UserDetails tomUser = User.builder()
////                .username("tom")
////                .password(passwordEncoder.encode("password123"))
//////                .roles(ADMINTRAINEE.name()) // ROLE_ADMINTRAINEE
////                .authorities(ADMINTRAINEE.getGrantedAuthorities())
////                .build();
//
//        return new InMemoryUserDetailsManager(
//                annaSmithUser,
//                lindaUser,
//                tomUser
//        );

    //}
}

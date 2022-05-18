package Security.LogIn.auth;

import com.google.common.collect.Lists;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

import static Security.LogIn.security.ApplicationUserRole.*;

@Repository("fake") //this repository tells spring that this class needs to be instantiated
//we can use the "fake" name to auto-wire it later(maybe using the qualifier)
public class FakeApplicationUserDaoService implements ApplicationUserDao{

    private final PasswordEncoder passwordEncoder;

    public FakeApplicationUserDaoService(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }


    //search for the name we want to check
    @Override
    public Optional<ApplicationUser> selectApplicationUserByUserName(String userName) {
    //search for the name we want to check
        return getApplicationUsers()
                .stream()
                .filter(applicationUser -> userName.equals(applicationUser.getUsername()))
                .findFirst();
    }

private List<ApplicationUser> getApplicationUsers(){
        List<ApplicationUser> applicationUsers = Lists.newArrayList(
            new ApplicationUser(
                  "annasmith",
                    passwordEncoder.encode("password"),
                   STUDENT.getGrantedAuthorities(),
                     true,
                       true,
                          true,
                          true
            ),

    new ApplicationUser(
            "linda",
            passwordEncoder.encode("password"),
            ADMIN.getGrantedAuthorities(),
            true,
            true,
            true,
            true
    ),

                new ApplicationUser(
                        "tom",
                        passwordEncoder.encode("password"),
                        ADMINTRAINEE.getGrantedAuthorities(),
                        true,
                        true,
                        true,
                        true
                )
        );

        return applicationUsers;
}



}

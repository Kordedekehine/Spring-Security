package Security.LogIn.auth;

import java.util.Optional;

public interface ApplicationUserDao { //help us load data from the datasource

     Optional<ApplicationUser> selectApplicationUserByUserName(String userName);
}

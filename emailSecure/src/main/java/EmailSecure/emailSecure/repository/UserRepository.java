package EmailSecure.emailSecure.repository;

import EmailSecure.emailSecure.model.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository("userRepository")
public interface UserRepository extends CrudRepository<User,String> {

    User findByEmailIdIgnoreCase(String emailId);
}

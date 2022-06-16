package EmailSecure.emailSecure.repository;

import EmailSecure.emailSecure.model.ConfirmToken;
import org.springframework.data.repository.CrudRepository;

public interface ConfirmTokenRepository extends CrudRepository<ConfirmToken, String> {

    ConfirmToken findByConfirmationToken(String confirmationToken);

}

package sk.leziemevpezinku.spring.repo;

import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;
import sk.leziemevpezinku.spring.model.User;

import java.util.Optional;

@Repository
public interface UserRepository extends ListCrudRepository<User, Long> {

    /**
     * Finds user by given email
     * @param email emiail to find
     * @return Optional User
     */
    Optional<User> findByEmail(String email);
}

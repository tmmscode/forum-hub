package tmmscode.forumHub.domain.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    @Query("SELECT u FROM User u WHERE u.email = :username")
    UserDetails findUserByLogin(String username);


    Optional<User> findUserByEmail(String email);

    @Query("SELECT u FROM User u WHERE u.active != false")
    List<User> findExistingUsers ();

    @Query("SELECT u FROM User u WHERE u.id = :userId AND u.active = false")
    Optional<User> isDeleted (Long userId);

}

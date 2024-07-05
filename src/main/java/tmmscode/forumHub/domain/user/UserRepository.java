package tmmscode.forumHub.domain.user;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import tmmscode.forumHub.domain.topic.Topic;
import tmmscode.forumHub.domain.topic.TopicStatus;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findUserByEmail(String email);

    @Query("SELECT u FROM User u WHERE u.active != false")
    List<User> findExistingUsers ();

    @Query("SELECT u FROM User u WHERE u.id = :userId AND u.active = false")
    Optional<User> isDeleted (Long userId);
}

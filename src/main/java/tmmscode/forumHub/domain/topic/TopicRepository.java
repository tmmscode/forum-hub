package tmmscode.forumHub.domain.topic;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface TopicRepository extends JpaRepository<Topic, Long> {
    @Query("SELECT t FROM Topic t JOIN t.course c WHERE t.title ILIKE :title AND t.message ILIKE :message AND c.id = :courseId")
    Optional<Topic> findTopicByTitleAndMessageInCourse(String title, String message, Long courseId);

    @Query(value = "SELECT t FROM Topic t WHERE t.status != DELETED")
    Page<Topic> findExistingTopics (Pageable pageable);

    @Query("SELECT t FROM Topic t WHERE t.id = :topicId AND t.status = DELETED")
    Optional<Topic> isDeleted (Long topicId);

    @Query("SELECT t FROM Topic t WHERE t.id = :topicId AND t.status = CLOSED")
    Optional<Topic> isClosed (Long topicId);

    @Query("SELECT t FROM Topic t JOIN t.course c WHERE t.status != DELETED AND c.id = :courseId AND c.active = true")
    Page<Topic> findExistingTopicsFromCourse(Pageable pageable, Long courseId);
}

package tmmscode.forumHub.domain.topic;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface TopicRepository extends JpaRepository<Topic, Long> {
    @Query("SELECT t FROM Topic t JOIN t.course c WHERE t.title ILIKE :title AND t.message ILIKE :message AND c.id = :courseId")
    Optional<Topic> findTopicByTitleAndMessageInCourse(String title, String message, Long courseId);

    @Query("SELECT t FROM Topic t WHERE t.status != :deletedStatus")
    Page<Topic> findExistingTopics (Pageable pageable, TopicStatus deletedStatus);
}

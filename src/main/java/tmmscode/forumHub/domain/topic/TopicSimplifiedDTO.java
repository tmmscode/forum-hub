package tmmscode.forumHub.domain.topic;

import tmmscode.forumHub.domain.course.Course;

import java.time.LocalDateTime;

public record TopicSimplifiedDTO(
        Long id,
        String title,
        String message,
        LocalDateTime createdAt,
        TopicStatus status,
        String authorName,
        Course course
) {
    public TopicSimplifiedDTO(Topic topic){
        this(
                topic.getId(),
                topic.getTitle(),
                topic.getMessage(),
                topic.getCreatedAt(),
                topic.getStatus(),
                topic.getAuthor().getName(),
                topic.getCourse()
        );
    }
}

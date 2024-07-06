package tmmscode.forumHub.domain.topic;

import tmmscode.forumHub.domain.course.Course;
import tmmscode.forumHub.domain.topic.answer.Answer;
import tmmscode.forumHub.domain.topic.answer.AnswerSimplifiedDTO;

import java.time.LocalDateTime;
import java.util.List;

public record TopicDetailsDTO(
        Long id,
        String title,
        String message,
        LocalDateTime createdAt,
        String authorName,
        TopicStatus status,
        Course course,
        List<AnswerSimplifiedDTO> answersList

) {

    public TopicDetailsDTO(Topic topic) {
        this(
                topic.getId(),
                topic.getTitle(),
                topic.getMessage(),
                topic.getCreatedAt(),
                topic.getAuthor().getName(),
                topic.getStatus(),
                topic.getCourse(),
                topic.getAnswers().stream().map(AnswerSimplifiedDTO::new).toList()
        );
    }

}

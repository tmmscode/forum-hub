package tmmscode.forumHub.domain.topic.answer;

import java.time.LocalDateTime;

public record AnswerSimplifiedDTO(
        Long id,
        String message,
        String authorName,
        String solution,
        LocalDateTime createdAt
) {
    public AnswerSimplifiedDTO(Answer data){
        this(
                data.getId(),
                data.getMessage(),
                data.getAuthor().getName(),
                data.getSolution(),
                data.getCreatedAt()
        );
    }
}

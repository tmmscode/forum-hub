package tmmscode.forumHub.domain.topic.answer;

import tmmscode.forumHub.domain.topic.TopicSimplifiedDTO;
import tmmscode.forumHub.domain.user.UserSimplifiedDTO;

import java.time.LocalDateTime;

public record AnswerDetailsDTO(
        Long id,
        String message,
        String solution,
        LocalDateTime createdAt,
        TopicSimplifiedDTO topic,
        UserSimplifiedDTO author
) {
    public AnswerDetailsDTO(Answer data){
        this(
                data.getId(),
                data.getMessage(),
                data.getSolution(),
                data.getCreatedAt(),
                new TopicSimplifiedDTO(data.getTopic()),
                new UserSimplifiedDTO(data.getAuthor())
        );
    }
}

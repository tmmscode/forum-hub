package tmmscode.forumHub.domain.topic.answer;

import jakarta.validation.constraints.NotBlank;

public record NewAnswerDTO(
        @NotBlank
        String message,
        @NotBlank
        String solution

) {
}

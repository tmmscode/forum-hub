package tmmscode.forumHub.domain.topic;

import jakarta.validation.constraints.NotBlank;

public record NewTopicDTO (
        @NotBlank
        String title,
        @NotBlank
        String message,
        @NotBlank
        Long authorId,
        @NotBlank
        Long courseId
) {
}

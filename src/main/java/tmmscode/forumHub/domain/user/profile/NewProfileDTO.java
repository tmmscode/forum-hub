package tmmscode.forumHub.domain.user.profile;

import jakarta.validation.constraints.NotBlank;

public record NewProfileDTO(
        @NotBlank
        String name
) {
}

package tmmscode.forumHub.domain.user.profile;

import jakarta.validation.constraints.NotBlank;

public record UpdateProfileDTO(
        @NotBlank
        String name
) {
}

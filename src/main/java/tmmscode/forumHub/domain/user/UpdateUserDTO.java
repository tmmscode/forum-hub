package tmmscode.forumHub.domain.user;

import jakarta.validation.constraints.NotBlank;

public record UpdateUserDTO(
        @NotBlank
        String name
) {
}

package tmmscode.forumHub.domain.user;

import jakarta.validation.constraints.NotBlank;

public record NewUserDTO (
        @NotBlank
        String name,
        @NotBlank
        String email,
        @NotBlank
        String password
) {
}

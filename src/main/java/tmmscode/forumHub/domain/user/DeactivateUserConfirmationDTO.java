package tmmscode.forumHub.domain.user;

import jakarta.validation.constraints.NotBlank;

public record DeactivateUserConfirmationDTO(
        @NotBlank
        String password
) {
}

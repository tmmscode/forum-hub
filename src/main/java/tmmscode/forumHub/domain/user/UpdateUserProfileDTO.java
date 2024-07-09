package tmmscode.forumHub.domain.user;

import jakarta.validation.constraints.NotNull;
import tmmscode.forumHub.domain.user.profile.UserProfileAction;

public record UpdateUserProfileDTO(
        @NotNull
        Long userId,
        @NotNull
        Long profileId,
        @NotNull
        UserProfileAction action

        // working on it...
) {
}

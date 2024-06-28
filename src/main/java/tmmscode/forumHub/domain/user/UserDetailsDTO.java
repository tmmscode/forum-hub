package tmmscode.forumHub.domain.user;

import tmmscode.forumHub.domain.user.profile.Profile;

import java.util.Set;

public record UserDetailsDTO(
        Long id,
        String name,
        Set<Profile> profile
) {
    public UserDetailsDTO(User data) {
        this(data.getId(), data.getName(), data.getProfile());
    }
}

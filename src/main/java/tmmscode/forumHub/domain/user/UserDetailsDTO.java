package tmmscode.forumHub.domain.user;

import tmmscode.forumHub.domain.user.profile.ProfileDetails;

import java.util.Set;
import java.util.stream.Collectors;

public record UserDetailsDTO(
        Long id,
        String name,
        String email,
        boolean active,
        Set<ProfileDetails> profile
) {
    public UserDetailsDTO(User data) {
        this(
                data.getId(),
                data.getName(),
                data.getEmail(),
                data.isActive(),
                data.getProfile().stream().map(ProfileDetails::new).collect(Collectors.toSet()));
    }
}

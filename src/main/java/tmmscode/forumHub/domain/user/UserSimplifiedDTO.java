package tmmscode.forumHub.domain.user;


import tmmscode.forumHub.domain.user.profile.ProfileDetails;

import java.util.Set;
import java.util.stream.Collectors;

public record UserSimplifiedDTO(
        Long id,
        String name,
        boolean active,
        Set<ProfileDetails> profile
) {
    public UserSimplifiedDTO(User data) {
        this(
                data.getId(),
                data.getName(),
                data.isActive(),
                data.getProfile().stream().map(ProfileDetails::new).collect(Collectors.toSet())
        );
    }
}

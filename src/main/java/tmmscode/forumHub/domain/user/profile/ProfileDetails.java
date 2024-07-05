package tmmscode.forumHub.domain.user.profile;

public record ProfileDetails(
        Long id,
        String name
) {
    public ProfileDetails(Profile data){
        this(data.getId(), data.getName());
    }
}

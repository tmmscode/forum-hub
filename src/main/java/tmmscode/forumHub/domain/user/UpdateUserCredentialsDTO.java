package tmmscode.forumHub.domain.user;

public record UpdateUserCredentialsDTO(
        String email,
        String password
) {
}

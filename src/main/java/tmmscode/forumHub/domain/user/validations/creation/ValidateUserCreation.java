package tmmscode.forumHub.domain.user.validations.creation;

import tmmscode.forumHub.domain.user.NewUserDTO;

public interface ValidateUserCreation {
    void validate(NewUserDTO data);
}

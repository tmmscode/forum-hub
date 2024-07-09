package tmmscode.forumHub.domain.user.profile.validations.creation;

import tmmscode.forumHub.domain.user.profile.NewProfileDTO;

public interface ValidateProfileCreation {
    void validate(NewProfileDTO data);
}

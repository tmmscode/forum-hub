package tmmscode.forumHub.domain.user.profile.validations.creation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import tmmscode.forumHub.domain.BusinessRulesException;
import tmmscode.forumHub.domain.user.profile.NewProfileDTO;
import tmmscode.forumHub.domain.user.profile.ProfileRepository;

@Component("ValidateProfileExistsByName")
public class ValidateProfileExistsByName implements ValidateProfileCreation {
    @Autowired
    private ProfileRepository profileRepository;

    @Override
    public void validate(NewProfileDTO data) {
        if(profileRepository.findProfileByName(data.name()).isPresent()){
            throw new BusinessRulesException("There is already a profile with this name");
        }
    }
}

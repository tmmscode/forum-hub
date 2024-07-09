package tmmscode.forumHub.domain.user.profile.validations.update;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import tmmscode.forumHub.domain.BusinessRulesException;
import tmmscode.forumHub.domain.user.profile.ProfileRepository;
import tmmscode.forumHub.domain.user.validations.update.ValidateUserUpdate;

@Component("ValidateProfileExistsById")
public class ValidateProfileExistsById implements ValidateProfileUpdate {
    @Autowired
    private ProfileRepository profileRepository;

    @Override
    public void validate(Long id){
        if(!profileRepository.existsById(id)){
            throw new BusinessRulesException("This profile does not exist");
        }
    }

}

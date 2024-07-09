package tmmscode.forumHub.domain.user.validations.creation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import tmmscode.forumHub.domain.BusinessRulesException;
import tmmscode.forumHub.domain.user.NewUserDTO;
import tmmscode.forumHub.domain.user.UserRepository;

@Component("ValidateExistingEmail")
public class ValidateExistingEmail implements ValidateUserCreation {
    @Autowired
    private UserRepository userRepository;

    @Override
    public void validate(NewUserDTO data) {
        if(userRepository.findUserByEmail(data.email()).isPresent()){
            throw new BusinessRulesException("The email is already in use");
        }
    }
}

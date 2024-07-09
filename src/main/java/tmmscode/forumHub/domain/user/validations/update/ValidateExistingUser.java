package tmmscode.forumHub.domain.user.validations.update;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import tmmscode.forumHub.domain.BusinessRulesException;
import tmmscode.forumHub.domain.user.UserRepository;

@Component("ValidateExistingUser")
public class ValidateExistingUser implements ValidateUserUpdate {
    @Autowired
    private UserRepository userRepository;

    @Override
    public void validate(Long id) {
        if(!userRepository.existsById(id)) {
            throw new BusinessRulesException("The user does not exist");
        }
    }
}

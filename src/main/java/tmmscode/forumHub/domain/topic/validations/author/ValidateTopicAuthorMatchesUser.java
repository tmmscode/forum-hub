package tmmscode.forumHub.domain.topic.validations.author;

import org.springframework.stereotype.Component;
import tmmscode.forumHub.domain.BusinessRulesException;
import tmmscode.forumHub.domain.topic.Topic;
import tmmscode.forumHub.domain.user.User;

@Component("ValidateTopicAuthorMatchesUser")
public class ValidateTopicAuthorMatchesUser implements ValidateAuthor<Topic> {
    @Override
    public void validate(Topic topic, User user){
        if(!topic.getAuthor().getId().equals(user.getId())){
            throw new BusinessRulesException("You are not the author");
        }
    }

}

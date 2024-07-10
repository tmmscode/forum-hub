package tmmscode.forumHub.domain.topic.validations.author;

import org.springframework.stereotype.Component;
import tmmscode.forumHub.domain.BusinessRulesException;
import tmmscode.forumHub.domain.topic.answer.Answer;
import tmmscode.forumHub.domain.user.User;

@Component("ValidateAnswerAuthorMatchesUser")
public class ValidateAnswerAuthorMatchesUser implements ValidateAuthor<Answer> {
    @Override
    public void validate(Answer answer, User user){
        if(!answer.getAuthor().getId().equals(user.getId())){
            throw new BusinessRulesException("You are not the author");
        }
    }
}

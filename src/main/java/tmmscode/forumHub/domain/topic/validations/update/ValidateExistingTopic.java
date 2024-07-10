package tmmscode.forumHub.domain.topic.validations.update;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import tmmscode.forumHub.domain.BusinessRulesException;
import tmmscode.forumHub.domain.topic.TopicRepository;

@Component("ValidateExistingTopic")
public class ValidateExistingTopic implements ValidateUpdateTopic{
    @Autowired
    private TopicRepository topicRepository;

    @Override
    public void validate(Long id){
        if(!topicRepository.existsById(id)){
            throw new BusinessRulesException("The chosen topic does not exist");
        }
        if(topicRepository.isDeleted(id).isPresent()){
            throw new BusinessRulesException("The chosen topic is deleted");
        }
    }
}

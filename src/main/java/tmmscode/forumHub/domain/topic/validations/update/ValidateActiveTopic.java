package tmmscode.forumHub.domain.topic.validations.update;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import tmmscode.forumHub.domain.BusinessRulesException;
import tmmscode.forumHub.domain.topic.TopicRepository;

@Component("ValidateActiveTopic")
public class ValidateActiveTopic implements ValidateUpdateTopic{
    @Autowired
    private TopicRepository topicRepository;

    @Override
    public void validate(Long id){
        if(topicRepository.isClosed(id).isPresent()){
            throw new BusinessRulesException("The chosen topic is closed");
        }
    }

}

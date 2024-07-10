package tmmscode.forumHub.domain.topic.validations.creation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import tmmscode.forumHub.domain.BusinessRulesException;
import tmmscode.forumHub.domain.topic.NewTopicDTO;
import tmmscode.forumHub.domain.topic.TopicRepository;

@Component("ValidateDuplicateTopic")
public class ValidateDuplicateTopic implements ValidateTopicCreation{
    @Autowired
    private TopicRepository topicRepository;

    @Override
    public void validate(NewTopicDTO data) {
        if(topicRepository.findTopicByTitleAndMessageInCourse(data.title(), data.message(), data.courseId()).isPresent()) {
            throw new BusinessRulesException("This topic already exists");
        }
    }
}

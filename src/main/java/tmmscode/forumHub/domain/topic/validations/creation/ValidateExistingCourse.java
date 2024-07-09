package tmmscode.forumHub.domain.topic.validations.creation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import tmmscode.forumHub.domain.BusinessRulesException;
import tmmscode.forumHub.domain.course.CourseRepository;
import tmmscode.forumHub.domain.topic.NewTopicDTO;

@Component("ValidateExistingCourse")
public class ValidateExistingCourse implements ValidateTopicCreation{
    @Autowired
    private CourseRepository courseRepository;

    @Override
    public void validate(NewTopicDTO data) {
        if(!courseRepository.existsById(data.courseId())){
            throw new BusinessRulesException("This course does not exists");
        }
    }
}

package tmmscode.forumHub.domain.course.validations.update;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import tmmscode.forumHub.domain.BusinessRulesException;
import tmmscode.forumHub.domain.course.CourseRepository;

@Component("ValidateActiveCourse")
public class ValidadeActiveCourse implements ValidateCourseUpdate {
    @Autowired
    private CourseRepository courseRepository;

    @Override
    public void validate(Long id){
        if(!courseRepository.existsById(id) && !courseRepository.isActive(id)) {
            throw new BusinessRulesException("The chosen course does not exist or is deactivated");
        }
    }
}

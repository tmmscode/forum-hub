package tmmscode.forumHub.domain.course;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import tmmscode.forumHub.domain.BusinessRulesException;
import tmmscode.forumHub.domain.course.validations.update.ValidateCourseUpdate;
import tmmscode.forumHub.domain.topic.Topic;
import tmmscode.forumHub.domain.topic.TopicRepository;

import java.util.List;

@Service
public class CourseManager {
    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private TopicRepository topicRepository;

    @Autowired
    private List<ValidateCourseUpdate> validateCourseUpdate;

    public List<Course> getAllActiveCourses () {
        return courseRepository.getAllActiveCourses();
    }

    public CourseDetailsDTO createCourse(NewCourseDTO data) {
        Course creatingCourse = new Course(data);
        courseRepository.save(creatingCourse);
        return new CourseDetailsDTO(creatingCourse);
    }

    public CourseDetailsDTO getCourseDetails(Long id) {
        var courseSelected = courseRepository.getReferenceById(id);
        return new CourseDetailsDTO(courseSelected);
    }

    public CourseDetailsDTO updateCourse(UpdateCourseDTO data, Long id) {
        validateCourseUpdate.forEach(v -> v.validate(id));

        var courseSelected = courseRepository.getReferenceById(id);
        courseSelected.update(data);
        return new CourseDetailsDTO(courseSelected);
    }

    public void deleteCourse(Long id) {
        validateCourseUpdate.forEach(v -> v.validate(id));

        var courseSelected = courseRepository.getReferenceById(id);
        courseSelected.delete();
    }

    public Page<Topic> getExistingTopicsFromCourse(Pageable pageable, Long courseId) {
        var activeTopicsFromCourse = topicRepository.findExistingTopicsFromCourse(pageable, courseId);
        return activeTopicsFromCourse;
    }

}

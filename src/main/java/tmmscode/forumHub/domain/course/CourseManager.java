package tmmscode.forumHub.domain.course;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import tmmscode.forumHub.domain.topic.Topic;
import tmmscode.forumHub.domain.topic.TopicRepository;
import tmmscode.forumHub.domain.topic.TopicStatus;

import java.util.List;

@Service
public class CourseManager {
    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private TopicRepository topicRepository;

    public List<Course> getAllActiveCourses () {
        return courseRepository.getAllActiveCourses();
    }

    public CourseDetailsDTO createCourse(NewCourseDTO data) {
        Course creatingCourse = new Course(data);
        courseRepository.save(creatingCourse);
        return new CourseDetailsDTO(creatingCourse);
    }

    public CourseDetailsDTO getCourseDetails(Long id) {
        if(courseRepository.existsById(id)) {
            var courseSelected = courseRepository.getReferenceById(id);
            return new CourseDetailsDTO(courseSelected);
        } else {
            throw new RuntimeException("O curso escolhido para detalhamento não existe!");
        }
    }

    public CourseDetailsDTO updateCourse(UpdateCourseDTO data, Long id) {
        if(courseRepository.existsById(id) && courseRepository.isActive(id)) {
            var courseSelected = courseRepository.getReferenceById(id);
            courseSelected.update(data);
            return new CourseDetailsDTO(courseSelected);
        } else {
            throw new RuntimeException("O curso escolhido para atualização não existe!");
        }
    }

    public void deleteCourse(Long id) {
        if(courseRepository.existsById(id) && courseRepository.isActive(id)) {
            var courseSelected = courseRepository.getReferenceById(id);
            courseSelected.delete();
        } else {
            throw new RuntimeException("O curso escolhido para exclusão não existe!");
        }
    }

    // pegar tópicos de um curso (topic repository
    public Page<Topic> getExistingTopicsFromCourse(Pageable pageable, Long courseId) {
        var activeTopicsFromCourse = topicRepository.findExistingTopicsFromCourse(pageable, courseId);
        return activeTopicsFromCourse;
    }

}

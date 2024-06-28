package tmmscode.forumHub.controller;


import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import tmmscode.forumHub.domain.course.Course;
import tmmscode.forumHub.domain.course.CourseDetailsDTO;
import tmmscode.forumHub.domain.course.CourseRepository;
import tmmscode.forumHub.domain.course.NewCourseDTO;
import tmmscode.forumHub.domain.topic.NewTopicDTO;

@RestController
@RequestMapping("courses")
public class CourseController {
    @Autowired
    private CourseRepository courseRepository;

    @GetMapping
    public ResponseEntity showAllCourses() {

        return ResponseEntity.ok(courseRepository.findAll());
    }

    @PostMapping
    @Transactional
    public ResponseEntity createCourse (@RequestBody @Valid NewCourseDTO data, UriComponentsBuilder uriComponentsBuilder) {
    Course newCourse = new Course(data);
    courseRepository.save(newCourse);

        return ResponseEntity.ok(new CourseDetailsDTO(newCourse));
    }
}

package tmmscode.forumHub.controller;


import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import tmmscode.forumHub.domain.course.CourseDetailsDTO;
import tmmscode.forumHub.domain.course.CourseManager;
import tmmscode.forumHub.domain.course.NewCourseDTO;
import tmmscode.forumHub.domain.course.UpdateCourseDTO;
import tmmscode.forumHub.domain.topic.TopicSimplifiedDTO;

@RestController
@RequestMapping("courses")
@Secured("ADMIN")
public class CourseController {
    @Autowired
    private CourseManager courseManager;

    @GetMapping
    public ResponseEntity getAllActiveCourses() {
        var allCourses = courseManager.getAllActiveCourses();
        return ResponseEntity.ok(allCourses.stream().map(CourseDetailsDTO::new));
    }

    @PostMapping
    @Transactional
    public ResponseEntity createCourse (@RequestBody @Valid NewCourseDTO data, UriComponentsBuilder uriComponentsBuilder) {
        CourseDetailsDTO createdCourse = courseManager.createCourse(data);

        var uri = uriComponentsBuilder.path("/couses/{id}").buildAndExpand(createdCourse.id()).toUri();
        return ResponseEntity.created(uri).body(createdCourse);
    }

    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity updateCourse(@RequestBody @Valid UpdateCourseDTO data, @PathVariable Long id) {
        CourseDetailsDTO courseUpdated = courseManager.updateCourse(data, id);
        return ResponseEntity.ok(courseUpdated);
    }

    @GetMapping("/{id}/topics")
    public ResponseEntity getAllTopicFromCurse(@PageableDefault(size = 10, sort = "createdAt", direction = Sort.Direction.ASC) Pageable pageable, @PathVariable Long id){
        var topicsFromCurse = courseManager.getExistingTopicsFromCourse(pageable, id).map(TopicSimplifiedDTO::new);
        return ResponseEntity.ok(topicsFromCurse);
    }
}
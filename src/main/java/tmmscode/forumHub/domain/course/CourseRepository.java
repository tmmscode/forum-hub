package tmmscode.forumHub.domain.course;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CourseRepository extends JpaRepository<Course, Long> {

    @Query("SELECT c FROM Course c WHERE c.active = true")
    List<Course> getAllActiveCourses();

    @Query("SELECT c.active FROM Course c WHERE c.id = :courseId")
    boolean isActive(Long courseId);
}

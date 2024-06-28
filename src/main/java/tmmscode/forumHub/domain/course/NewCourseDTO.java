package tmmscode.forumHub.domain.course;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record NewCourseDTO (
        @NotBlank
        String title,
        @NotNull
        CourseCategory category
){
}

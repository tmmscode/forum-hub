package tmmscode.forumHub.domain.course;

public record CourseDetailsDTO(
        Long id,
        String title,
        CourseCategory category
) {
    public CourseDetailsDTO(Course data) {
        this(data.getId(), data.getTitle(), data.getCategory());
    }
}

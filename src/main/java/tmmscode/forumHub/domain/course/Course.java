package tmmscode.forumHub.domain.course;

import jakarta.persistence.*;
import lombok.*;
import tmmscode.forumHub.domain.topic.TopicStatus;
import tmmscode.forumHub.domain.topic.UpdateTopicDTO;

@Entity(name = "Course")
@Table(name = "courses")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    @Enumerated(EnumType.STRING)
    @Column(name = "category", nullable = false)
    private CourseCategory category;

    private boolean active;

    public Course(NewCourseDTO data) {
        this.active = true;
        this.title = data.title();
        this.category = data.category();
    }

    public void update(UpdateCourseDTO data) {
        if(data.title() != null){
            this.title = data.title();
        }
        if(data.category() != null){
            this.category = data.category();
        }
    }

    public void delete() {
        this.active = false;
    }
}

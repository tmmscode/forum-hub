package tmmscode.forumHub.domain.topic;

import jakarta.persistence.*;
import lombok.*;
import tmmscode.forumHub.domain.course.Course;
import tmmscode.forumHub.domain.topic.answer.Answer;
import tmmscode.forumHub.domain.user.User;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity(name = "Topic")
@Table(name = "topics")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class Topic {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    @Lob
    private String message;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private TopicStatus status;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "author_id")
    private User author;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "course_id")
    private Course course;

    @OneToMany(mappedBy = "topic", fetch = FetchType.EAGER)
    private List<Answer> answers = new ArrayList<>();



    public void close() {
        this.status = TopicStatus.CLOSED;
    }

    public void update(UpdateTopicDTO data) {
        if(data.title() != null){
            this.title = data.title();
        }
        if(data.message() != null){
            this.message = data.message();
        }
    }

    public void delete() {
        this.status = TopicStatus.DELETED;
    }
}

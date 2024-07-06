package tmmscode.forumHub.domain.topic.answer;

import jakarta.persistence.*;
import lombok.*;
import tmmscode.forumHub.domain.topic.Topic;
import tmmscode.forumHub.domain.user.User;

import java.time.LocalDateTime;

@Entity(name = "Answer")
@Table(name = "answers")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class Answer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Lob
    private String message;

    @ManyToOne
    @JoinColumn(name = "topic_id", nullable = false)
    private Topic topic;

    @ManyToOne
    @JoinColumn(name = "author_id", nullable = false)
    private User author;

    @Lob
    private String solution;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;
}

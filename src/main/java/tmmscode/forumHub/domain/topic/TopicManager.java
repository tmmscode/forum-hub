package tmmscode.forumHub.domain.topic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tmmscode.forumHub.domain.course.Course;
import tmmscode.forumHub.domain.course.CourseRepository;
import tmmscode.forumHub.domain.user.User;
import tmmscode.forumHub.domain.user.UserRepository;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class TopicManager {
    @Autowired
    private TopicRepository topicRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CourseRepository courseRepository;

    public List<TopicDetailsDTO> showAllTopics() {
        var alltopics = topicRepository.findAll();

        return alltopics.stream()
                .map(t -> new TopicDetailsDTO(t))
                .toList();
    }

    public TopicDetailsDTO createTopic(NewTopicDTO data) {
        Topic creatingTopic = new Topic();
        creatingTopic.setTitle(data.title());
        creatingTopic.setMessage(data.message());

        creatingTopic.setStatus(TopicStatus.ACTIVE);
        creatingTopic.setCreatedAt(LocalDateTime.now());


        if(userRepository.existsById(data.authorId())){
            User foundUser = userRepository.findById(data.authorId()).get();
            creatingTopic.setAuthor(foundUser);
        } else {
            throw new RuntimeException("Usuário não existe!");
        }

        if(courseRepository.existsById(data.courseId())){
            Course foundCourse = courseRepository.findById(data.courseId()).get();
            creatingTopic.setCourse(foundCourse);
        } else {
            throw new RuntimeException("Curso não existe");
        }

        topicRepository.save(creatingTopic);

        return new TopicDetailsDTO(creatingTopic);
    }
}

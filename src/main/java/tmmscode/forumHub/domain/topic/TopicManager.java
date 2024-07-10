package tmmscode.forumHub.domain.topic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import tmmscode.forumHub.domain.BusinessRulesException;
import tmmscode.forumHub.domain.course.Course;
import tmmscode.forumHub.domain.course.CourseRepository;
import tmmscode.forumHub.domain.topic.validations.author.ValidateAuthor;
import tmmscode.forumHub.domain.topic.validations.creation.ValidateTopicCreation;
import tmmscode.forumHub.domain.topic.validations.update.ValidateExistingTopic;
import tmmscode.forumHub.domain.topic.validations.update.ValidateUpdateTopic;
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

    @Autowired
    private List<ValidateTopicCreation> validateTopicCreation;

    @Autowired
    private List<ValidateUpdateTopic> validateUpdateTopic;

    @Autowired
    private ValidateExistingTopic validateExistingTopic;

    @Autowired
    private ValidateAuthor<Topic> validateTopicAuthor;

    public Page<Topic> getExistingTopics(Pageable pageable) {
        var activeTopics = topicRepository.findExistingTopics(pageable);
        return activeTopics;
    }

    public TopicDetailsDTO getTopicDetails(Long id) {
        var topicSelected = topicRepository.getReferenceById(id);
        return new TopicDetailsDTO(topicSelected);
    }

    public TopicDetailsDTO createTopic(NewTopicDTO data, UserDetails user) {
        validateTopicCreation.forEach(v -> v.validate(data));

        Topic creatingTopic = new Topic();

        User creator = (User) user;
        creatingTopic.setAuthor(creator);

        Course foundCourse = courseRepository.findById(data.courseId()).get();
        creatingTopic.setCourse(foundCourse);

        creatingTopic.setTitle(data.title());
        creatingTopic.setMessage(data.message());

        creatingTopic.setStatus(TopicStatus.ACTIVE);
        creatingTopic.setCreatedAt(LocalDateTime.now());

        topicRepository.save(creatingTopic);
        return new TopicDetailsDTO(creatingTopic);
    }



    public TopicDetailsDTO updateTopic(UpdateTopicDTO data, Long id, UserDetails user) {
        validateUpdateTopic.forEach(v -> v.validate(id));
        User requester = (User) user;

        var topicSelected = topicRepository.findById(id).get();
        validateTopicAuthor.validate(topicSelected, requester);

        topicSelected.update(data);
        return new TopicDetailsDTO(topicSelected);
    }

    public void delete(Long id, UserDetails user) {
        validateExistingTopic.validate(id);
        User requester = (User) user;

        var topicSelected = topicRepository.findById(id).get();
        validateTopicAuthor.validate(topicSelected, requester);

        topicSelected.delete();
    }

    public void close(Long id, UserDetails user) {
        validateUpdateTopic.forEach(v -> v.validate(id));
        User requester = (User) user;

        var topicSelected = topicRepository.getReferenceById(id);
        validateTopicAuthor.validate(topicSelected, requester);

        topicSelected.close();
    }
}

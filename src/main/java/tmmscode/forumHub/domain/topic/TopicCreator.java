package tmmscode.forumHub.domain.topic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class TopicCreator {
    @Autowired
    private TopicRepository topicRepository;


    public TopicDetailsDTO createTopic(NewTopicDTO data) {
        Topic newTopic = new Topic();
        newTopic.setTitle(data.title());
            newTopic.setMessage(data.message());
//            Pesquisar no banco e getReferenceById
//        this.author = data.author();
//            Pesquisar no banco e getReferenceById
//        newTopic.setCourse(data.courseId());
        newTopic.setStatus(TopicStatus.ACTIVE);
        newTopic.setCreatedAt(LocalDateTime.now());

        return new TopicDetailsDTO(newTopic);
    }
}

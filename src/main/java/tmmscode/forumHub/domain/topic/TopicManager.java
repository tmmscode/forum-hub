package tmmscode.forumHub.domain.topic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

    public List<TopicSimplifiedDTO> getAllTopics() {
        var alltopics = topicRepository.findAll();

        return alltopics.stream()
                .map(TopicSimplifiedDTO::new)
                .toList();
    }

    public Page<Topic> getExistingTopics(Pageable pageable) {
        var activeTopics = topicRepository.findExistingTopics(pageable);
        return activeTopics;
    }

    public TopicDetailsDTO createTopic(NewTopicDTO data) {
        // criar validador para tópicos iguais
        if(topicRepository.findTopicByTitleAndMessageInCourse(data.title(), data.message(), data.courseId()).isPresent()) {
            throw new RuntimeException("O Tópico já existe!");
        }
        // ==============================

        Topic creatingTopic = new Topic();

        if(userRepository.existsById(data.authorId())){
            User foundUser = userRepository.getReferenceById(data.authorId());
            creatingTopic.setAuthor(foundUser);
        } else {
            throw new RuntimeException("O Usuário não existe!");
        }

        if(courseRepository.existsById(data.courseId())){
            Course foundCourse = courseRepository.getReferenceById(data.courseId());
            creatingTopic.setCourse(foundCourse);
        } else {
            throw new RuntimeException("O Curso não existe");
        }

        creatingTopic.setTitle(data.title());
        creatingTopic.setMessage(data.message());
        creatingTopic.setStatus(TopicStatus.ACTIVE);
        creatingTopic.setCreatedAt(LocalDateTime.now());

        topicRepository.save(creatingTopic);
        return new TopicDetailsDTO(creatingTopic);
    }


    public TopicDetailsDTO getTopicDetails(Long id) {
        if(topicRepository.existsById(id)) {
            var topicSelected = topicRepository.getReferenceById(id);
            return new TopicDetailsDTO(topicSelected);
        } else {
            throw new RuntimeException("O Tópico escolhido para detalhamento não existe!");
        }
    }


    public TopicDetailsDTO updateTopic(UpdateTopicDTO data, Long id) {
        topicIsActive(id);

        var topicSelected = topicRepository.getReferenceById(id);
        topicSelected.update(data);
        return new TopicDetailsDTO(topicSelected);
    }

    public void delete(Long id) {
        topicExist(id);

        var topicSelected = topicRepository.getReferenceById(id);
        topicSelected.delete();
    }

    public void close(Long id) {
        topicIsActive(id);

        var topicSelected = topicRepository.getReferenceById(id);
        topicSelected.close();
    }

    // faz parte das validações
    private void topicExist(Long id){
        if(!topicRepository.existsById(id)){
            throw new RuntimeException("O Tópico escolhido para atualização não existe!");
        }
        if(topicRepository.isDeleted(id).isPresent()){
            throw new RuntimeException("O Tópico escolhido para atualização foi excluido!");
        }
    }
    private void topicIsActive(Long id){
        topicExist(id);
        if(topicRepository.isClosed(id).isPresent()){
            throw new RuntimeException("O tópico escolhido para atualização está fechado!");
        }
    }
}

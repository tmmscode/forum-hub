package tmmscode.forumHub.domain.topic.answer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import tmmscode.forumHub.domain.BusinessRulesException;
import tmmscode.forumHub.domain.topic.TopicRepository;
import tmmscode.forumHub.domain.topic.TopicStatus;
import tmmscode.forumHub.domain.topic.validations.author.ValidateAuthor;
import tmmscode.forumHub.domain.topic.validations.update.ValidateExistingTopic;
import tmmscode.forumHub.domain.topic.validations.update.ValidateUpdateTopic;
import tmmscode.forumHub.domain.user.User;
import tmmscode.forumHub.domain.user.UserRepository;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class AnswerManager {
    @Autowired
    private AnswerRepository answerRepository;

    @Autowired
    private TopicRepository topicRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ValidateAuthor<Answer> validateAnswerAuthor;

    @Autowired
    private List<ValidateUpdateTopic> validateUpdateTopic;

    public AnswerDetailsDTO createAnswer(NewAnswerDTO data, Long topicId, UserDetails user) {
        validateUpdateTopic.forEach(v -> v.validate(topicId));
        User requester = (User) user;

        Answer creatingAnswer = new Answer();
        creatingAnswer.setMessage(data.message());
        creatingAnswer.setSolution(data.solution());
        creatingAnswer.setCreatedAt(LocalDateTime.now());

        creatingAnswer.setAuthor(requester);
        creatingAnswer.setTopic(topicRepository.getReferenceById(topicId));

        answerRepository.save(creatingAnswer);
        return new AnswerDetailsDTO(creatingAnswer);
    }

    public AnswerDetailsDTO getAnswerDetails(Long id) {
        return new AnswerDetailsDTO(answerRepository.getReferenceById(id));
    }


    public void delete(Long id, UserDetails user) {
        if(!answerRepository.existsById(id)){
            throw new BusinessRulesException("The answer does not exist");
        }

        User requester = (User) user;

        var answerSelected = answerRepository.findById(id).get();
        validateAnswerAuthor.validate(answerSelected, requester);

        answerRepository.delete(answerSelected);
    }
}

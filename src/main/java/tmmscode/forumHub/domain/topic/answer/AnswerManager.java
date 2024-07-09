package tmmscode.forumHub.domain.topic.answer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tmmscode.forumHub.domain.BusinessRulesException;
import tmmscode.forumHub.domain.topic.TopicRepository;
import tmmscode.forumHub.domain.topic.TopicStatus;
import tmmscode.forumHub.domain.user.UserRepository;

import java.time.LocalDateTime;

@Service
public class AnswerManager {
    @Autowired
    private AnswerRepository answerRepository;

    @Autowired
    private TopicRepository topicRepository;

    @Autowired
    private UserRepository userRepository;


    public AnswerDetailsDTO createAnswer(NewAnswerDTO data, Long topicId) {
        if(!topicRepository.existsById(topicId)) {
            throw new BusinessRulesException("O tópico que você está tentando responder não existe");
        }
        if(topicRepository.isDeleted(topicId).isPresent()){
            throw new BusinessRulesException("O tópico que você está tentando responder, foi apagado");
        }
        if(topicRepository.getReferenceById(topicId).getStatus().equals(TopicStatus.CLOSED)){
            throw new BusinessRulesException("O tópico que você está tentando responder, foi fechado");
        }

        Answer creatingAnswer = new Answer();
        creatingAnswer.setMessage(data.message());
        creatingAnswer.setSolution(data.solution());
        creatingAnswer.setCreatedAt(LocalDateTime.now());

        // pegar ID do usuário e adicionar a resposta - userRepository.getreference
        creatingAnswer.setAuthor(userRepository.getReferenceById((long) 1));
//   !!!!!!!!!!! Depois tem que tirar isso daqui, e pegar do token! !!!!!!!!!!!!!!!!!!!!!!!!!!!!


        creatingAnswer.setTopic(topicRepository.getReferenceById(topicId));

        answerRepository.save(creatingAnswer);
        return new AnswerDetailsDTO(creatingAnswer);
    }

    public AnswerDetailsDTO getAnswerDetails(Long id) {
        if(!answerRepository.existsById(id)){
            throw new BusinessRulesException("Essa resposta não existe!");
        }
        return new AnswerDetailsDTO(answerRepository.getReferenceById(id));
    }
}

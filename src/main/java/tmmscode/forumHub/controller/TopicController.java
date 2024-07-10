package tmmscode.forumHub.controller;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import tmmscode.forumHub.domain.topic.*;
import tmmscode.forumHub.domain.topic.answer.AnswerDetailsDTO;
import tmmscode.forumHub.domain.topic.answer.AnswerManager;
import tmmscode.forumHub.domain.topic.answer.NewAnswerDTO;

@RestController
@RequestMapping("topics")
public class TopicController {
    @Autowired
    private TopicManager topicManager;

    @Autowired
    private AnswerManager answerManager;

    @GetMapping
    public ResponseEntity<Page<TopicSimplifiedDTO>> getExistingTopics (@PageableDefault(size = 10, sort = "createdAt", direction = Sort.Direction.ASC) Pageable pageable) {
        var topicPage = topicManager.getExistingTopics(pageable).map(TopicSimplifiedDTO::new);
        return ResponseEntity.ok(topicPage);
    }

    @GetMapping("/{id}")
    public ResponseEntity getTopicDetails (@PathVariable Long id) {
        TopicDetailsDTO detailedTopic = topicManager.getTopicDetails(id);
        return ResponseEntity.ok(detailedTopic);
    }

    @PostMapping
    @Transactional
    @Secured({"ADMIN", "USER"})
    public ResponseEntity createTopic (@RequestBody @Valid NewTopicDTO data, UriComponentsBuilder uriComponentsBuilder, @AuthenticationPrincipal UserDetails user) {
        TopicDetailsDTO createdTopic = topicManager.createTopic(data, user);

        var uri = uriComponentsBuilder.path("/topics/{id}").buildAndExpand(createdTopic.id()).toUri();
        return ResponseEntity.created(uri).body(createdTopic);
    }

    @PutMapping("/{id}")
    @Transactional
    @Secured({"ADMIN", "USER"})
    public ResponseEntity updateTopic(@RequestBody @Valid UpdateTopicDTO data, @PathVariable Long id, @AuthenticationPrincipal UserDetails user) {
        TopicDetailsDTO topicUpdated = topicManager.updateTopic(data, id, user);
        return ResponseEntity.ok(topicUpdated);
    }

    @PutMapping("/{id}/close")
    @Transactional
    @Secured({"ADMIN", "USER"})
    public ResponseEntity closeTopic(@PathVariable Long id, @AuthenticationPrincipal UserDetails user) {
        topicManager.close(id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    @Transactional
    @Secured({"ADMIN", "USER"})
    public ResponseEntity deleteTopic(@PathVariable Long id, @AuthenticationPrincipal UserDetails user) {
        topicManager.delete(id, user);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{topicId}")
    @Transactional
    @Secured({"ADMIN", "USER"})
    public ResponseEntity sendAnswer (@RequestBody @Valid NewAnswerDTO data, @PathVariable Long topicId, UriComponentsBuilder uriComponentsBuilder, @AuthenticationPrincipal UserDetails user){
        // pegar id do usuário pelo token e enviar para a função para atribuir o autor
        AnswerDetailsDTO createdAnswer = answerManager.createAnswer(data, topicId);

        var uri = uriComponentsBuilder.path("/topics/answer/{id}").buildAndExpand(createdAnswer.id()).toUri();
        return ResponseEntity.created(uri).body(createdAnswer);
    }

    // Get pra id do tópico/answer/id = detalhes da pergunta
    @GetMapping("/answer/{id}")
    public ResponseEntity answerDetails (@PathVariable Long id) {
        AnswerDetailsDTO detailedAnswer = answerManager.getAnswerDetails(id);
        return ResponseEntity.ok(detailedAnswer);
    }
}

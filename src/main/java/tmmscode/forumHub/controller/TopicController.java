package tmmscode.forumHub.controller;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import tmmscode.forumHub.domain.topic.NewTopicDTO;
import tmmscode.forumHub.domain.topic.TopicDetailsDTO;
import tmmscode.forumHub.domain.topic.TopicManager;

@RestController
@RequestMapping("topics")
public class TopicController {
    @Autowired
    private TopicManager topicManager;

    @GetMapping
    public ResponseEntity topic () {
//        return ResponseEntity.ok("Olá!");
        return ResponseEntity.ok(topicManager.showAllTopics());
    }


    @PostMapping
    @Transactional
    public ResponseEntity createTopic (@RequestBody @Valid NewTopicDTO data, UriComponentsBuilder uriComponentsBuilder) {
        TopicDetailsDTO dto = topicManager.createTopic(data);

        var uri = uriComponentsBuilder.path("/topics/{id}").buildAndExpand(dto.id()).toUri();
        return ResponseEntity.created(uri).body(data);
    }
}

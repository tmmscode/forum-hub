package tmmscode.forumHub.controller;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;
import tmmscode.forumHub.domain.topic.NewTopicDTO;
import tmmscode.forumHub.domain.topic.TopicCreator;
import tmmscode.forumHub.domain.topic.TopicDetailsDTO;

@RestController
@RequestMapping("topics")
public class TopicController {
    @Autowired
    private TopicCreator topicCreator;

    @PostMapping
    @Transactional
    public ResponseEntity createTopic (@RequestBody @Valid NewTopicDTO data, UriComponentsBuilder uriComponentsBuilder) {
        TopicDetailsDTO dto = topicCreator.createTopic(data);
        System.out.println("Finge que salvou no repo");

        var uri = uriComponentsBuilder.path("/topics/{id}").buildAndExpand(dto.id()).toUri();
        return ResponseEntity.created(uri).body(dto);
    }
}

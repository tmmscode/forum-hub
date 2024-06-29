package tmmscode.forumHub.controller;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import tmmscode.forumHub.domain.topic.*;

@RestController
@RequestMapping("topics")
public class TopicController {
    @Autowired
    private TopicManager topicManager;

    @GetMapping
    public ResponseEntity<Page<TopicSimplifiedDTO>> listExistingTopics (@PageableDefault(size = 10, sort = "createdAt", direction = Sort.Direction.ASC) Pageable pageable) {
        var topicPage = topicManager.showExistingTopics(pageable).map(TopicSimplifiedDTO::new);
        return ResponseEntity.ok(topicPage);
    }

    @GetMapping("/{id}")
    public ResponseEntity detailedTopic (@PathVariable Long id) {
        TopicDetailsDTO detailedTopic = topicManager.getTopicDetails(id);
        return ResponseEntity.ok(detailedTopic);
    }

    @PostMapping
    @Transactional
    public ResponseEntity createTopic (@RequestBody @Valid NewTopicDTO data, UriComponentsBuilder uriComponentsBuilder) {
        TopicDetailsDTO createdTopic = topicManager.createTopic(data);

        var uri = uriComponentsBuilder.path("/topics/{id}").buildAndExpand(createdTopic.id()).toUri();
        return ResponseEntity.created(uri).body(data);
    }

    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity updateTopic(@RequestBody @Valid UpdateTopicDTO data, @PathVariable Long id) {
        TopicDetailsDTO topicUpdated = topicManager.updateTopic(data, id);
        return ResponseEntity.ok(topicUpdated);
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity deleteTopic(@PathVariable Long id) {
        topicManager.delete(id);
        return ResponseEntity.noContent().build();
    }
}

package tmmscode.forumHub.domain.topic.validations.creation;

import tmmscode.forumHub.domain.topic.NewTopicDTO;

public interface ValidateTopicCreation {
    void validate(NewTopicDTO data);
}

package tmmscode.forumHub.domain.topic.validations.author;

import tmmscode.forumHub.domain.user.User;

public interface ValidateAuthor<T> {
    void validate(T createdObject, User user);
}

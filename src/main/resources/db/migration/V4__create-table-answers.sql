create table answers(

    id bigint not null auto_increment,
    message TEXT NOT NULL,
    solution TEXT NOT NULL,
    created_at DATETIME NOT NULL,
    author_id bigint NOT NULL,
    topic_id bigint NOT NULL,

    primary key(id),
    FOREIGN KEY (author_id) REFERENCES users(id),
    FOREIGN KEY (topic_id) REFERENCES topics(id)
);


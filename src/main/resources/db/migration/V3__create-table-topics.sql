create table topics(

    id bigint not null auto_increment,
    title VARCHAR(255) NOT NULL,
    message TEXT NOT NULL,
    created_at DATETIME NOT NULL,
    status VARCHAR(100) NOT NULL,
    author_id bigint NOT NULL,
    course_id bigint NOT NULL,

    primary key(id),
    FOREIGN KEY (author_id) REFERENCES users(id),
    FOREIGN KEY (course_id) REFERENCES courses(id)
);


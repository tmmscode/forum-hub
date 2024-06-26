create table users(

    id bigint not null auto_increment,
    name VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL,

    primary key(id)
);

create table profiles(
    id bigint not null auto_increment,
    name VARCHAR(255) NOT NULL,

    primary key(id)
);

CREATE TABLE user_profile (
    user_id bigint,
    profile_id bigint,
    PRIMARY KEY (user_id, profile_id),
    FOREIGN KEY (user_id) REFERENCES users(id),
    FOREIGN KEY (profile_id) REFERENCES profiles(id)
);
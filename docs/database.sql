CREATE TABLE 'account'
(
    `id`             bigint(20) NOT NULL AUTO_INCREMENT,
    `account_id`     varchar(255) NOT NULL,
    `date_of_birth`  varchar(255) NOT NULL,
    `email`          varchar(255) NOT NULL,
    `gender`         varchar(255) NOT NULL,
    `name`           varchar(255) NOT NULL,
    `password`       varchar(255) NOT NULL,
    `parent_info_id` bigint,
    `sitter_info_id` bigint,
    primary key (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE child
(
    `id`            bigint(20) NOT NULL AUTO_INCREMENT,
    `date_of_birth` varchar(255) NOT NULL,
    `gender`        varchar(255),
    `parent_id`     bigint,
    primary key (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE parent_info
(
    `id` bigint(20) NOT NULL AUTO_INCREMENT,
    `care_request_info` TEXT,
    primary key (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE sitter_info
(
    `id`           bigint(20) NOT NULL AUTO_INCREMENT,
    `about_me` TEXT,
    `max_care_age` INT NOT NULL,
    `min_care_age` INT NOT NULL,
    primary key (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

alter table account
    add constraint UK_4lde57f579n315au55ua4gqxk unique (account_id);

alter table account
    add constraint UK_q0uja26qgu1atulenwup9rxyr unique (email);

alter table account
    add constraint FKklnf27egottmnt1akemedtqgs
        foreign key (parent_info_id)
            references parent_info(id);

alter table account
    add constraint FK4b921uoy6yt1ctnst8ektmne3
        foreign key (sitter_info_id)
            references sitter_info(id);

alter table child
    add constraint FK2w4l3g32q5s9w7i7ulwd4nbx5
        foreign key (parent_id)
            references parent_info(id);
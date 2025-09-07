CREATE TABLE users
(
    id       BIGINT AUTO_INCREMENT NOT NULL,
    name     VARCHAR(255) NOT NULL,
    email    VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL,
    role     VARCHAR(20) DEFAULT 'USER' NOT NULL,
    CONSTRAINT `PRIMARY` PRIMARY KEY (id)
);

ALTER TABLE links
    ADD user_id BIGINT NOT NULL;

ALTER TABLE links
    ADD CONSTRAINT links_users_id_fk FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE NO ACTION;

CREATE INDEX links_users_id_fk ON links (user_id);
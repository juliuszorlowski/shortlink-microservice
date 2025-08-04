CREATE TABLE links
(
    id              BIGINT AUTO_INCREMENT NOT NULL,
    url             VARCHAR(255) NOT NULL,
    shortened_url   VARCHAR(255) NOT NULL,
    created_at      DATE NOT NULL,
    click_count     INT DEFAULT 0 NULL,
    CONSTRAINT `PRIMARY` PRIMARY KEY (id)
);
CREATE TABLE links
(
    id              BIGINT AUTO_INCREMENT NOT NULL,
    url             VARCHAR(255) NOT NULL,
    code            VARCHAR(255) NOT NULL,
    created_at      TIMESTAMP(0) NOT NULL,
    click_count     INT DEFAULT 0 NULL,
    CONSTRAINT `PRIMARY` PRIMARY KEY (id)
);
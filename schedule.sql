CREATE database IF NOT EXISTS v1;
CREATE database IF NOT EXISTS v2;


USE v1;
CREATE TABLE IF NOT EXISTS schedule (
        schedule_id BIGINT AUTO_INCREMENT PRIMARY KEY,
        author VARCHAR(255) NOT NULL ,
        content VARCHAR(255) NOT NULL,
        create_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
        update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
        password VARCHAR(255) NOT NULL
);

USE v2;
CREATE TABLE IF NOT EXISTS member (
        member_id BIGINT PRIMARY KEY AUTO_INCREMENT,
        name VARCHAR(255) NOT NULL,
        email VARCHAR(255) UNIQUE NOT NULL,
        password VARCHAR(255) NOT NULL,
        create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
        update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS schedule (
        schedule_id BIGINT PRIMARY KEY AUTO_INCREMENT,
        member_id BIGINT NOT NULL,
        content TEXT NOT NULL,
        create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
        update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
        FOREIGN KEY (member_id) REFERENCES member(member_id) ON DELETE CASCADE
);

CREATE TABLE `foto` (
    `id` BIGINT NOT NULL AUTO_INCREMENT,
    `content_length` BIGINT NOT NULL,
    `content_type` VARCHAR(255) NOT NULL,
    `filename` VARCHAR(255) NOT NULL,  
    `url` VARCHAR(255) NOT NULL,  
    PRIMARY KEY (`id`),
    UNIQUE KEY (`filename`) 
);
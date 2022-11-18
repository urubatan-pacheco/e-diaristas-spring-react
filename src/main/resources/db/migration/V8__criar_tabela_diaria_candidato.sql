CREATE TABLE `diaria_candidato` (
    `diaria_id` BIGINT NOT NULL,
    `candidato_id` BIGINT NOT NULL,
    KEY(`diaria_id`),
    KEY(`candidato_id`),
    CONSTRAINT FOREIGN KEY(`candidato_id`) REFERENCES `usuario` (`id`),
    CONSTRAINT FOREIGN KEY(`diaria_id`) REFERENCES `diaria` (`id`)
);
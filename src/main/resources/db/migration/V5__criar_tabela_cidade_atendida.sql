CREATE TABLE `cidade_atendida` (
    `id` BIGINT NOT NULL AUTO_INCREMENT,
    `codigo_ibge` VARCHAR(255) NOT NULL,
    `cidade` VARCHAR(255) NOT NULL, 
    `estado` VARCHAR(255) NOT NULL, 
    PRIMARY KEY (`id`),
    UNIQUE KEY (`codigo_ibge`)
);

CREATE TABLE `cidade_atendida_usuario` (
    `cidade_atendida_id` BIGINT NOT NULL,
    `usuario_id` BIGINT NOT NULL,
    KEY (`usuario_id`),
    KEY (`cidade_atendida_id`),
    CONSTRAINT FOREIGN KEY (`cidade_atendida_id`) REFERENCES `cidade_atendida` (`id`),
    CONSTRAINT FOREIGN KEY (`usuario_id`) REFERENCES `usuario` (`id`)

);

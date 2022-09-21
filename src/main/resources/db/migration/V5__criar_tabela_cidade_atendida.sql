CREATE TABLE `cidade_atendida` (
    `id` BIGINT NOT NULL AUTO_INCREMENT,
    `codigo_ibge` VARCHAR(255) NOT NULL,
    `cidade` VARCHAR(255) NOT NULL, 
    `estado` VARCHAR(255) NOT NULL, 
    PRIMARY KEY (`id`),
    UNIQUE KEY (`codigo_ibge`)
);

CREATE TABLE `usuario_cidade_atendida` (
    `cidades_atendidas_id` BIGINT NOT NULL,
    `usuarios_id` BIGINT NOT NULL,
    KEY (`usuarios_id`),
    KEY (`cidades_atendidas_id`),
    CONSTRAINT FOREIGN KEY (`cidades_atendidas_id`) REFERENCES `cidade_atendida` (`id`),
    CONSTRAINT FOREIGN KEY (`usuarios_id`) REFERENCES `usuario` (`id`)

);

ALTER TABLE `usuario`
    ADD `chave_pix` VARCHAR(255) DEFAULT NULL, 
    ADD `cpf` VARCHAR(11) DEFAULT NULL, 
    ADD `nascimento` DATE DEFAULT NULL, 
    ADD `telefone`  VARCHAR(11) DEFAULT NULL, 
    ADD `reputacao` DOUBLE DEFAULT NULL,  
    ADD `foto_documento` BIGINT DEFAULT NULL,
    ADD `foto_usuario` BIGINT DEFAULT NULL,
    ADD UNIQUE KEY (`chave_pix`),
    ADD UNIQUE KEY (`cpf`),
    ADD FOREIGN KEY (`foto_usuario`) REFERENCES `foto` (`id`),
    ADD FOREIGN KEY (`foto_documento`) REFERENCES `foto` (`id`);



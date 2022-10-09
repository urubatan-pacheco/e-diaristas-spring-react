package br.com.urubatanpacheco.ediaristas.core.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum TipoUsuario {
    CLIENTE (1),
    DIARISTA (3), 
    ADMIN (3);

    private Integer id;
}

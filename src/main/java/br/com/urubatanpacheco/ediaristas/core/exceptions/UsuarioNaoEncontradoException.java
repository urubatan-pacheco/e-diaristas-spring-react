package br.com.urubatanpacheco.ediaristas.core.exceptions;

import javax.persistence.EntityNotFoundException;

public class UsuarioNaoEncontradoException extends EntityNotFoundException {

    public UsuarioNaoEncontradoException(String message) {
        super(message);
    }
    
}

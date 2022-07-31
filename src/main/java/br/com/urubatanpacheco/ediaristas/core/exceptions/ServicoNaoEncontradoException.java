package br.com.urubatanpacheco.ediaristas.core.exceptions;

import javax.persistence.EntityNotFoundException;

public class ServicoNaoEncontradoException extends EntityNotFoundException {
    public ServicoNaoEncontradoException( String message) {
        super(message);
    }
}

package br.com.urubatanpacheco.ediaristas.core.exceptions;

import javax.persistence.EntityNotFoundException;

public class DiariaNaoEncontrada  extends EntityNotFoundException  {

    public DiariaNaoEncontrada(String message) {
        super(message);
    }
    
}

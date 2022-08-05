package br.com.urubatanpacheco.ediaristas.core.exceptions;

import org.springframework.validation.FieldError;

public class SenhaIncorretaException extends ValidacaoException {

    public SenhaIncorretaException(String message, FieldError fieldError) {
        super(message, fieldError);
    }
}


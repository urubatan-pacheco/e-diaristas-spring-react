package br.com.urubatanpacheco.ediaristas.core.exceptions;

import org.springframework.validation.FieldError;

public class EmailExistenteException extends ValidacaoException {

    public EmailExistenteException(String message, FieldError fieldError) {
        super(message, fieldError);
    }
    
}

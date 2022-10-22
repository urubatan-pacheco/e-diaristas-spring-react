package br.com.urubatanpacheco.ediaristas.core.services.token.exceptions;

public class TokenServiceException extends RuntimeException {

    public TokenServiceException() {
    }

    public TokenServiceException(String message) {
        super(message);
    }
    
}

package br.com.urubatanpacheco.ediaristas.core.services.email.adapters;

import br.com.urubatanpacheco.ediaristas.core.services.email.dtos.EmailParams;
import br.com.urubatanpacheco.ediaristas.core.services.email.exceptions.EmailServiceException;

public interface EmailService {
    void enviarEmailComTemplate(EmailParams params) throws EmailServiceException;
}

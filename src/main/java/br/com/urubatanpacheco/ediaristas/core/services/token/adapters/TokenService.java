package br.com.urubatanpacheco.ediaristas.core.services.token.adapters;

import org.springframework.stereotype.Service;

public interface TokenService {
    String gerarAccessToken(String subject);

    String getSubjectDoAccessToken(String  accessToken);
}

package br.com.urubatanpacheco.ediaristas.core.services.token.adapters;

public interface TokenService {
    String gerarAccessToken(String subject);

    String getSubjectDoAccessToken(String  accessToken);
}

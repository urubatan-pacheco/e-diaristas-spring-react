package br.com.urubatanpacheco.ediaristas.api.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Service;

import br.com.urubatanpacheco.ediaristas.api.dtos.requests.TokenRequest;
import br.com.urubatanpacheco.ediaristas.api.dtos.responses.TokenResponse;
import br.com.urubatanpacheco.ediaristas.core.services.token.adapters.TokenService;

@Service
public class ApiAuthService {
    
    @Autowired
    private TokenService tokenService;

    @Autowired
    private AuthenticationManager authenticationManager;


    public TokenResponse autenticar(TokenRequest tokenRequest) {
        var email = tokenRequest.getEmail();
        var senha = tokenRequest.getSenha();

        var autenticacao = new UsernamePasswordAuthenticationToken(email, senha);
        authenticationManager.authenticate(autenticacao);

        var access = tokenService.gerarAccessToken(email);

        return new TokenResponse(access);
    }


}

package br.com.urubatanpacheco.ediaristas.api.services;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import br.com.urubatanpacheco.ediaristas.api.dtos.requests.RefreshRequest;
import br.com.urubatanpacheco.ediaristas.api.dtos.requests.TokenRequest;
import br.com.urubatanpacheco.ediaristas.api.dtos.responses.TokenResponse;
import br.com.urubatanpacheco.ediaristas.core.services.token.adapters.TokenService;

@Service
public class ApiAuthService {
    
    @Autowired
    private TokenService tokenService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserDetailsService userDetailsService;


    public TokenResponse autenticar(TokenRequest tokenRequest) {
        var email = tokenRequest.getEmail();
        var senha = tokenRequest.getSenha();

        var autenticacao = new UsernamePasswordAuthenticationToken(email, senha);
        authenticationManager.authenticate(autenticacao);

        var access = tokenService.gerarAccessToken(email);
        var refresh = tokenService.gerarRefreshToken(email);

        return new TokenResponse(access, refresh);
    }


    public TokenResponse reautenticar(@Valid RefreshRequest refreshRequest) {

        var email = tokenService.getSubjectDoRfreshToken(refreshRequest.getRefresh()); // valida o token e obtem o email

        userDetailsService.loadUserByUsername(email);  // somente para garantir que é um email de um usuário válido

        var access = tokenService.gerarAccessToken(email);
        var refresh = tokenService.gerarRefreshToken(email);

        return new TokenResponse(access, refresh);
    }


}

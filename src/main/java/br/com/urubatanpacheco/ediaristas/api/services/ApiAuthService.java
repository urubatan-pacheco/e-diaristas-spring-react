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
import br.com.urubatanpacheco.ediaristas.core.services.TokenBlackListService;
import br.com.urubatanpacheco.ediaristas.core.services.token.adapters.TokenService;

@Service
public class ApiAuthService {
    
    @Autowired
    private TokenService tokenService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private TokenBlackListService tokenBlackListService;

    public TokenResponse autenticar(TokenRequest tokenRequest) {
        var email = tokenRequest.getEmail();
        var senha = tokenRequest.getPassword();

        var autenticacao = new UsernamePasswordAuthenticationToken(email, senha);
        authenticationManager.authenticate(autenticacao);

        var access = tokenService.gerarAccessToken(email);
        var refresh = tokenService.gerarRefreshToken(email);

        return new TokenResponse(access, refresh);
    }


    public TokenResponse reautenticar(@Valid RefreshRequest refreshRequest) {
        var refreshRequestToken = refreshRequest.getRefresh();
        tokenBlackListService.verificarToken(refreshRequestToken);

        var email = tokenService.getSubjectDoRfreshToken(refreshRequestToken); // valida o token e obtem o email

        userDetailsService.loadUserByUsername(email);  // somente para garantir que é um email de um usuário válido

        var access = tokenService.gerarAccessToken(email);
        var refresh = tokenService.gerarRefreshToken(email);

        tokenBlackListService.colocarTokenNaBlackList(refreshRequestToken);
        return new TokenResponse(access, refresh);
    }

    public void logout(@Valid RefreshRequest refreshRequest) {
        var refreshRequestToken = refreshRequest.getRefresh();

        tokenBlackListService.colocarTokenNaBlackList(refreshRequestToken);
    }
}

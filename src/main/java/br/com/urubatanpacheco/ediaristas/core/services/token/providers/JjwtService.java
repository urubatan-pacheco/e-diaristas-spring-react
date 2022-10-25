package br.com.urubatanpacheco.ediaristas.core.services.token.providers;

import java.time.Instant;
import java.util.Date;
import java.util.HashMap;

import org.springframework.stereotype.Service;

import br.com.urubatanpacheco.ediaristas.core.services.token.adapters.TokenService;
import br.com.urubatanpacheco.ediaristas.core.services.token.exceptions.TokenServiceException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service
public class JjwtService implements TokenService {

    private final Long TEMPO_EXPIRACAO =  30L;
    private final String SECRET_KEY = "chave_access_token";

    @Override
    public String gerarAccessToken(String subject) {
        var claims = new HashMap<String, Object>();
        var dataHoraAtual = Instant.now();
        var dataHoraExpiracao = dataHoraAtual.plusSeconds(TEMPO_EXPIRACAO);

        return Jwts.builder()
            .setClaims(claims)
            .setSubject(subject)
            .setIssuedAt(new Date(dataHoraAtual.toEpochMilli()))
            .setExpiration(new Date(dataHoraExpiracao.toEpochMilli()))
            .signWith(SignatureAlgorithm.HS512, SECRET_KEY)
            .compact();
    }

    @Override
    public String getSubjectDoAccessToken(String accessToken) {
        var claims =  getClaims(accessToken, SECRET_KEY);

        return claims.getSubject();
    }

    private Claims getClaims(String accessToken, String signKey) {
        try {
            return tryGetClaims(accessToken, signKey);
        } catch( JwtException exception ) {
            throw new TokenServiceException(exception.getLocalizedMessage());
        }
    }

    private Claims tryGetClaims(String accessToken, String signKey) {
        return Jwts.parser()
            .setSigningKey(signKey)
            .parseClaimsJws(accessToken)
            .getBody();
    }
    
}

package br.com.urubatanpacheco.ediaristas.core.services.token.providers;

import java.time.Instant;
import java.util.Date;
import java.util.HashMap;

import org.springframework.stereotype.Service;

import br.com.urubatanpacheco.ediaristas.core.services.token.adapters.TokenService;
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
        var claims =  Jwts.parser()
            .setSigningKey(SECRET_KEY)
            .parseClaimsJws(accessToken)
            .getBody();

        return claims.getSubject();
    }
    
}

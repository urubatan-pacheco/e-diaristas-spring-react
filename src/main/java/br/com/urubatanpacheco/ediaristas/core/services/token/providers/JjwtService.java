package br.com.urubatanpacheco.ediaristas.core.services.token.providers;

import java.time.Instant;
import java.util.Date;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import br.com.urubatanpacheco.ediaristas.core.services.token.adapters.TokenService;
import br.com.urubatanpacheco.ediaristas.core.services.token.exceptions.TokenServiceException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service
public class JjwtService implements TokenService {

    @Value("${br.com.urubatanpacheco.ediaristas.refresh.expiration}")
    private Integer refreshExpiration;

    @Value("${br.com.urubatanpacheco.ediaristas.refresh.key}")
    private String refreshKey;


    @Value("${br.com.urubatanpacheco.ediaristas.access.expiration}")
    private Integer accessExpiration;

    @Value("${br.com.urubatanpacheco.ediaristas.access.key}")
    private String accessKey;

    @Override
    public String gerarAccessToken(String subject) {
        return gerarToken(accessKey, accessExpiration, subject);
    }

    private String gerarToken(String key, int expiration, String subject) {
        var claims = new HashMap<String, Object>();
        var dataHoraAtual = Instant.now();
        var dataHoraExpiracao = dataHoraAtual.plusSeconds(expiration);

        return Jwts.builder()
            .setClaims(claims)
            .setSubject(subject)
            .setIssuedAt(new Date(dataHoraAtual.toEpochMilli()))
            .setExpiration(new Date(dataHoraExpiracao.toEpochMilli()))
            .signWith(SignatureAlgorithm.HS512, key)
            .compact();
    }

    @Override
    public String getSubjectDoAccessToken(String accessToken) {
        var claims =  getClaims(accessToken, accessKey);

        return claims.getSubject();
    }

    

    @Override
    public String gerarRefreshToken(String subject) {
        return gerarToken(refreshKey, refreshExpiration, subject);
    }

    @Override
    public String getSubjectDoRfreshToken(String refreshToken) {
        var claims =  getClaims(refreshToken, refreshKey);

        return claims.getSubject();
    }

    private Claims getClaims(String token, String signKey) {
        try {
            return tryGetClaims(token, signKey);
        } catch( JwtException exception ) {
            throw new TokenServiceException(exception.getLocalizedMessage());
        }
    }

    private Claims tryGetClaims(String token, String signKey) {
        return Jwts.parser()
            .setSigningKey(signKey)
            .parseClaimsJws(token)
            .getBody();
    }
    
}

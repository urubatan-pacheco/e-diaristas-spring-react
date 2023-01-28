package br.com.urubatanpacheco.ediaristas.core.filters;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.urubatanpacheco.ediaristas.api.dtos.responses.ErrorTokenResponse;
import br.com.urubatanpacheco.ediaristas.core.services.token.adapters.TokenService;
import br.com.urubatanpacheco.ediaristas.core.services.token.exceptions.TokenServiceException;

@Component
public class AccessTokenRequestFilter extends OncePerRequestFilter {



    private final static String TOKEN_TYPE = "Bearer ";

    private final static String AUTHORIZATION_HEADER_FIELD_NAME = "Authorization";
    

    @Autowired
    private TokenService tokenService;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private ObjectMapper objectMapper;

    @Override
    protected void doFilterInternal(
        HttpServletRequest request, 
        HttpServletResponse response, 
        FilterChain filterChain
        ) throws ServletException, IOException {
            try {
                tryDoFilterInternal(request, response, filterChain);
            } catch (TokenServiceException exception) {
                var status = HttpStatus.UNAUTHORIZED; // 401
                var errorResponse =  ErrorTokenResponse
                    .builder()
                    .status(status.value())
                    .code(ErrorTokenResponse.TOKEN_NOT_VALID)
                    .timestamp(LocalDateTime.now())
                    .message(exception.getLocalizedMessage())
                    .path(request.getRequestURI())
                    .build();
                var json = objectMapper.writeValueAsString(errorResponse);

                response.setStatus(status.value());
                response.setHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE); 
                response.setCharacterEncoding(StandardCharsets.UTF_8.name());
                response.getWriter().write(json);                   
            }
        
    }


    private void tryDoFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws IOException, ServletException {
        var token = "";
        var email = "";

        System.out.println("AccessTokenRequest.doFilter request.requestURI:"+request.getRequestURI());
        var authorizationHeader = request.getHeader(AUTHORIZATION_HEADER_FIELD_NAME);

        System.out.println("AccessTokenRequest.doFilter (authorization):("+authorizationHeader+")");
        if (isTokenPresente(authorizationHeader)) {
            token = authorizationHeader.substring(TOKEN_TYPE.length());
            System.out.println("AccessTokenRequest.doFilter token:"+token);

            email = tokenService.getSubjectDoAccessToken(token);
            System.out.println("AccessTokenRequest.doFilter email:"+email);

        }

        if (isEmailNotInContext(email)) {
            addEmailInContext(request, email);
           System.out.println("AccessTokenRequest.doFilter added to context email:"+email);

        }

        filterChain.doFilter(request, response);
    }


    private Boolean isTokenPresente(String authorizationHeader) {
       System.out.println("AccessTokenRequest.doFilter (TOKEN_TYPE):("+TOKEN_TYPE+")");
       System.out.println("AccessTokenRequest.doFilter added to (authorizationHeader != null) "+(authorizationHeader != null)+ " authorizationHeader.startsWith(TOKEN_TYPE) " +(authorizationHeader != null && authorizationHeader.startsWith(TOKEN_TYPE)));
        return authorizationHeader != null && authorizationHeader.startsWith(TOKEN_TYPE);
    }

    private Boolean isEmailNotInContext(String email) {
        return (!email.isEmpty()) && SecurityContextHolder.getContext().getAuthentication() == null;
    }

    private void addEmailInContext(HttpServletRequest request,  String email) {

        var usuario = userDetailsService.loadUserByUsername(email);

        var autenticacao =  new UsernamePasswordAuthenticationToken(usuario, null, usuario.getAuthorities());

        autenticacao.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(autenticacao);
    }
    
}

package br.com.urubatanpacheco.ediaristas.api.handlers;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.urubatanpacheco.ediaristas.api.dtos.responses.ErrorResponse;

@Component
public class TokenAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public void commence(
        HttpServletRequest request, 
        HttpServletResponse response,
        AuthenticationException authException
    ) throws IOException, ServletException {
        var status = HttpStatus.UNAUTHORIZED; // 401
        var errorResponse =  ErrorResponse
            .builder()
            .status(status.value())
            .timestamp(LocalDateTime.now())
            .message(authException.getLocalizedMessage())
            .path(request.getRequestURI())
            .build();
        var json = objectMapper.writeValueAsString(errorResponse);

        response.setStatus(status.value());
        response.setHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE); 
        response.setCharacterEncoding(StandardCharsets.UTF_8.name());
        response.getWriter().write(json);        
    }
    
}

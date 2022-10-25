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
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.urubatanpacheco.ediaristas.api.dtos.responses.ErrorResponse;

@Component
public class TokenAccessDeniedHandler implements AccessDeniedHandler {


    @Autowired
    private ObjectMapper objectMapper;
    
    @Override
    public void handle(
            HttpServletRequest request, 
            HttpServletResponse response,
            AccessDeniedException accessDeniedException
            ) throws IOException, ServletException {
                var status = HttpStatus.FORBIDDEN; // 403
                var errorResponse =  ErrorResponse
                    .builder()
                    .status(status.value())
                    .timestamp(LocalDateTime.now())
                    .message(accessDeniedException.getLocalizedMessage())
                    .path(request.getRequestURI())
                    .build();
                var json = objectMapper.writeValueAsString(errorResponse);

                response.setStatus(status.value());
                response.setHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE); 
                response.setCharacterEncoding(StandardCharsets.UTF_8.name());
                response.getWriter().write(json); 
    }
    
}

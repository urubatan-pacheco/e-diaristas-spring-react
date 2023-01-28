package br.com.urubatanpacheco.ediaristas.api.dtos.responses;

import java.time.LocalDateTime;
import java.util.AbstractMap;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;



@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ErrorTokenResponse {    
    
    public final static String TOKEN_NOT_VALID = "token_not_valid";

    private Integer status;

    private String code;

    private LocalDateTime timestamp;

    private String message;

    private String path;

    private AbstractMap<String, List<String>> errors;
}

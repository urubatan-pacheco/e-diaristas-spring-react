package br.com.urubatanpacheco.ediaristas.api.dtos.requests;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TokenRequest {
    @NotNull
    @Email
    @Size(max = 255)
    @NotEmpty
    private String email;

    @NotNull
    @Size(max = 255)
    @NotEmpty    
    private String password;
    
}

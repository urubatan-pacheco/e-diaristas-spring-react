package br.com.urubatanpacheco.ediaristas.web.dtos;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioEdicaoForm {
    @NotNull
    @Size(min = 3, max = 255)
    private String nomeCompleto; 

    @NotNull
    @Email
    @Size(min = 3, max = 255)
    private String email;

}

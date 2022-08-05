package br.com.urubatanpacheco.ediaristas.web.dtos;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import br.com.urubatanpacheco.ediaristas.web.interfaces.IConfirmacaoSenha;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AlterarSenhaForm implements IConfirmacaoSenha {
    
    @NotNull
    @NotEmpty
    @Size(max = 128)
    private String senhaAntiga;
    
    @NotNull
    @NotEmpty
    @Size(max = 128)    
    private String senha;

    @NotNull
    @NotEmpty
    @Size(max = 128)
    private String confirmacaoSenha;
}

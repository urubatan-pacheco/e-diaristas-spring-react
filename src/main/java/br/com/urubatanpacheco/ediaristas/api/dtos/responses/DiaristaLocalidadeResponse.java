package br.com.urubatanpacheco.ediaristas.api.dtos.responses;



import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DiaristaLocalidadeResponse {

    private String nomeCompleto; 

    private Double reputacao;

    private String urlFotoUsuario;
    
    private String cidade;

    
}

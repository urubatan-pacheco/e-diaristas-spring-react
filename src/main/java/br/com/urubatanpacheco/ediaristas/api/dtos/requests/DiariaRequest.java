package br.com.urubatanpacheco.ediaristas.api.dtos.requests;

import java.math.BigDecimal;
import java.time.LocalDate;

import javax.validation.constraints.Future;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.databind.PropertyNamingStrategies.SnakeCaseStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonNaming(SnakeCaseStrategy.class)
public class DiariaRequest {
    
    @NotNull
    @Future
    private LocalDate dataAtendimento;

    @NotNull    
    @Positive
    private Integer tempoAtendimento;

    @NotNull
    @Positive    
    private BigDecimal preco;

    @NotNull
    @NotEmpty
    @Size(max = 60)
    private String logradouro;

    @NotNull
    @NotEmpty
    @Size(max = 10)
    private String numero;

    @NotNull
    @NotEmpty
    @Size(max = 30)
    private String bairro;

    @Size(max = 100)
    private String complemento;

    @NotNull
    @NotEmpty
    @Size(max = 30)
    private String cidade;

    @NotNull
    @NotEmpty
    @Size(min = 2, max = 2)
    private String estado;

    @NotNull
    @NotEmpty
    @Size(min = 8, max = 8)
    private String cep;

    @NotNull
    @Positive 
    private Integer quantidadeQuartos;

    @NotNull
    @Positive 
    private Integer quantidadeSalas;

    @NotNull
    @Positive 
    private Integer quantidadeCozinhas;

    @NotNull
    @Positive 
    private Integer quantidadeBanheiros;

    @NotNull
    @Positive 
    private Integer quantidadeQuintais;

    @NotNull
    @Positive 
    private Integer quantidadeOutros;

    @Size(max = 255)    
    private String observacoes;

    @Size(max = 255)    
    private String motivoCancelamento;

    @NotNull
    @Positive     
    private Long servico;

}

package br.com.urubatanpacheco.ediaristas.api.dtos.responses;

import java.util.List;

import com.fasterxml.jackson.databind.PropertyNamingStrategies.SnakeCaseStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@JsonNaming(SnakeCaseStrategy.class)
public class DiaristaLocalidadePagedResponse {

    private List<DiaristaLocalidadeResponse> diaristas;

    private Long quantidadeDiaristasRestante;

    public DiaristaLocalidadePagedResponse(List<DiaristaLocalidadeResponse> diaristas, Integer tamanhoPagina, Long totalDiaristas) {
        this.diaristas = diaristas;
        this.quantidadeDiaristasRestante = Math.max(0, totalDiaristas - tamanhoPagina);
    }
}

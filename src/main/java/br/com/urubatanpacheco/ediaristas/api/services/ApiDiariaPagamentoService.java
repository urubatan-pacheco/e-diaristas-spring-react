package br.com.urubatanpacheco.ediaristas.api.services;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import br.com.urubatanpacheco.ediaristas.api.dtos.requests.PagamentoRequest;
import br.com.urubatanpacheco.ediaristas.api.dtos.responses.MensagemResponse;
import br.com.urubatanpacheco.ediaristas.core.enums.DiariaStatus;
import br.com.urubatanpacheco.ediaristas.core.exceptions.DiariaNaoEncontrada;
import br.com.urubatanpacheco.ediaristas.core.models.Diaria;
import br.com.urubatanpacheco.ediaristas.core.repositories.DiariaRepository;

@Service
public class ApiDiariaPagamentoService {

    @Autowired
    private DiariaRepository diariaRepository;

    public MensagemResponse pagar(PagamentoRequest request, Long id) {
        var diaria = buscarDiariaPorId(id);

        diaria.setStatus(DiariaStatus.PAGO);

        diariaRepository.save(diaria);
        return new MensagemResponse("Diária paga com sucesso!");
    }

    private Diaria buscarDiariaPorId(Long id) {
        var mensagem = String.format("Diária com id %d não encontrada", id);
        return diariaRepository.findById(id).orElseThrow(() -> new DiariaNaoEncontrada(mensagem));
        }     
}

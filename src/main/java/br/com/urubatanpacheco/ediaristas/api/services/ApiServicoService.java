package br.com.urubatanpacheco.ediaristas.api.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import br.com.urubatanpacheco.ediaristas.api.dtos.responses.ServicoResponse;
import br.com.urubatanpacheco.ediaristas.api.mappers.ApiServicoMapper;
import br.com.urubatanpacheco.ediaristas.core.models.Servico;
import br.com.urubatanpacheco.ediaristas.core.repositories.ServicoRepository;

@Service
public class ApiServicoService {
    
    @Autowired
    ServicoRepository repository;

    @Autowired
    ApiServicoMapper mapper;

    public List<ServicoResponse> buscarTodos() {
        var servicoSort = Sort.sort(Servico.class);
        var ordenacao = servicoSort.by(Servico::getPosicao).ascending();
        return repository
            .findAll(ordenacao)
            .stream()
            .map(mapper::toServicoResponse)
            .toList();
    }

}

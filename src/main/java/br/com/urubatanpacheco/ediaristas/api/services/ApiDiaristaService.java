package br.com.urubatanpacheco.ediaristas.api.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.urubatanpacheco.ediaristas.api.dtos.responses.DiaristaLocalidadeResponse;
import br.com.urubatanpacheco.ediaristas.api.mappers.ApiDiaristaMapper;
import br.com.urubatanpacheco.ediaristas.core.repositories.UsuarioRepository;
import br.com.urubatanpacheco.ediaristas.core.services.consultaEndereco.adapters.EnderecoService;

@Service
public class ApiDiaristaService {

    @Autowired
    private UsuarioRepository repository;

    @Autowired
    private ApiDiaristaMapper mapper;

    @Autowired
    private EnderecoService enderecoService;

    public List<DiaristaLocalidadeResponse> buscarDiaristasPorCep(String cep) {

        var codigoIbge =  enderecoService.buscarEnderecoPorCep(cep).getIbge();

        return repository
            .findByCidadesAtendidasCodigoIbge(codigoIbge)
            .stream()
            .map(mapper::toDiaristaLocalidadeResponse)
            .toList();
    }

}

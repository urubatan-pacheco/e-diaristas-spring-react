package br.com.urubatanpacheco.ediaristas.api.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import org.springframework.stereotype.Service;

import br.com.urubatanpacheco.ediaristas.api.dtos.responses.DiaristaLocalidadePagedResponse;
import br.com.urubatanpacheco.ediaristas.api.dtos.responses.DisponibilidadeResponse;
import br.com.urubatanpacheco.ediaristas.api.mappers.ApiDiaristaMapper;
import br.com.urubatanpacheco.ediaristas.core.models.Usuario;
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

    public DiaristaLocalidadePagedResponse buscarDiaristasPorCep(String cep) {

        var codigoIbge =  buscarCodigoIbgePorCep(cep);

        final var DIARISTAS_PAGE_INDEX = 0;
        final var DIARISTAS_PAGE_SIZE = 6;
        final var USUARIO_SORT = Sort.sort(Usuario.class);


        var pageable = PageRequest.of(DIARISTAS_PAGE_INDEX , DIARISTAS_PAGE_SIZE, USUARIO_SORT.by(Usuario::getReputacao).descending());


        var diaristasPaginavel = repository.findByCidadesAtendidasCodigoIbge(codigoIbge, pageable);
        var diaristas  = diaristasPaginavel.getContent()
            .stream()
            .map(mapper::toDiaristaLocalidadeResponse)
            .toList();
        
        return new DiaristaLocalidadePagedResponse(diaristas, DIARISTAS_PAGE_SIZE, diaristasPaginavel.getTotalElements());
    }

    private String buscarCodigoIbgePorCep(String cep) {
        return enderecoService.buscarEnderecoPorCep(cep).getIbge();
    }

    public DisponibilidadeResponse verificarDiaristasPorCep(String cep) {
        var codigoIbge =  buscarCodigoIbgePorCep(cep);
        var disponibilidade = repository.existsByCidadesAtendidasCodigoIbge(codigoIbge);
        return new DisponibilidadeResponse(disponibilidade);
    }

}

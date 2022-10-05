package br.com.urubatanpacheco.ediaristas.api.controllers;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.urubatanpacheco.ediaristas.api.dtos.responses.HateoasResponse;


@RestController
@RequestMapping("/api")
public class HomeRestController {

    @GetMapping
    public HateoasResponse home() {

        var listarServicosLink = linkTo(methodOn(ServicoRestController.class)
            .buscarTodos())
            .withRel("listar_servicos")
            .withType("GET");
        
        var enderecoCepLink = linkTo(methodOn(EnderecoRestController.class)
            .buscarEnderecoPorCep(null))
            .withRel("endereco_cep")
            .expand()
            .withType("GET");
        
        var diaristasLocalidadeLink = linkTo(methodOn(DiaristaRestController.class)
            .buscarDiaristasPorCep(null))
            .withRel("diaristas_localidades")
            .expand()
            .withType("GET");

        var verificarDisponibilidadeAtendimentoLink = linkTo(methodOn(DiaristaRestController.class)
            .verificarDiaristasPorCep(null))
            .withRel("verificar_disponibilidade_atendimento")
            .expand()
            .withType("GET");            
            
        var response = new HateoasResponse();

        response.adicionarLinks(
            listarServicosLink, 
            enderecoCepLink, 
            diaristasLocalidadeLink, 
            verificarDisponibilidadeAtendimentoLink);

        return response;
    }
    
}

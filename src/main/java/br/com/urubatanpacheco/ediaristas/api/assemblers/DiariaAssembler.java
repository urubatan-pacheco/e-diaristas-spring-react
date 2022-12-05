package br.com.urubatanpacheco.ediaristas.api.assemblers;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.urubatanpacheco.ediaristas.api.controllers.DiariaPagamentoRestController;
import br.com.urubatanpacheco.ediaristas.api.dtos.responses.DiariaResponse;

import br.com.urubatanpacheco.ediaristas.core.utils.SecurityUtils;

@Component
public class DiariaAssembler implements Assembler<DiariaResponse> {

    @Autowired
    private SecurityUtils securityUtils;

    @Override
    public void adicionarLinks(DiariaResponse resource) {
    
        var id = resource.getId();
        if (securityUtils.isCliente() && resource.isSemPagamento()) {
            var pagarDiariaLink = linkTo(methodOn(DiariaPagamentoRestController.class).pagar(null, id ))
                .withRel("pagar_diaria")
                .withType("POST");
            
            resource.adicionarLinks(pagarDiariaLink);
        } 

        
    }

    @Override
    public void adicionarLinks(List<DiariaResponse> collectionResources) {
        
        
    }


    
}

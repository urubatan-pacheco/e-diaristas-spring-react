package br.com.urubatanpacheco.ediaristas.api.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.urubatanpacheco.ediaristas.api.assemblers.UsuarioAssembler;
import br.com.urubatanpacheco.ediaristas.api.dtos.responses.UsuarioResponse;
import br.com.urubatanpacheco.ediaristas.api.services.ApiMeService;
import br.com.urubatanpacheco.ediaristas.core.permissions.EDiaristasPermissions;

@RestController
@RequestMapping("/api/me")
public class MeRestController {
 
    @Autowired
    private ApiMeService service;

    @Autowired
    private UsuarioAssembler assembler;

    @EDiaristasPermissions.isDiaristaOrCliente
    @GetMapping
    public UsuarioResponse me() {
        var response = service.obterUsuarioLogado();
        assembler.adicionarLinks(response);
        return response;
    }
}

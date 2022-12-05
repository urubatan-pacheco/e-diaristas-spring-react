package br.com.urubatanpacheco.ediaristas.api.controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.urubatanpacheco.ediaristas.api.assemblers.DiariaAssembler;
import br.com.urubatanpacheco.ediaristas.api.dtos.requests.DiariaRequest;
import br.com.urubatanpacheco.ediaristas.api.dtos.responses.DiariaResponse;
import br.com.urubatanpacheco.ediaristas.api.services.ApiDiariaService;
import br.com.urubatanpacheco.ediaristas.core.permissions.EDiaristasPermissions;

@RestController
@RequestMapping("/api/diarias")
public class DiariaRestController {
    
    @Autowired
    private ApiDiariaService service;

    @Autowired
    private DiariaAssembler assembler;

    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)    
    @EDiaristasPermissions.isCliente
    public DiariaResponse cadastrar(@RequestBody @Valid DiariaRequest request) {
        var response = service.cadastrar(request);

        assembler.adicionarLinks(response);

        return response;
    }
}

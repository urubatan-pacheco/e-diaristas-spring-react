package br.com.urubatanpacheco.ediaristas.api.controllers;


import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.urubatanpacheco.ediaristas.api.dtos.requests.UsuarioRequest;
import br.com.urubatanpacheco.ediaristas.api.dtos.responses.UsuarioResponse;
import br.com.urubatanpacheco.ediaristas.api.services.ApiUsuarioService;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioRestController {
    @Autowired
    private ApiUsuarioService servico;
    
    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    public UsuarioResponse cadastrar(@ModelAttribute @Valid UsuarioRequest request) {
        return servico.cadastrar(request);
    }
}

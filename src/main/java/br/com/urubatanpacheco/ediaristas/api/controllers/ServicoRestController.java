package br.com.urubatanpacheco.ediaristas.api.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.urubatanpacheco.ediaristas.api.dtos.responses.ServicoResponse;
import br.com.urubatanpacheco.ediaristas.api.services.ApiServicoService;

@RestController
@RequestMapping("/api/servicos")
public class ServicoRestController {
    @Autowired
    private ApiServicoService servico;

    @GetMapping
    public List<ServicoResponse> buscarTodos() {
        return servico.buscarTodos();
    }
}

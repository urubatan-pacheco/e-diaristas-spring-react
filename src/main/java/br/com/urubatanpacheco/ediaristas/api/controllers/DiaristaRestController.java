package br.com.urubatanpacheco.ediaristas.api.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.urubatanpacheco.ediaristas.api.dtos.responses.DiaristaLocalidadePagedResponse;
import br.com.urubatanpacheco.ediaristas.api.dtos.responses.DisponibilidadeResponse;
import br.com.urubatanpacheco.ediaristas.api.services.ApiDiaristaService;

@RestController
@RequestMapping("/api/diaristas")
public class DiaristaRestController {
    
    @Autowired
    private ApiDiaristaService service;

    @GetMapping("/localidades")
    public DiaristaLocalidadePagedResponse buscarDiaristasPorCep(@RequestParam(required = false) String cep) {

        return  service.buscarDiaristasPorCep(cep);
    }

    @GetMapping("/disponibilidade")
    public DisponibilidadeResponse verificarDiaristasPorCep(@RequestParam(required = false) String cep) {

        return  service.verificarDiaristasPorCep(cep);
    }

}

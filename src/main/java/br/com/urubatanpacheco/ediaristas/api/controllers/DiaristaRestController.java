package br.com.urubatanpacheco.ediaristas.api.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.urubatanpacheco.ediaristas.api.dtos.responses.DiaristaLocalidadeResponse;
import br.com.urubatanpacheco.ediaristas.api.services.ApiDiaristaService;

@RestController
@RequestMapping("/api/diaristas")
public class DiaristaRestController {
    
    @Autowired
    private ApiDiaristaService service;

    @GetMapping("/localidades")
    public List<DiaristaLocalidadeResponse> buscarDiaristasPorCep() {
        return  service.buscarDiaristasPorCep();
    }
}
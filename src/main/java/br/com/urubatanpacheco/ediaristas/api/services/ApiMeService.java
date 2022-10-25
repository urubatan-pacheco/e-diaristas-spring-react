package br.com.urubatanpacheco.ediaristas.api.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import br.com.urubatanpacheco.ediaristas.api.dtos.responses.UsuarioResponse;
import br.com.urubatanpacheco.ediaristas.api.mappers.ApiUsuarioMapper;
import br.com.urubatanpacheco.ediaristas.core.utils.SecurityUtils;

@Service
public class ApiMeService {

    @Autowired
    private SecurityUtils securityUtils;

    @Autowired
    private ApiUsuarioMapper usuarioMapper;

    public UsuarioResponse obterUsuarioLogado() {
        return usuarioMapper.toResponse(securityUtils.getUsuarioLogado());
    }
}

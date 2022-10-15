package br.com.urubatanpacheco.ediaristas.api.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import br.com.urubatanpacheco.ediaristas.api.dtos.requests.UsuarioRequest;
import br.com.urubatanpacheco.ediaristas.api.dtos.responses.UsuarioResponse;
import br.com.urubatanpacheco.ediaristas.api.mappers.ApiUsuarioMapper;
import br.com.urubatanpacheco.ediaristas.core.repositories.UsuarioRepository;
import br.com.urubatanpacheco.ediaristas.core.validators.UsuarioValidator;

@Service
public class ApiUsuarioService {

    @Autowired
    UsuarioRepository repository;

    @Autowired
    ApiUsuarioMapper mapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UsuarioValidator validator;

    public UsuarioResponse cadastrar(UsuarioRequest request) {
        var usuarioParaCadastrar = mapper.toModel(request);

        validator.validar(usuarioParaCadastrar);

        usuarioParaCadastrar.setSenha(passwordEncoder.encode(usuarioParaCadastrar.getSenha()));
        return mapper.toResponse(repository.save(usuarioParaCadastrar));
    }
}

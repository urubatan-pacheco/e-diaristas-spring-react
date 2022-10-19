package br.com.urubatanpacheco.ediaristas.api.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.FieldError;

import br.com.urubatanpacheco.ediaristas.api.dtos.requests.UsuarioRequest;
import br.com.urubatanpacheco.ediaristas.api.dtos.responses.UsuarioResponse;
import br.com.urubatanpacheco.ediaristas.api.mappers.ApiUsuarioMapper;
import br.com.urubatanpacheco.ediaristas.core.exceptions.SenhasNaoConferemException;
import br.com.urubatanpacheco.ediaristas.core.repositories.UsuarioRepository;
import br.com.urubatanpacheco.ediaristas.core.services.storage.adapters.StorageService;
import br.com.urubatanpacheco.ediaristas.core.validators.UsuarioValidator;

@Service
public class ApiUsuarioService {

    @Autowired
    UsuarioRepository repository;

    @Autowired
    StorageService storageService;

    @Autowired
    ApiUsuarioMapper mapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UsuarioValidator validator;

    public UsuarioResponse cadastrar(UsuarioRequest request) {
        validarConfirmacaoSenha(request);

        var usuarioParaCadastrar = mapper.toModel(request);

        validator.validar(usuarioParaCadastrar);

        usuarioParaCadastrar.setSenha(passwordEncoder.encode(usuarioParaCadastrar.getSenha()));

        usuarioParaCadastrar.setFotoDocumento(storageService.salvar(request.getFotoDocumento()));
        
        return mapper.toResponse(repository.save(usuarioParaCadastrar));
    }

    private void validarConfirmacaoSenha(UsuarioRequest obj) {

        var confirmacaoSenha = obj.getPasswordConfirmation();
        if (obj.getPassword().equals(confirmacaoSenha) == false) {
            var mensagem = "Os dois campos de senhas não conferem!";
            var fieldError = new FieldError(obj.getClass().getName(), "passwordConfirmation", confirmacaoSenha, false, null, null, mensagem);

            throw new SenhasNaoConferemException(mensagem, fieldError);
        }

    }
}

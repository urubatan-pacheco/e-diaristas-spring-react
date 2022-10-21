package br.com.urubatanpacheco.ediaristas.api.services;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.FieldError;

import br.com.urubatanpacheco.ediaristas.api.dtos.requests.UsuarioRequest;
import br.com.urubatanpacheco.ediaristas.api.dtos.responses.UsuarioResponse;
import br.com.urubatanpacheco.ediaristas.api.mappers.ApiUsuarioMapper;
import br.com.urubatanpacheco.ediaristas.core.exceptions.SenhasNaoConferemException;
import br.com.urubatanpacheco.ediaristas.core.repositories.UsuarioRepository;
import br.com.urubatanpacheco.ediaristas.core.services.email.adapters.EmailService;
import br.com.urubatanpacheco.ediaristas.core.services.email.dtos.EmailParams;
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

    @Autowired
    private EmailService emailService;

    public UsuarioResponse cadastrar(UsuarioRequest request) {
        validarConfirmacaoSenha(request);

        var usuarioParaCadastrar = mapper.toModel(request);

        validator.validar(usuarioParaCadastrar);

        usuarioParaCadastrar.setSenha(passwordEncoder.encode(usuarioParaCadastrar.getSenha()));

        usuarioParaCadastrar.setFotoDocumento(storageService.salvar(request.getFotoDocumento()));

        if (usuarioParaCadastrar.isDiarista()) {
            usuarioParaCadastrar.setReputacao(calcularReputacaoDiaristas());
        }
        
        var usuarioResponse = mapper.toResponse(repository.save(usuarioParaCadastrar));

        if (usuarioParaCadastrar.isCliente() || usuarioParaCadastrar.isDiarista()) {
            var emailParams = new EmailParams();
            var props = new HashMap<String, Object>();

            props.put("nome", usuarioParaCadastrar.getNomeCompleto());
            props.put("tipoUsuario",usuarioParaCadastrar.getTipoUsuario().name());

            emailParams.setDestinatario(usuarioParaCadastrar.getEmail());
            emailParams.setAssunto("Cadastro realizado com sucesso");
            emailParams.setTemplate("emails/boas-vindas");
            emailParams.setProps(props);

            emailService.enviarEmailComTemplate(emailParams);
        }

        return usuarioResponse;
    }

    private Double calcularReputacaoDiaristas() {
        var reputacao = repository.getMediaRuputacaoDiarista();
        return (reputacao == null || reputacao == 0.0) ? 5.0 : reputacao;
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

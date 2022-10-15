package br.com.urubatanpacheco.ediaristas.core.validators;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.FieldError;

import br.com.urubatanpacheco.ediaristas.core.exceptions.UsuarioJaCadastradoException;
import br.com.urubatanpacheco.ediaristas.core.models.Usuario;
import br.com.urubatanpacheco.ediaristas.core.repositories.UsuarioRepository;

@Component
public class UsuarioValidator {

    @Autowired
    private UsuarioRepository repository;

    public void validar(Usuario usuario) {
        validarEmail(usuario);
        validarCpf(usuario);
        validarChavePix(usuario);
    }

    private void validarChavePix(Usuario usuario) {
        if (repository.isChavePixJaCadastrado(usuario)) {
            var mensagem = "Já existe um usuário cadastrado com esta Chave PIX!";
            var fieldError = new FieldError(usuario.getClass().getName(), "chavePix", usuario.getChavePix(), false, null, null, mensagem);

            throw new UsuarioJaCadastradoException(mensagem, fieldError);
        }

        if (usuario.isDiarista() && usuario.getChavePix() == null)  {
            var mensagem = "Usuário do tipo Diarista precisa ter a Chave PIX!";
            var fieldError = new FieldError(usuario.getClass().getName(), "chavePix", usuario.getChavePix(), false, null, null, mensagem);

            throw new UsuarioJaCadastradoException(mensagem, fieldError);            
        }
    }

    private void validarCpf(Usuario usuario) {
       if (repository.isCpfJaCadastrado(usuario)) {
            var mensagem = "Já existe um usuário cadastrado com este CPF!";
            var fieldError = new FieldError(usuario.getClass().getName(), "cpf", usuario.getCpf(), false, null, null, mensagem);

            throw new UsuarioJaCadastradoException(mensagem, fieldError);
        }

    }

    private void validarEmail(Usuario usuario) {
       if (repository.isEmailJaCadastrado(usuario)) {
            var mensagem = "Já existe um usuário cadastrado com este e-mail!";
            var fieldError = new FieldError(usuario.getClass().getName(), "email", usuario.getEmail(), false, null, null, mensagem);

            throw new UsuarioJaCadastradoException(mensagem, fieldError);
        }
    }
}

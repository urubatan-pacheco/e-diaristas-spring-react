package br.com.urubatanpacheco.ediaristas.web.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.FieldError;

import br.com.urubatanpacheco.ediaristas.core.enums.TipoUsuario;
import br.com.urubatanpacheco.ediaristas.core.exceptions.SenhasNaoConferemException;

import br.com.urubatanpacheco.ediaristas.core.exceptions.UsuarioNaoEncontradoException;
import br.com.urubatanpacheco.ediaristas.core.models.Usuario;
import br.com.urubatanpacheco.ediaristas.core.repositories.UsuarioRepository;
import br.com.urubatanpacheco.ediaristas.core.validators.UsuarioValidator;
import br.com.urubatanpacheco.ediaristas.web.dtos.AlterarSenhaForm;
import br.com.urubatanpacheco.ediaristas.web.dtos.UsuarioCadastroForm;
import br.com.urubatanpacheco.ediaristas.web.dtos.UsuarioEdicaoForm;
import br.com.urubatanpacheco.ediaristas.web.interfaces.IConfirmacaoSenha;
import br.com.urubatanpacheco.ediaristas.web.mappers.WebUsuarioMapper;

@Service
public class WebUsuarioService {
    @Autowired
    private UsuarioRepository repository;

    @Autowired
    private WebUsuarioMapper mapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UsuarioValidator validator;

    public List<Usuario> buscarTodos() {
        return repository.findAll();
    }

    public Usuario buscarPorId(Long id) {
        return repository.findById(id)
        .orElseThrow(() -> new UsuarioNaoEncontradoException(String.format("Usuário com ID %d não encontrado!", id)));
    }

    public Usuario buscarPorEmail(String email) {
        return repository.findByEmail(email)
        .orElseThrow(() -> new UsuarioNaoEncontradoException(String.format("Usuário com email %s não encontrado!", email)));
    }

    public UsuarioEdicaoForm buscarFormPorId(Long id) {
        var model = buscarPorId(id);
        return mapper.toForm(model);
    }

    public Usuario cadastrar(UsuarioCadastroForm form) {
        validarConfirmacaoSenha(form);

        var usuario = mapper.toModel(form);

        // encriptar a senha do usuário
        usuario.setSenha( passwordEncoder.encode(form.getSenha()));

        // todo usuário cadastrado no Sistema Administrativo é do tipo ADMIN
        // todos estes usuários poderão realizar todas as tarefas do sistema administrativo
        usuario.setTipoUsuario(TipoUsuario.ADMIN);

        // validar emails não duplicados
        validator.validar(usuario);        
        
        return repository.save(usuario);

    }

    public Usuario editar(UsuarioEdicaoForm form, Long id) {
        var usuario = buscarPorId(id);  

        
        var model = mapper.toModel(form);
        // A edição não altera id, senha e tipoUsuário que devem ser mantidos inalterados
        model.setId(usuario.getId());
        model.setSenha(usuario.getSenha());
        model.setTipoUsuario(usuario.getTipoUsuario());

        // validar emails não duplicados
        validator.validar(model);

        return repository.save(model);
    }

    public void excluir(Long id) {
        var usuario = buscarPorId(id);
        repository.delete(usuario);
    }

    public void alterarSenha(AlterarSenhaForm form, String email) {
        validarConfirmacaoSenha(form);

        var usuario = buscarPorEmail(email);
        var senhaNova = form.getSenha();


        // verificar se senha atual corresponde a senha cadastrada
        var senhaAtualEncoded = usuario.getSenha();
        var senhaAntiga =  form.getSenhaAntiga();

        if (passwordEncoder.matches(senhaAntiga, senhaAtualEncoded)  ==  false) {
            var mensagem = "A senha antiga está incorreta!";
            var fieldError = new FieldError(form.getClass().getName(), "senhaAntiga", senhaAntiga, false, null, null, mensagem);

            throw new SenhasNaoConferemException(mensagem,fieldError);
        }

        usuario.setSenha(passwordEncoder.encode(senhaNova));
        repository.save(usuario);

    }


    private void validarConfirmacaoSenha(IConfirmacaoSenha obj) {

        var confirmacaoSenha = obj.getConfirmacaoSenha();
        if (obj.getSenha().equals(confirmacaoSenha) == false) {
            var mensagem = "Os dois campos de senhas não conferem!";
            var fieldError = new FieldError(obj.getClass().getName(), "confirmacaoSenha", confirmacaoSenha, false, null, null, mensagem);

            throw new SenhasNaoConferemException(mensagem, fieldError);
        }

    }
}

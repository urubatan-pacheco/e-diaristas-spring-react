package br.com.urubatanpacheco.ediaristas.core.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import br.com.urubatanpacheco.ediaristas.core.exceptions.UsuarioNaoEncontradoException;
import br.com.urubatanpacheco.ediaristas.core.models.Usuario;
import br.com.urubatanpacheco.ediaristas.core.repositories.UsuarioRepository;

@Component
public class SecurityUtils {
    @Autowired
    private UsuarioRepository usuarioRepository;


    public Authentication getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }

    public String getEmailUsuarioLogado() {
        return getAuthentication().getName();
    }

    public Usuario getUsuarioLogado() {
        var email = getEmailUsuarioLogado();
        return usuarioRepository.findByEmail(email)
            .orElseThrow(() -> new UsuarioNaoEncontradoException(String.format("Usuário com email %s não encontrado", email)));
    }
 }

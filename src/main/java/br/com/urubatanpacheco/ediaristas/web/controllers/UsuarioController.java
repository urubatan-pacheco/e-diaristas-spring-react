package br.com.urubatanpacheco.ediaristas.web.controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.com.urubatanpacheco.ediaristas.web.dtos.FlashMessage;
import br.com.urubatanpacheco.ediaristas.web.dtos.UsuarioCadastroForm;
import br.com.urubatanpacheco.ediaristas.web.services.WebUsuarioService;

@Controller
@RequestMapping("/admin/usuarios")
public class UsuarioController {

    @Autowired
    private WebUsuarioService service;

    @GetMapping
    public ModelAndView buscarTodos() {
        var modelAndView = new ModelAndView("admin/usuario/lista");
        
        modelAndView.addObject("usuarios", service.buscarTodos() );

        return modelAndView;
    }

    @GetMapping("/cadastrar")
    public ModelAndView cadastrar() {
        var modelAndView = new ModelAndView("admin/usuario/cadastro-form");

        modelAndView.addObject("form", new UsuarioCadastroForm() );
        
        return modelAndView;
    }    

    @PostMapping("/cadastrar")
    public String cadastrar(@Valid @ModelAttribute("form") UsuarioCadastroForm form, BindingResult result, RedirectAttributes attrs) {
        if (result.hasErrors()) {
            // cadastro-form.html requires atribute "form", que can recover the previous form value
            return "admin/usuario/cadastro-form";        
        }

        service.cadastrar(form);
        // FlashMessage atrtribute alert tratado no usuario/lista.html
        attrs.addFlashAttribute("alert", new FlashMessage("alert-success","Usuário cadastrado com sucesso!"));

        return "redirect:/admin/usuarios";
    }    

    @GetMapping("/{id}/excluir")
    public String excluir( @PathVariable Long id, RedirectAttributes attrs) {
        service.excluir(id);

        attrs.addFlashAttribute("alert", new FlashMessage("alert-success","Usuário excluido com sucesso!"));

        return "redirect:/admin/usuarios";
    }
}

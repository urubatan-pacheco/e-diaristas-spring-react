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

import br.com.urubatanpacheco.ediaristas.core.enums.Icone;
import br.com.urubatanpacheco.ediaristas.web.dtos.FlashMessage;
import br.com.urubatanpacheco.ediaristas.web.dtos.ServicoForm;
import br.com.urubatanpacheco.ediaristas.web.services.WebServicoService;

@Controller
@RequestMapping("/admin/servicos")
public class ServicoController {

    @Autowired
    private WebServicoService service;
    
    
    @GetMapping
    public ModelAndView buscarTodos() {
        var modelAndView = new ModelAndView("admin/servico/lista");
        
        modelAndView.addObject("servicos", service.buscarTodos() );

        return modelAndView;
    }

    @GetMapping("/cadastrar")
    public ModelAndView cadastrar() {
        var modelAndView = new ModelAndView("admin/servico/form");
        modelAndView.addObject("form", new ServicoForm());

        return modelAndView;
    }

    @PostMapping("/cadastrar")
    public String cadastrar(@Valid @ModelAttribute("form") ServicoForm form, BindingResult result, RedirectAttributes attrs) {
        if (result.hasErrors()) {
            // renderizamos a view form reaproveitando o atributo form da view anterior
            // este form também terá acesso ao result            
            return "admin/servico/form";
        }

        service.cadastrar(form);
        attrs.addFlashAttribute("alert", new FlashMessage("alert-success","Serviço cadastrado com sucesso!"));

        return "redirect:/admin/servicos";
    }

    @GetMapping("/{id}/editar")
    public ModelAndView editar( @PathVariable Long id) {
        var modelAndView = new ModelAndView("admin/servico/form");

        modelAndView.addObject("form", service.buscarPorId(id));

        // quando tivermos a rota listagem será direcionado para lá
        return modelAndView;
    }

    @PostMapping("/{id}/editar")
    public String editar( @PathVariable Long id, @Valid @ModelAttribute("form") ServicoForm form, BindingResult result, RedirectAttributes attrs) {
        if (result.hasErrors()) {
            // renderizamos a view form reaproveitando o atributo form da view anterior
            // este form também terá acesso ao result
            return "admin/servico/form";
        }

        service.editar(form, id);
        attrs.addFlashAttribute("alert", new FlashMessage("alert-success","Serviço editado com sucesso!"));

        return "redirect:/admin/servicos";
    }    

    @GetMapping("/{id}/excluir")
    public String excluir( @PathVariable Long id, RedirectAttributes attrs) {
        service.excluir(id);

        attrs.addFlashAttribute("alert", new FlashMessage("alert-success","Serviço excluido com sucesso!"));

        // quando tivermos a rota listagem será direcionado para lá
        return "redirect:/admin/servicos";
    }

    @ModelAttribute("icones")
    public Icone[] getIcones() {
        return Icone.values();
    }
}

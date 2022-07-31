package br.com.urubatanpacheco.ediaristas.web.mappers;


import br.com.urubatanpacheco.ediaristas.core.models.Servico;
import br.com.urubatanpacheco.ediaristas.web.dtos.ServicoForm;


public class WebServicoMapper {
    public static Servico toModel(ServicoForm form) {
        var model = new Servico();

        if (form == null) {
            throw new IllegalArgumentException();
        }

        model.setNome(form.getNome());
        model.setValorMinimo(form.getValorMinimo());
        model.setQtdHoras(form.getQtdHoras());
        model.setPorcentagemComissao(form.getPorcentagemComissao());
        model.setHorasQuarto(form.getHorasQuarto());
        model.setValorQuarto(form.getValorQuarto());
        model.setHorasSala(form.getHorasSala());
        model.setValorSala(form.getValorSala());
        model.setValorBanheiro(form.getValorBanheiro());
        model.setHorasBanheiro(form.getHorasBanheiro());
        model.setHorasCozinha(form.getHorasCozinha());
        model.setValorCozinha(form.getValorCozinha());
        model.setHorasQuintal(form.getHorasQuintal());
        model.setValorQuintal(form.getValorQuintal());
        model.setHorasOutros(form.getHorasOutros());
        model.setValorOutros(form.getValorOutros());

        model.setIcone(form.getIcone());
        model.setPosicao(form.getPosicao());

        return model;
    }

    public static ServicoForm toForm(Servico model) {
        var form = new ServicoForm();

        if (model == null) {
            throw new IllegalArgumentException();
        }

        form.setNome(model.getNome());
        form.setValorMinimo(model.getValorMinimo());
        form.setQtdHoras(model.getQtdHoras());
        form.setPorcentagemComissao(model.getPorcentagemComissao());
        form.setHorasQuarto(model.getHorasQuarto());
        form.setValorQuarto(model.getValorQuarto());
        form.setHorasSala(model.getHorasSala());
        form.setValorSala(model.getValorSala());
        form.setValorBanheiro(model.getValorBanheiro());
        form.setHorasBanheiro(model.getHorasBanheiro());
        form.setHorasCozinha(model.getHorasCozinha());
        form.setValorCozinha(model.getValorCozinha());
        form.setHorasQuintal(model.getHorasQuintal());
        form.setValorQuintal(model.getValorQuintal());
        form.setHorasOutros(model.getHorasOutros());
        form.setValorOutros(model.getValorOutros());

        form.setIcone(model.getIcone());
        form.setPosicao(model.getPosicao());

        return form;
    }
}

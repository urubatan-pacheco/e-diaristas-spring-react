package br.com.urubatanpacheco.ediaristas.api.services;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import br.com.urubatanpacheco.ediaristas.api.dtos.requests.DiariaRequest;
import br.com.urubatanpacheco.ediaristas.api.dtos.responses.DiariaResponse;
import br.com.urubatanpacheco.ediaristas.api.mappers.ApiDiariaMapper;
import br.com.urubatanpacheco.ediaristas.core.enums.DiariaStatus;
import br.com.urubatanpacheco.ediaristas.core.models.Diaria;
import br.com.urubatanpacheco.ediaristas.core.repositories.DiariaRepository;
import br.com.urubatanpacheco.ediaristas.core.utils.SecurityUtils;


@Service
public class ApiDiariaService {

    @Autowired
    private DiariaRepository repository;

    @Autowired
    private ApiDiariaMapper mapper;

    @Autowired
    private SecurityUtils securityUtils;

    public DiariaResponse cadastrar(DiariaRequest request) {

        var model = mapper.toModel(request);

        model.setValorComissao(calcularComissao(model));
        model.setCliente(securityUtils.getUsuarioLogado());
        model.setStatus(DiariaStatus.SEM_PAGAMENTO);

        var diaria = repository.save(model);

        return mapper.toResponse(diaria);
    }

    private BigDecimal calcularComissao(Diaria diaria) {
        var servico = diaria.getServico();
        var preco = diaria.getPreco();
        var porcentagemComissao = servico.getPorcentagemComissao();

        return preco.multiply(porcentagemComissao.divide(new BigDecimal(100))).setScale(2);
    }
}

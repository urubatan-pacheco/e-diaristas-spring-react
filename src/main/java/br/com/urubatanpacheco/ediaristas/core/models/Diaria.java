package br.com.urubatanpacheco.ediaristas.core.models;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

import br.com.urubatanpacheco.ediaristas.core.enums.DiariaStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
@ToString(onlyExplicitlyIncluded = true)
public class Diaria extends Auditable {
    @EqualsAndHashCode.Include
    @ToString.Include
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)        
    private LocalDateTime dataAtendimento;

    @Column(nullable = false)        
    private Integer tempoAtendimento;
    
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)            
    private DiariaStatus status;

    @Column(nullable = false)        
    private BigDecimal preco;

    @Column(nullable = false)    
    private BigDecimal valorComissao;

    @Column(nullable = false, length = 60)
    private String logradouro;

    @Column(nullable = false, length = 10) 
    private String numero;

    @Column(nullable = false, length = 30) 
    private String bairro;

    @Column(nullable = true, length = 100) 
    private String complemento;

    @Column(nullable = false, length = 30) 
    private String cidade;

    @Column(nullable = false, length = 2) 
    private String estado;

    @Column(nullable = false, length = 8) 
    private String cep;

    @Column(nullable = false)        
    private String codigoIbge;

    @Column(nullable = false)   
    private Integer quantidadeQuartos;

    @Column(nullable = false)   
    private Integer quantidadeSalas;

    @Column(nullable = false)   
    private Integer quantidadeCozinhas;

    @Column(nullable = false)   
    private Integer quantidadeBanheiros;

    @Column(nullable = false)   
    private Integer quantidadeQuintais;

    @Column(nullable = false)   
    private Integer quantidadeOutros;

    @Column(nullable = true) 
    private String observacoes;

    @Column(nullable = true) 
    private String motivoCancelamento;

    @ManyToOne
    @JoinColumn(nullable = false)  
    private Usuario cliente;

    @ManyToOne
    @JoinColumn(nullable = true)  
    private Usuario diarista;

    @ManyToOne
    @JoinColumn(nullable = false)  
    private Servico servico;

    @ManyToMany
    @JoinTable
    private List<Usuario> candidatos;

}

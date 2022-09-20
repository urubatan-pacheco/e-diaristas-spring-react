package br.com.urubatanpacheco.ediaristas.core.models;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import br.com.urubatanpacheco.ediaristas.core.enums.TipoUsuario;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString(onlyExplicitlyIncluded = true)
public class Usuario {
    
    @EqualsAndHashCode.Include
    @ToString.Include
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)    
    private String nomeCompleto;

    @Column(nullable = false, unique = true)    
    private String email;

    @Column(nullable = false, length = 128)    
    private String senha;

    @Column(nullable = false, length = 8)
    @Enumerated(EnumType.STRING)    
    private TipoUsuario tipoUsuario;

    @Column(nullable = true, unique = true, length = 11)    
    private String cpf;

    @Column(nullable = true)    
    private LocalDate nascimento;

    @Column(nullable = true, length = 11) 
    private String telefone;

    @Column(nullable = true)    
    private Double reputacao;

    @Column(nullable = true, unique = true)    
    private String chavePix;

    @JoinColumn(nullable = true)       
    @OneToOne(fetch = FetchType.LAZY, orphanRemoval = true  )
    private Foto fotoDocumento;


    @JoinColumn(nullable = true)  
    @OneToOne(fetch = FetchType.LAZY, orphanRemoval = true  )       
    private Foto fotoUsuario;


}

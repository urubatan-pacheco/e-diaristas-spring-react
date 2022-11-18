package br.com.urubatanpacheco.ediaristas.web.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import br.com.urubatanpacheco.ediaristas.core.models.Usuario;
import br.com.urubatanpacheco.ediaristas.web.dtos.UsuarioCadastroForm;
import br.com.urubatanpacheco.ediaristas.web.dtos.UsuarioEdicaoForm;

@Mapper(componentModel = "spring")
public interface WebUsuarioMapper {

    WebUsuarioMapper INSTANCE =  Mappers.getMapper(WebUsuarioMapper.class);

    @Mapping(target = "fotoDocumento", ignore = true)
    @Mapping(target = "fotoUsuario", ignore = true)
    @Mapping(target = "chavePix", ignore = true)
    @Mapping(target = "cidadesAtendidas", ignore = true)
    @Mapping(target = "cpf", ignore = true)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "nascimento", ignore = true)
    @Mapping(target = "reputacao", ignore = true)
    @Mapping(target = "telefone", ignore = true)
    @Mapping(target = "tipoUsuario", ignore = true)
    Usuario toModel(UsuarioCadastroForm form);

    @Mapping(target = "chavePix", ignore = true)
    @Mapping(target = "cidadesAtendidas", ignore = true)
    @Mapping(target = "cpf", ignore = true)
    @Mapping(target = "fotoDocumento", ignore = true)
    @Mapping(target = "fotoUsuario", ignore = true)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "nascimento", ignore = true)
    @Mapping(target = "reputacao", ignore = true)
    @Mapping(target = "telefone", ignore = true)
    @Mapping(target = "tipoUsuario", ignore = true)
    @Mapping(target = "senha", ignore = true)
    Usuario toModel(UsuarioEdicaoForm form);
    
    UsuarioEdicaoForm toForm(Usuario model);

    
}

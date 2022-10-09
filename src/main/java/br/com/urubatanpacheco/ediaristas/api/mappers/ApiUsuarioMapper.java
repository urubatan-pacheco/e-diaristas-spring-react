package br.com.urubatanpacheco.ediaristas.api.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import br.com.urubatanpacheco.ediaristas.api.dtos.requests.UsuarioRequest;
import br.com.urubatanpacheco.ediaristas.api.dtos.responses.UsuarioResponse;
import br.com.urubatanpacheco.ediaristas.core.enums.TipoUsuario;
import br.com.urubatanpacheco.ediaristas.core.models.Usuario;

import java.util.stream.Stream;

@Mapper(componentModel = "spring")
public interface ApiUsuarioMapper {
    ApiUsuarioMapper  INSTANCE =  Mappers.getMapper(ApiUsuarioMapper.class);

    //@Mapping(target = "urlFotoUsuario", source = "fotoUsuario.url")
    @Mapping(target = "senha", source = "password")
    Usuario toModel(UsuarioRequest request);

    default TipoUsuario integerToTipoUsuario(Integer valor) {
        return Stream.of(TipoUsuario.values())
        .filter(tipoUsuario -> tipoUsuario.getId().equals(valor))
        .findFirst()
        .orElseThrow(() -> new IllegalArgumentException("Tipo de usuário inválido"));
    }

    @Mapping(target = "tipoUsuario", source = "tipoUsuario.id")
    UsuarioResponse toResponse(Usuario usuario);
}

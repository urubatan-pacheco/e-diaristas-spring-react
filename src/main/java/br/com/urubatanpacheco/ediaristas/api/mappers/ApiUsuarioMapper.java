package br.com.urubatanpacheco.ediaristas.api.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import br.com.urubatanpacheco.ediaristas.api.dtos.requests.UsuarioRequest;
import br.com.urubatanpacheco.ediaristas.api.dtos.responses.UsuarioCadastroResponse;
import br.com.urubatanpacheco.ediaristas.api.dtos.responses.UsuarioResponse;
import br.com.urubatanpacheco.ediaristas.core.enums.TipoUsuario;
import br.com.urubatanpacheco.ediaristas.core.models.Usuario;

import java.util.stream.Stream;

@Mapper(componentModel = "spring")
public interface ApiUsuarioMapper {
    ApiUsuarioMapper  INSTANCE =  Mappers.getMapper(ApiUsuarioMapper.class);

    @Mapping(target = "fotoDocumento", ignore = true)
    @Mapping(target = "cidadesAtendidas", ignore = true)
    @Mapping(target = "fotoUsuario", ignore = true)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "reputacao", ignore = true)
    @Mapping(target = "senha", source = "password")
    Usuario toModel(UsuarioRequest request);

    default TipoUsuario integerToTipoUsuario(Integer valor) {
        return Stream.of(TipoUsuario.values())
        .filter(tipoUsuario -> tipoUsuario.getId().equals(valor))
        .findFirst()
        .orElseThrow(() -> new IllegalArgumentException("Tipo de usuário inválido"));
    }

    @Mapping(target = "tipoUsuario", source = "tipoUsuario.id")
    @Mapping(target = "links", ignore = true)
    UsuarioResponse toResponse(Usuario usuario);

    @Mapping(target = "tipoUsuario", source = "tipoUsuario.id")
    @Mapping(target = "links", ignore = true)
    @Mapping(target = "token", ignore = true)
    UsuarioCadastroResponse toCadastroResponse(Usuario usuario);
}

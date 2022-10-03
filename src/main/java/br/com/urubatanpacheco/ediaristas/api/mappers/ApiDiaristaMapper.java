package br.com.urubatanpacheco.ediaristas.api.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import br.com.urubatanpacheco.ediaristas.api.dtos.responses.DiaristaLocalidadeResponse;
import br.com.urubatanpacheco.ediaristas.core.models.Usuario;

@Mapper(componentModel = "spring")
public interface ApiDiaristaMapper {
    ApiDiaristaMapper INSTANCE =  Mappers.getMapper(ApiDiaristaMapper.class);

    @Mapping(target = "urlFotoUsuario", source = "fotoUsuario.url")
    DiaristaLocalidadeResponse toDiaristaLocalidadeResponse(Usuario model);

}

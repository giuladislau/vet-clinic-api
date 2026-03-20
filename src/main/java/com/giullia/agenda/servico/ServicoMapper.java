package com.giullia.agenda.servico;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ServicoMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "ativo", ignore = true)
    Servico toEntity(ServicoRequest request);

    ServicoResponse toResponse(Servico entity);
}

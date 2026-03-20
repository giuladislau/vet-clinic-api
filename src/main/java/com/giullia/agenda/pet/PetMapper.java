package com.giullia.agenda.pet;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface PetMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "cliente", ignore = true)
    @Mapping(target = "ativo", ignore = true)
    Pet toEntity(PetRequest request);

    @Mapping(source = "cliente.id", target = "clienteId")
    PetResponse toResponse(Pet entity);
}

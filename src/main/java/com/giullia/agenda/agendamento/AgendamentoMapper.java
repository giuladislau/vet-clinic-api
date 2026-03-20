package com.giullia.agenda.agendamento;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AgendamentoMapper {

    @Mapping(source = "pet.id", target = "petId")
    @Mapping(source = "servico.id", target = "servicoId")
    AgendamentoResponse toResponse(Agendamento entity);
}

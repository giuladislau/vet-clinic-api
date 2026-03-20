package com.giullia.agenda.agendamento;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class AgendamentoRequest {

    @NotNull
    private Long petId;

    @NotNull
    private Long servicoId;

    @NotNull
    @Future
    private LocalDateTime dataHora;
}

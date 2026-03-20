package com.giullia.agenda.agendamento;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
public class AgendamentoResponse {

    private Long id;
    private Long petId;
    private Long servicoId;
    private StatusAgendamento status;
    private LocalDateTime dataHora;
}

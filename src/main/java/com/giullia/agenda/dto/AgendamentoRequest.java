package com.giullia.agenda.dto;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class AgendamentoRequest {

    private Long petId;
    private Long servicoId;
    private LocalDateTime dataHora;
}
package com.giullia.agenda.repository;

import com.giullia.agenda.entity.Agendamento;
import com.giullia.agenda.entity.Servico;
import com.giullia.agenda.entity.StatusAgendamento;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;

public interface AgendamentoRepository extends JpaRepository<Agendamento, Long> {

    boolean existsByServicoAndDataHoraAndStatus(
            Servico servico,
            LocalDateTime dataHora,
            StatusAgendamento status
    );
}
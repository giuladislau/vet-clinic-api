package com.giullia.agenda.agendamento;

import com.giullia.agenda.servico.Servico;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;

public interface AgendamentoRepository extends JpaRepository<Agendamento, Long> {

    boolean existsByServicoAndDataHoraAndStatus(
            Servico servico,
            LocalDateTime dataHora,
            StatusAgendamento status
    );

    Page<Agendamento> findAll(Pageable pageable);
}

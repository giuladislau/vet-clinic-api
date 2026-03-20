package com.giullia.agenda.servico;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ServicoRepository extends JpaRepository<Servico, Long> {

    Optional<Servico> findByIdAndAtivoTrue(Long id);

    Page<Servico> findAllByAtivoTrue(Pageable pageable);
}

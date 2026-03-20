package com.giullia.agenda.pet;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PetRepository extends JpaRepository<Pet, Long> {

    Optional<Pet> findByIdAndAtivoTrue(Long id);

    Page<Pet> findAllByAtivoTrue(Pageable pageable);
}

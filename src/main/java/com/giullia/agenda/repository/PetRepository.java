package com.giullia.agenda.repository;

import com.giullia.agenda.entity.Pet;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PetRepository extends JpaRepository<Pet, Long> {
}
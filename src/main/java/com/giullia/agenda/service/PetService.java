package com.giullia.agenda.service;

import com.giullia.agenda.entity.Pet;
import com.giullia.agenda.repository.PetRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PetService {

    private final PetRepository repository;

    public Pet salvar(Pet pet) {
        return repository.save(pet);
    }

    public List<Pet> listar() {
        return repository.findAll();
    }

    public Pet buscar(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pet não encontrado"));
    }

    public Pet atualizar(Long id, Pet petAtualizado) {

        Pet pet = buscar(id);

        pet.setNome(petAtualizado.getNome());
        pet.setEspecie(petAtualizado.getEspecie());
        pet.setRaca(petAtualizado.getRaca());
        pet.setIdade(petAtualizado.getIdade());
        pet.setPeso(petAtualizado.getPeso());

        return repository.save(pet);
    }

    public void deletar(Long id) {
        repository.deleteById(id);
    }
}
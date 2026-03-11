package com.giullia.agenda.controller;

import com.giullia.agenda.entity.Pet;
import com.giullia.agenda.service.PetService;
import com.giullia.agenda.dto.PetRequest;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;

import java.util.List;

@RestController
@RequestMapping("/pets")
@RequiredArgsConstructor
public class PetController {

    private final PetService service;

    @PostMapping
    public ResponseEntity<Pet> criar(@RequestBody PetRequest request) {

        Pet pet = Pet.builder()
                .nome(request.getNome())
                .especie(request.getEspecie())
                .raca(request.getRaca())
                .idade(request.getIdade())
                .peso(request.getPeso())
                .build();

        return ResponseEntity.ok(service.salvar(pet));
    }

    @GetMapping
    public List<Pet> listar() {
        return service.listar();
    }

    @GetMapping("/{id}")
    public Pet buscar(@PathVariable Long id) {
        return service.buscar(id);
    }

    @PutMapping("/{id}")
    public Pet atualizar(@PathVariable Long id, @RequestBody Pet pet) {
        return service.atualizar(id, pet);
    }

    @DeleteMapping("/{id}")
    public void deletar(@PathVariable Long id) {
        service.deletar(id);
    }
}
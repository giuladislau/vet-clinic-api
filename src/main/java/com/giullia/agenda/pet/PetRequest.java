package com.giullia.agenda.pet;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class PetRequest {

    @NotBlank
    private String nome;

    @NotBlank
    private String especie;

    private String raca;

    private Integer idade;

    private Double peso;

    @NotNull
    private Long clienteId;
}

package com.giullia.agenda.dto;

import lombok.Data;

@Data
public class PetRequest {

    private String nome;
    private String especie;
    private String raca;
    private Integer idade;
    private Double peso;
}
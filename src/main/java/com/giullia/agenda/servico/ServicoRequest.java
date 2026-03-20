package com.giullia.agenda.servico;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class ServicoRequest {

    @NotBlank
    private String nome;

    private String descricao;

    @NotNull
    private BigDecimal preco;

    @NotNull
    private Integer duracaoMinutos;
}

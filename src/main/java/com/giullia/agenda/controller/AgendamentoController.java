package com.giullia.agenda.controller;

import com.giullia.agenda.entity.Agendamento;
import com.giullia.agenda.service.AgendamentoService;
import com.giullia.agenda.dto.AgendamentoRequest;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/agendamentos")
@RequiredArgsConstructor
public class AgendamentoController {

    private final AgendamentoService service;

    @PostMapping
    public Agendamento criar(@RequestBody AgendamentoRequest request) {
        return service.criar(
                request.getPetId(),
                request.getServicoId(),
                request.getDataHora());
    }

    @GetMapping
    public List<Agendamento> listar() {
        return service.listar();
    }

    @PutMapping("/{id}/cancelar")
    public void cancelar(@PathVariable Long id) {
        service.cancelar(id);
    }
}
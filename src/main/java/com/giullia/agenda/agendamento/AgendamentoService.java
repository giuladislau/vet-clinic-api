package com.giullia.agenda.agendamento;

import com.giullia.agenda.common.exception.HorarioIndisponivelException;
import com.giullia.agenda.common.exception.RegraDeNegocioException;
import com.giullia.agenda.common.exception.RecursoNaoEncontradoException;
import com.giullia.agenda.pet.Pet;
import com.giullia.agenda.pet.PetService;
import com.giullia.agenda.servico.Servico;
import com.giullia.agenda.servico.ServicoService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AgendamentoService {

    private final AgendamentoRepository repository;
    private final AgendamentoMapper mapper;
    private final PetService petService;
    private final ServicoService servicoService;

    @Transactional
    public AgendamentoResponse criar(AgendamentoRequest request) {
        Pet pet = petService.buscarEntidade(request.getPetId());
        Servico servico = servicoService.buscarEntidade(request.getServicoId());

        // Regra de conflito: serviço + horário + status AGENDADO
        if (repository.existsByServicoAndDataHoraAndStatus(
                servico, request.getDataHora(), StatusAgendamento.AGENDADO)) {
            throw new HorarioIndisponivelException(
                    "Serviço já agendado para o horário " + request.getDataHora());
        }

        Agendamento agendamento = Agendamento.builder()
                .pet(pet)
                .servico(servico)
                .dataHora(request.getDataHora())
                .status(StatusAgendamento.AGENDADO)
                .build();

        return mapper.toResponse(repository.save(agendamento));
    }

    @Transactional(readOnly = true)
    public Page<AgendamentoResponse> listar(Pageable pageable) {
        return repository.findAll(pageable)
                .map(mapper::toResponse);
    }

    @Transactional
    public AgendamentoResponse cancelar(Long id) {
        Agendamento agendamento = repository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException(
                        "Agendamento com id " + id + " não encontrado"));

        // Idempotência: já cancelado → retorna sem erro
        if (agendamento.getStatus() == StatusAgendamento.CANCELADO) {
            return mapper.toResponse(agendamento);
        }

        // Não pode cancelar serviço já concluído
        if (agendamento.getStatus() == StatusAgendamento.CONCLUIDO) {
            throw new RegraDeNegocioException(
                    "Não é possível cancelar um agendamento já concluído");
        }

        agendamento.setStatus(StatusAgendamento.CANCELADO);
        return mapper.toResponse(repository.save(agendamento));
    }
}

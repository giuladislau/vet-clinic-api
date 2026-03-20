package com.giullia.agenda.servico;

import com.giullia.agenda.common.exception.RecursoNaoEncontradoException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ServicoService {

    private final ServicoRepository repository;
    private final ServicoMapper mapper;

    @Transactional
    public ServicoResponse criar(ServicoRequest request) {
        Servico servico = mapper.toEntity(request);
        servico.setAtivo(true);
        return mapper.toResponse(repository.save(servico));
    }

    @Transactional(readOnly = true)
    public Page<ServicoResponse> listar(Pageable pageable) {
        return repository.findAllByAtivoTrue(pageable)
                .map(mapper::toResponse);
    }

    @Transactional(readOnly = true)
    public ServicoResponse buscarPorId(Long id) {
        return mapper.toResponse(buscarEntidade(id));
    }

    @Transactional
    public void deletar(Long id) {
        Servico servico = buscarEntidade(id);
        servico.setAtivo(false);
        repository.save(servico);
    }

    // Usado por AgendamentoService
    public Servico buscarEntidade(Long id) {
        return repository.findByIdAndAtivoTrue(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Serviço com id " + id + " não encontrado"));
    }
}

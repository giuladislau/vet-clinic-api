package com.giullia.agenda.cliente;

import com.giullia.agenda.common.exception.RecursoNaoEncontradoException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ClienteService {

    private final ClienteRepository repository;
    private final ClienteMapper mapper;

    @Transactional
    public ClienteResponse criar(ClienteRequest request) {
        Cliente cliente = mapper.toEntity(request);
        cliente.setAtivo(true);
        return mapper.toResponse(repository.save(cliente));
    }

    @Transactional(readOnly = true)
    public Page<ClienteResponse> listar(Pageable pageable) {
        return repository.findAllByAtivoTrue(pageable)
                .map(mapper::toResponse);
    }

    @Transactional(readOnly = true)
    public ClienteResponse buscarPorId(Long id) {
        return mapper.toResponse(buscarEntidade(id));
    }

    @Transactional
    public ClienteResponse atualizar(Long id, ClienteRequest request) {
        Cliente cliente = buscarEntidade(id);
        cliente.setNome(request.getNome());
        cliente.setEmail(request.getEmail());
        cliente.setTelefone(request.getTelefone());
        return mapper.toResponse(repository.save(cliente));
    }

    @Transactional
    public void deletar(Long id) {
        Cliente cliente = buscarEntidade(id);
        cliente.setAtivo(false);
        repository.save(cliente);
    }

    // Método interno usado por outros domínios (ex: PetService)
    public Cliente buscarEntidade(Long id) {
        return repository.findByIdAndAtivoTrue(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Cliente com id " + id + " não encontrado"));
    }
}

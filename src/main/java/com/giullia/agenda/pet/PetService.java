package com.giullia.agenda.pet;

import com.giullia.agenda.cliente.Cliente;
import com.giullia.agenda.cliente.ClienteService;
import com.giullia.agenda.common.exception.RecursoNaoEncontradoException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PetService {

    private final PetRepository repository;
    private final PetMapper mapper;
    private final ClienteService clienteService;

    @Transactional
    public PetResponse criar(PetRequest request) {
        Cliente cliente = clienteService.buscarEntidade(request.getClienteId());

        Pet pet = mapper.toEntity(request);
        pet.setCliente(cliente);
        pet.setAtivo(true);

        return mapper.toResponse(repository.save(pet));
    }

    @Transactional(readOnly = true)
    public Page<PetResponse> listar(Pageable pageable) {
        return repository.findAllByAtivoTrue(pageable)
                .map(mapper::toResponse);
    }

    @Transactional(readOnly = true)
    public PetResponse buscarPorId(Long id) {
        return mapper.toResponse(buscarEntidade(id));
    }

    @Transactional
    public PetResponse atualizar(Long id, PetRequest request) {
        Pet pet = buscarEntidade(id);
        Cliente cliente = clienteService.buscarEntidade(request.getClienteId());

        pet.setNome(request.getNome());
        pet.setEspecie(request.getEspecie());
        pet.setRaca(request.getRaca());
        pet.setIdade(request.getIdade());
        pet.setPeso(request.getPeso());
        pet.setCliente(cliente);

        return mapper.toResponse(repository.save(pet));
    }

    @Transactional
    public void deletar(Long id) {
        Pet pet = buscarEntidade(id);
        pet.setAtivo(false);
        repository.save(pet);
    }

    // Usado por AgendamentoService
    public Pet buscarEntidade(Long id) {
        return repository.findByIdAndAtivoTrue(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Pet com id " + id + " não encontrado"));
    }
}

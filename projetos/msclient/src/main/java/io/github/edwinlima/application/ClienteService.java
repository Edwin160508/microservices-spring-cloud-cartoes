package io.github.edwinlima.application;

import io.github.edwinlima.application.representation.ClienteCpfResponse;
import io.github.edwinlima.domain.Cliente;
import io.github.edwinlima.exception.ResourceNotFoundException;
import io.github.edwinlima.infra.repository.ClienteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ClienteService {

    private final ClienteRepository repository;

    @Transactional
    public Cliente save(Cliente cliente){
        return repository.save(cliente);
    }

    public ClienteCpfResponse getByCPF(String cpf){
        var clienteEncontrado = repository.findByCpf(cpf).orElseThrow(() -> new ResourceNotFoundException("Cliente não encontrado pelo cpf: "+cpf+" !"));

        var clienteDto = new ClienteCpfResponse(clienteEncontrado.getNome(), clienteEncontrado.getCpf(), clienteEncontrado.getIdade());
        return clienteDto;
    }


}

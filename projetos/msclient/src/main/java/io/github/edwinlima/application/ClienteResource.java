package io.github.edwinlima.application;

import io.github.edwinlima.application.representation.ClienteCpfResponse;
import io.github.edwinlima.application.representation.ClienteSaveRequest;
import io.github.edwinlima.domain.Cliente;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("clientes")
@RequiredArgsConstructor
@Slf4j
public class ClienteResource {

    private final ClienteService service;

    @GetMapping
    public String getStatus(){
        log.info("Obtendo o status do microservice de cliente");
        return "ok";
    }
    @PostMapping
    public ResponseEntity save(@RequestBody ClienteSaveRequest request){
        var cliente = request.toModel();
        service.save(cliente);
        URI headerLocation = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .query("cpf={cpf}")
                .buildAndExpand(cliente.getCpf())
                .toUri();
        return ResponseEntity.created(headerLocation).build();
    }

    @GetMapping(params = "cpf")
    public ResponseEntity<ClienteCpfResponse> getByCPF(@RequestParam("cpf") String cpf){
        var dadosCliente = service.getByCPF(cpf);
        return ResponseEntity.ok(dadosCliente);
    }
}

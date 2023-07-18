package io.github.edwinlima.mscartoes.application;

import io.github.edwinlima.mscartoes.application.representation.CartaoRequest;
import io.github.edwinlima.mscartoes.application.representation.CartaoResponse;
import io.github.edwinlima.mscartoes.application.representation.CartoesPorClienteResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("cartoes")
@RequiredArgsConstructor
public class CartoesResource {

    private final CartaoService service;
    private final ClienteCartaoService clienteCartaoService;
    @GetMapping
    public String status(){
        return "ok";
    }

    @PostMapping
    public ResponseEntity save(@RequestBody CartaoRequest cartaoRequest){
        service.save(cartaoRequest);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
    @GetMapping(params = "renda")
    public ResponseEntity<List<CartaoResponse>> getCartoesRendaMenorIgual(@RequestParam("renda") Long renda){
        return ResponseEntity.ok(service.getCartoesRendaMenorIgual(renda));
    }

    @GetMapping(params = "cpf")
    public ResponseEntity<List<CartoesPorClienteResponse>> getCartoesByCliente(@RequestParam("cpf")  String cpf){
        return ResponseEntity.ok(clienteCartaoService.listaClienteCartaoPorCpf(cpf));
    }

}

package io.github.edwinlima.msavaliadorcredito.infra.clients;

import io.github.edwinlima.msavaliadorcredito.domain.model.CartaoCliente;
import io.github.edwinlima.msavaliadorcredito.domain.model.CartaoResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(value = "mscartoes", path = "/cartoes")
public interface CartoesResourceClient {
    @GetMapping(params = "cpf")
    ResponseEntity<List<CartaoCliente>> getCartoesByCliente(@RequestParam("cpf") String cpf);
    @GetMapping(params = "renda")
    ResponseEntity<List<CartaoResponse>> getCartoesRendaMenorIgual(@RequestParam("renda") Long renda);
}

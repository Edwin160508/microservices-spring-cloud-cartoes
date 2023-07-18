package io.github.edwinlima.msavaliadorcredito.application;

import io.github.edwinlima.msavaliadorcredito.domain.model.DadosAvaliacao;
import io.github.edwinlima.msavaliadorcredito.domain.model.RetornoDadosAvaliacao;
import io.github.edwinlima.msavaliadorcredito.domain.model.SituacaoCliente;
import io.github.edwinlima.msavaliadorcredito.exceptions.DadosClienteNotFoundException;
import io.github.edwinlima.msavaliadorcredito.exceptions.ErroComunicacaoMicroserviceException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("avaliacoes-credito")
@RequiredArgsConstructor
public class AvaliadorCreditoResource {
    private final AvaliadorCreditoService service;
    @GetMapping
    public String getStatus(){
        return "ok";
    }

    @GetMapping(value = "situacao-cliente",params = "cpf")
    public ResponseEntity consultaSituacaoCliente(@RequestParam("cpf") String cpf){
        try {
            SituacaoCliente situacaoCliente = service.consultaSituacaoCliente(cpf);
            return ResponseEntity.ok(situacaoCliente);
        } catch (DadosClienteNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (ErroComunicacaoMicroserviceException e) {
            return ResponseEntity.status(HttpStatus.resolve(e.getStatus())).body(e.getMessage());
        }

    }

    @PostMapping
    public ResponseEntity realizarAvaliacao(@RequestBody DadosAvaliacao dadosAvaliacao){
        try {
            RetornoDadosAvaliacao retornoDadosAvaliados = service.realizarAvaliacao(dadosAvaliacao.getCpf(), dadosAvaliacao.getRenda());
            return ResponseEntity.ok(retornoDadosAvaliados);
        } catch (DadosClienteNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (ErroComunicacaoMicroserviceException e) {
            return ResponseEntity.status(HttpStatus.resolve(e.getStatus())).body(e.getMessage());
        }
    }
}

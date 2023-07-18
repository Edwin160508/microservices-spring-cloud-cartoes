package io.github.edwinlima.msavaliadorcredito.application;

import feign.FeignException;
import io.github.edwinlima.msavaliadorcredito.domain.model.*;
import io.github.edwinlima.msavaliadorcredito.exceptions.ErroComunicacaoMicroserviceException;
import io.github.edwinlima.msavaliadorcredito.exceptions.DadosClienteNotFoundException;
import io.github.edwinlima.msavaliadorcredito.infra.clients.CartoesResourceClient;
import io.github.edwinlima.msavaliadorcredito.infra.clients.ClienteResourceClient;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AvaliadorCreditoService {

    private final ClienteResourceClient clientesClient;
    private final CartoesResourceClient cartoesClient;
    public SituacaoCliente consultaSituacaoCliente(String cpf)
            throws DadosClienteNotFoundException, ErroComunicacaoMicroserviceException {
        try {
            ResponseEntity<DadosCliente> responseDados = buscaDadosCliente(cpf);
            ResponseEntity<List<CartaoCliente>> responseCartoes = buscaDadosCartoes(cpf);

            return SituacaoCliente
                    .builder()
                    .dadosCliente(responseDados.getBody())
                    .cartoes(responseCartoes.getBody())
                    .build();
        }catch (FeignException.FeignClientException fe){
            int status = fe.status();
            if(HttpStatus.NOT_FOUND.value() == status){
                throw new DadosClienteNotFoundException(cpf);
            }
            throw new ErroComunicacaoMicroserviceException(fe.getMessage(), status);
        }
    }

    public RetornoDadosAvaliacao realizarAvaliacao(String cpf, Long renda)
            throws DadosClienteNotFoundException, ErroComunicacaoMicroserviceException{
        try {
            ResponseEntity<DadosCliente> responseDados = buscaDadosCliente(cpf);
            ResponseEntity<List<CartaoResponse>> responseRenda = cartoesClient.getCartoesRendaMenorIgual(renda);
            List<CartaoAprovado> cartoesAprovados = carregaCartoesAprovados(responseRenda.getBody(), responseDados.getBody());
            return new RetornoDadosAvaliacao(cartoesAprovados);
        }catch (FeignException.FeignClientException fe){
            int status = fe.status();
            if(HttpStatus.NOT_FOUND.value() == status){
                throw new DadosClienteNotFoundException(cpf);
            }
            throw new ErroComunicacaoMicroserviceException(fe.getMessage(), status);
        }
    }

    private List<CartaoAprovado> carregaCartoesAprovados(List<CartaoResponse> cartoes, DadosCliente dadosCliente){
        var cartoesAprovados = cartoes.stream().map(cartao -> {
            BigDecimal limiteBasico = cartao.getLimiteBasico();
            BigDecimal limiteAprovado = calculaLimiteCartao(limiteBasico, dadosCliente);

            CartaoAprovado cartaoAprovado = new CartaoAprovado();
            cartaoAprovado.setCartao(cartao.getNome());
            cartaoAprovado.setBandeira(cartao.getBandeira());
            cartaoAprovado.setLimiteAprovado(limiteAprovado);

            return cartaoAprovado;
        }).collect(Collectors.toList());

        return cartoesAprovados;
    }

    private BigDecimal calculaLimiteCartao(BigDecimal limiteBasico, DadosCliente dadosCliente){
        BigDecimal idadeBD = BigDecimal.valueOf(dadosCliente.getIdade());
        var fator = idadeBD.divide(BigDecimal.valueOf(10));
        return fator.multiply(limiteBasico);
    }

    private ResponseEntity<DadosCliente> buscaDadosCliente(String cpf) {
        return clientesClient.dadosCliente(cpf);
    }

    private ResponseEntity<List<CartaoCliente>> buscaDadosCartoes(String cpf){
       return cartoesClient.getCartoesByCliente(cpf);
    }
}

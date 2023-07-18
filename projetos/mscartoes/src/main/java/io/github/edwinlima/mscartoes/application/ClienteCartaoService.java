package io.github.edwinlima.mscartoes.application;

import io.github.edwinlima.mscartoes.application.representation.CartaoResponse;
import io.github.edwinlima.mscartoes.application.representation.CartoesPorClienteResponse;
import io.github.edwinlima.mscartoes.domain.Cartao;
import io.github.edwinlima.mscartoes.domain.ClienteCartao;
import io.github.edwinlima.mscartoes.infra.ClienteCartaoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ClienteCartaoService {

    private final ClienteCartaoRepository repository;
    List<CartoesPorClienteResponse> listaClienteCartaoPorCpf(String cpf){
        var clienteCardsEntity = repository.findByCpf(cpf);
        var clienteCardsResponseList = carregaListaClienteCartoesResponse(clienteCardsEntity);
        return clienteCardsResponseList;
    }
    private List<CartoesPorClienteResponse> carregaListaClienteCartoesResponse(List<ClienteCartao> entityList){
        var responseList = entityList.stream()
                .map(CartoesPorClienteResponse::toRepresentation)
                .collect(Collectors.toList());
        return responseList;
    }
}

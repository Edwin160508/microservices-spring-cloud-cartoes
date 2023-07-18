package io.github.edwinlima.mscartoes.application;

import io.github.edwinlima.mscartoes.application.representation.CartaoRequest;
import io.github.edwinlima.mscartoes.application.representation.CartaoResponse;
import io.github.edwinlima.mscartoes.domain.Cartao;
import io.github.edwinlima.mscartoes.infra.CartaoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CartaoService {

    private final CartaoRepository repository;

    @Transactional
    public Cartao save(CartaoRequest cartao){
        return repository.save(cartao.toModel());
    }

    public List<CartaoResponse> getCartoesRendaMenorIgual(Long renda){
        var rendaBigDecimal = BigDecimal.valueOf(renda);
        return converteListaCartoesModelToRepresentation(repository.findByRendaLessThanEqual(rendaBigDecimal));
    }

    private List<CartaoResponse> converteListaCartoesModelToRepresentation(List<Cartao> cartoesList){
        var cartoesListResponse = new ArrayList<CartaoResponse>();
        for(Cartao entity : cartoesList){
            var cartaoResponse = new CartaoResponse();
            cartaoResponse.setNome(entity.getNome());
            cartaoResponse.setBandeira(entity.getBandeira().toString());
            cartaoResponse.setRenda(entity.getRenda());
            cartaoResponse.setLimiteBasico(entity.getLimiteBasico());
            cartoesListResponse.add(cartaoResponse);
        }
        return cartoesListResponse;
    }
}

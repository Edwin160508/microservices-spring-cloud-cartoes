package io.github.edwinlima.mscartoes.application.representation;

import io.github.edwinlima.mscartoes.domain.ClienteCartao;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CartoesPorClienteResponse {
    private String nome;
    private String bandeira;
    private BigDecimal limiteLiberado;

    public static CartoesPorClienteResponse toRepresentation(ClienteCartao entity){
        return new CartoesPorClienteResponse(
                entity.getCartao().getNome(),
                entity.getCartao().getBandeira().toString(),
                entity.getLimite()
        );
    }
}

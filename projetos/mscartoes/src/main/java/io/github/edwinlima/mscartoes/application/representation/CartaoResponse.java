package io.github.edwinlima.mscartoes.application.representation;

import io.github.edwinlima.mscartoes.domain.BandeiraCartao;
import lombok.Data;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.math.BigDecimal;
@Data
public class CartaoResponse {

    private String nome;
    private String bandeira;
    private BigDecimal renda;
    private BigDecimal limiteBasico;

}

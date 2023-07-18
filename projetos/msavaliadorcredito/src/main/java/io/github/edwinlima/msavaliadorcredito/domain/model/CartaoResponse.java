package io.github.edwinlima.msavaliadorcredito.domain.model;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class CartaoResponse {
    private String nome;
    private String bandeira;
    private BigDecimal limiteBasico;
}

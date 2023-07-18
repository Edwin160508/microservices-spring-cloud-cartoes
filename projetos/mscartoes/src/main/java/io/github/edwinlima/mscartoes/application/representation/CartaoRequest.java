package io.github.edwinlima.mscartoes.application.representation;

import io.github.edwinlima.mscartoes.domain.BandeiraCartao;
import io.github.edwinlima.mscartoes.domain.Cartao;
import lombok.Data;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.math.BigDecimal;

@Data
public class CartaoRequest {
    private String nome;
    @Enumerated(EnumType.STRING)
    private BandeiraCartao bandeira;
    private BigDecimal renda;
    private BigDecimal limiteBasico;

    public CartaoRequest(String nome,
                         BandeiraCartao bandeira,
                         BigDecimal renda,
                         BigDecimal limiteBasico){
        this.nome = nome;
        this.bandeira = bandeira;
        this.renda = renda;
        this.limiteBasico = limiteBasico;
    }

    public Cartao toModel(){
        return new Cartao( nome, bandeira, renda, limiteBasico);
    }
}

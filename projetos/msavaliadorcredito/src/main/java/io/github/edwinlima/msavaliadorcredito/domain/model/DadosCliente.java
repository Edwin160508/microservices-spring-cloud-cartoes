package io.github.edwinlima.msavaliadorcredito.domain.model;

import lombok.Data;

@Data
public class DadosCliente {
    private String nome;
    private String cpf;
    private Integer idade;
}

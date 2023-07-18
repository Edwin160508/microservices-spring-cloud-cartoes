package io.github.edwinlima.application.representation;

import lombok.Data;

@Data
public class ClienteCpfResponse {
    private String nome;
    private String cpf;
    private Integer idade;

    public ClienteCpfResponse(String nome, String cpf, Integer idade) {
        this.nome = nome;
        this.cpf = cpf;
        this.idade = idade;
    }


}

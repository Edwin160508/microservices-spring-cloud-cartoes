package io.github.edwinlima.application.representation;

import io.github.edwinlima.domain.Cliente;
import lombok.Data;

@Data
public class ClienteSaveRequest {
    private String nome;
    private String cpf;
    private Integer idade;

    public Cliente toModel(){
        return new Cliente(nome, cpf, idade);
    }
}

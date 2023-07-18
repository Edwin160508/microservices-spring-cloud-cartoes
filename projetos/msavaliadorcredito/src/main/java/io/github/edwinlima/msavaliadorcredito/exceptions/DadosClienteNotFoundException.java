package io.github.edwinlima.msavaliadorcredito.exceptions;


public class DadosClienteNotFoundException extends Exception{

    public DadosClienteNotFoundException(String cpf) {
        super("Cliente não encontrado pelo cpf: "+cpf+" informado !");
    }
}

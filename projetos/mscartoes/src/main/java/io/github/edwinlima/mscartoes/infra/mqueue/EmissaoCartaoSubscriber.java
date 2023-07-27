package io.github.edwinlima.mscartoes.infra.mqueue;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.edwinlima.mscartoes.domain.ClienteCartao;
import io.github.edwinlima.mscartoes.domain.DadosSolicitacaoEmissaoCartao;
import io.github.edwinlima.mscartoes.infra.CartaoRepository;
import io.github.edwinlima.mscartoes.infra.ClienteCartaoRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
@Slf4j
public class EmissaoCartaoSubscriber {

    private final CartaoRepository cartaoRepository;
    private final ClienteCartaoRepository clienteCartaoRepository;
    @RabbitListener(queues = "${mq.queues.emissao-cartoes}")
    public void receberSolicitacaoEmissao(@Payload String payload){
        try {
            var mapper = new ObjectMapper();
            DadosSolicitacaoEmissaoCartao dados = mapper.readValue(payload, DadosSolicitacaoEmissaoCartao.class);
            log.info("Buscando cartao.");
            var cartaoEntity = cartaoRepository.findById(dados.getIdCartao()).orElseThrow();

            ClienteCartao clienteCartao = new ClienteCartao();
            clienteCartao.setCartao(cartaoEntity);
            clienteCartao.setCpf(dados.getCpf());
            clienteCartao.setLimite(dados.getLimiteLiberado());
            log.info("Salvando cliente.");
            clienteCartaoRepository.save(clienteCartao);
        }catch (Exception e){
            log.error("Erro ao receber solicitação de cartão: {}", e.getMessage());

        }
    }
}

package br.com.alurafood.avaliacao.avaliacao.amqp;

import br.com.alurafood.avaliacao.avaliacao.dto.PagamentoDTO;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
public class AvaliacaoListener {
    @RabbitListener(queues = "pagamentos.detalhes-avaliacao")
    public void recebeMensagem(@Payload PagamentoDTO pagamento) {
        System.out.println(pagamento.id());
        System.out.println(pagamento.numero());

        if (pagamento.numero().equals("0001")) {
           throw new RuntimeException("Não consegui processar");
        }

        String mensagem = """
                Necessário criar registro de avaliação para o pedido: %s 
                Id do pagamento: %s
                Nome do cliente: %s
                Valor: R$%s
                Status: %s 
                """.formatted(pagamento.pedidoId(),
                pagamento.id(),
                pagamento.nome(),
                pagamento.valor(),
                pagamento.status());

        System.out.println(mensagem);
    }
}

# AluraFood — Microsserviço de Avaliação

Microsserviço independente desenvolvido com **Java, Spring Boot e RabbitMQ**, com foco no estudo de mensageria, consumo assíncrono de mensagens e tratamento de falhas em aplicações distribuídas.

## Tecnologias

- Java 17
- Spring Boot 4.1.1
- Spring AMQP
- RabbitMQ
- Maven
- JUnit 5 / Spring Boot Test

## Conceitos explorados

- Consumo assíncrono de mensagens com `@RabbitListener`.
- Serialização e desserialização de mensagens JSON.
- Configuração de filas, exchanges e bindings.
- Dead Letter Exchange (DLX) e Dead Letter Queue (DLQ).
- Simulação de falhas durante o processamento de mensagens.

## Mensageria com RabbitMQ

O serviço recebe mensagens relacionadas a pagamentos e utiliza essas informações para simular a necessidade de criar registros de avaliação associados aos pedidos.

A infraestrutura AMQP é configurada pela classe `AvaliacaoAMQPConfiguration`.

| Componente | Responsabilidade |
|---|---|
| Exchange `pagamentos.ex` | Distribui mensagens por meio do tipo `fanout`. |
| Fila `pagamentos.detalhes-avaliacao` | Recebe mensagens consumidas pelo listener de avaliação. |
| `AvaliacaoListener` | Consome mensagens e lê os dados do pagamento. |
| Exchange `pagamentos.dlx` | Exchange destinada ao encaminhamento de mensagens rejeitadas. |
| Fila `pagamentos.detalhes-avaliacao-dlq` | Fila destinada a mensagens encaminhadas para tratamento de falhas. |

O listener recebe os dados por meio de um `PagamentoDTO`. Para exercitar cenários de erro, a aplicação lança uma exceção quando o número do pagamento é `0001`.

A configuração de DLX e DLQ permite estudar mecanismos de encaminhamento de mensagens com falha. O comportamento efetivo depende também da política de rejeição e reentrega configurada no consumidor.

## Como executar

**Pré-requisitos:** Java 17 e RabbitMQ em execução.

Clone o repositório:

```bash
git clone https://github.com/pedrobuzolin/alurafood-avaliacao.git
cd alurafood-avaliacao
```

Execute no Windows:

```bash
mvnw.cmd spring-boot:run
```

No Linux ou macOS:

```bash
./mvnw spring-boot:run
```

Certifique-se de que a conexão com o RabbitMQ esteja configurada corretamente nas propriedades da aplicação.

## Objetivo do projeto

Aprofundar conhecimentos em mensageria com RabbitMQ, explorando comunicação assíncrona, configuração de infraestrutura AMQP e cenários de falha no consumo de mensagens, de forma independente dos demais serviços do AluraFood.

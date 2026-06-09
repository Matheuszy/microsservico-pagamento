# Microsservico Pagamento

Microservico de pagamentos do ecossistema Alurafood, responsavel por registrar e gerenciar pagamentos com `PIX`, `CREDITO` e `DEBITO`.

## Visao Geral

Este servico:

- cria pagamentos com validacao de dados;
- lista e consulta pagamentos por ID;
- atualiza um pagamento (recriando o registro);
- remove pagamentos;
- confirma pagamentos e notifica o microservico de pedidos via Feign Client.

Baseado em:

- Java 17
- Spring Boot 4
- Spring Data JPA
- Flyway
- PostgreSQL
- OpenFeign
- Eureka Client

## Estrutura Principal

- `src/main/java/codex/alurafood/pagamento/controller/PagamentoController.java` - endpoints REST
- `src/main/java/codex/alurafood/pagamento/service/PagamentoService.java` - regras de negocio
- `src/main/java/codex/alurafood/pagamento/repository/PagamentoRepository.java` - acesso a dados
- `src/main/java/codex/alurafood/pagamento/http/PedidoClient.java` - comunicacao com servico `pedidos`
- `src/main/resources/db/migration` - scripts Flyway

## Modelo de Dados

Heranca JPA com estrategia `JOINED`:

- tabela base: `pagamentos` (`V1__create_table_pagamento.sql`)
- tabela filha cartao: `pagamentos_cartao` (`V2__create_table_pagamentos_cartao.sql`)
- tabela filha pix: `pagamentos_pix` (`V3__create_table_pagamentos_pix.sql`)

Enums principais:

- `Status`: `CRIADO`, `APROVADO`, `REPROVADO`
- `TipoPagamento`: `CREDITO`, `DEBITO`, `PIX`, `BOLETO`

> Observacao: embora exista `BOLETO` no enum, o request polimorfico atual aceita explicitamente apenas `PIX`, `CREDITO` e `DEBITO` em `PagamentoRequest`.

## Configuracao

Arquivo: `src/main/resources/application.properties`

Configuracoes atuais:

- `spring.application.name=pagamento`
- `server.port=0` (porta aleatoria)
- datasource PostgreSQL:
    - URL: `jdbc:postgresql://localhost:5432/historico`
    - usuario: `postgres`
- `spring.jpa.hibernate.ddl-auto=validate`
- Flyway habilitado
- Eureka:
    - `eureka.client.service-url.defaultZone=http://localhost:8081/eureka`

## Requisitos

- JDK 17+
- Maven Wrapper (ja incluso no projeto)
- PostgreSQL em execucao
- (Opcional) Servidor Eureka para service discovery
- (Opcional) Microservico `pedidos` para fluxo de confirmacao completo

## Como Executar

### 1) Ajuste o banco no `application.properties`

Garanta que o banco `historico` exista e esteja acessivel.

### 2) Suba a aplicacao

```powershell
.\mvnw spring-boot:run
```

Endpoints

Base path: /pagamento
Listar pagamentos
GET /pagamento
Suporta paginacao (page, size, sort)
Buscar pagamento por ID
GET /pagamento/{id}
Cadastrar pagamento
POST /pagamento/cadastrar

Exemplo PIX

{
"valor": 59.90,
"pedidoId": 10,
"formaDePagamento": "PIX"
}

Exemplo Cartao (Credito

{
"valor": 120.00,
"pedidoId": 11,
"formaDePagamento": "CREDITO",
"nome": "Maria Silva",
"numero": "4111111111111111",
"expiracao": "12/2029",
"codigo": "123"
}

Atualizar pagamento

PUT /pagamento/atualizar/{id}
Regra atual:
se status for APROVADO, bloqueia alteracao;
caso contrario, remove o registro anterior e cria um novo com os dados recebidos.
Deletar pagamento
DELETE /pagamento/deletar/{id}
Confirmar pagamento
PATCH /pagamento/{id}/confirmar
Atualiza status para APROVADO e chama PUT /pedidos/{id}/pago no servico pedidos.
Integracao com Pedido
Cliente Feign em PedidoClient:
servico alvo: pedidos
endpoint chamado: PUT /pedidos/{id}/pago


Resposta Padrao

Exemplo de PagamentoResponse:

{
"id": 1,
"valor": 59.90,
"status": "CRIADO",
"formaDePagamento": "PIX"
}

👨‍💻 Autor Desenvolvido por Matheus

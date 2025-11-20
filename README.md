# Desafio-Backend-Itau

Desafio Itaú – API de Contas Bancárias

Descrição:
API REST desenvolvida em Spring Boot 3 com armazenamento em memória (in-memory),
permitindo:
- Cadastro de contas bancárias
- Consulta de saldo por ID
- Transferência de valores entre contas
- Documentação interativa via Swagger OpenAPI

----------------------------------------------------
Tecnologias Utilizadas
----------------------------------------------------
Java 17
Spring Boot 3.2.4
Spring Web
Spring Validation
Spring Actuator
Springdoc OpenAPI 2.6.0
Maven

----------------------------------------------------
Como Executar
----------------------------------------------------
Pré-requisitos:
- Java 17 instalado
- Maven instalado

Comando para rodar o servidor:
./mvnw spring-boot:run

A aplicação ficará disponível em:
http://localhost:8080

----------------------------------------------------
Documentação da API (Swagger)
----------------------------------------------------
Acesse no navegador:
http://localhost:8080/swagger-ui.html
ou
http://localhost:8080/swagger-ui/index.html

----------------------------------------------------
Endpoints Disponíveis
----------------------------------------------------

1) Criar Conta
POST /contas
Exemplo de requisição JSON:

{
  "nome": "João Silva",
  "cpf": "12345678900",
  "saldoInicial": 500.00
}

Resposta de sucesso (200):

{
  "id": "UUID-gerado"
}

2) Consultar Saldo
GET /contas/{id}/saldo
Resposta de sucesso (200):
500.00

3) Transferência Bancária
POST /transferencia
Exemplo de requisição JSON:

{
  "idOrigem": "id-da-conta-1",
  "idDestino": "id-da-conta-2",
  "valor": 100.00
}

Resposta de sucesso (204):

Sem conteúdo (transferência realizada)

----------------------------------------------------
Testes Automatizados
----------------------------------------------------
Para executar os testes:
./mvnw -U test

----------------------------------------------------
Observações
----------------------------------------------------
- Os dados são temporários (armazenamento em memória)
- Ao reiniciar a aplicação, tudo é apagado
- Validações de entrada já implementadas (HTTP 400 em erros)

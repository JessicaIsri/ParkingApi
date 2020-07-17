# ParkingApi

Projeto que visa a criação de uma api capaz de gerenciar o historico de um estacionamento.

# Ferramentas Utilizadas
- Java 14
- Spring Boot 2
- PostgreSql 11

# Configuração:
Para executar é encessario configurar a conexão do banco de dados, através do arquivo src/main/resources/application.properties .


spring.datasource.url= jdbc:postgresql://localhost:5432/{your_database}


spring.datasource.username={your_user}


spring.datasource.password={your_password}


spring.jpa.hibernate.ddl-auto=update


# Chamadas Disponiveis

- GET /estacionamentos/:placa - Traz o historico da placa pesquisada

- POST /estacionamento - Cadastra uma entrada

> Body: {"placa": "AAA-1234"}

> Response: 
> {
>     "Reserva registrada:"50
> }

Necessario ser o padrão "AAA-9999"

- PUT /estacionamento/:reserva/pagamento - Realiza o pagamento

- PUT /estacionamento/:reserva/saida - Realiza a saida, mediante ao pagamento 




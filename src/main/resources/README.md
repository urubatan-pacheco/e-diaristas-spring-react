# Projeto e-diaristas

Projeto desenvolvido durante a imersão Multistack utilizando Java e Spring Boot

## Dependências do projeto

- Spring Boot
- Spring Web MVC
- Thymeleaf
- Spring Data JPA
- Bean Validation

## Dependências de desenvolvimento

- Spring Boot Devtools
- Lombok

## Requisitos

- Java 17
- Maven 3.8

## Como testar

Clone este projeto
```sh
git clone https://github.com/urubatan-pacheco/e-diaristas-spring-react.git
cd e-diaristas-spring-react
```
Atualize as configurações de acesso ao banco de dados no arquivo [application.properties](src/main/resources/application.properties) utilizando como base o arquivo [application.properties.template](src/main/resources/application.properties.template) 

```properties
# Datasource
spring.datasource.url=jdbc:mysql://host:porta/banco_de_dados?useTimezone=true&serverTimezone=America/Sao_Paulo
spring.datasource.username=root
spring.datasource.password=senha

 
# Hibernate e JPA
spring.jpa.hibernate.ddl-auto=update


# Hibernate e JPA - ambiente de desenvolvimento
spring.jpa.show-sql=true

# Thymeleaf - ambiente de desenvolvimento
spring.thymeleaf.cache=false
``` 
Execute o projeto através do Maven
```sh
mvn spring-boot:run
```

Acesse a aplicação http://locahost:8080/admin/servicos

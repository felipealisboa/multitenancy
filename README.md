# Spring Cloud Multitenancy

Spring Cloud Multitenancy é uma library java integrada com o Spring Cloud que implementa a arquitetura multi-tenant, na qual    
uma única instância de um aplicativo de software serve vários clientes.


## Como utilizar?
A versão corrente da library é a "2.17.7.1" e possui suporte a dois tipos de arquitetura multi-tenant:

- Orientada a schema.
- Orientada a repositorio("Collection ou Tabela").


### Modulos

O projeto é subdividido em 4 modulos:
- **spring-cloud-multitenancy-web**: Projeto responsavel por recuperar o tenant do cliente automaticamente interceptando a requisição em projetos Web.
- **spring-cloud-multitenancy-stream**: Projeto responsavel por recuperar o tenant do client automaticamente interceptando uma mensagem da fila em projetos AMQ.
- **spring-cloud-multitenancy-starter-mysql**: Projeto responsavel por implementar a arquitetura multi-tenant no banco MySQL. 
- **spring-cloud-multitenancy-starter-mongo**: Projeto responsavel por implementar a arquitetura multi-tenant no MongoDB.



### Configurações


```
spring.cloud.multitenancy.tenant.field=tenant
spring.cloud.multitenancy.tenant.dns=.meu.dominio

```
Essas duas configurações são utilizadas nos projetos "spring-cloud-multitenancy-web" e "spring-cloud-multitenancy-stream", 
o "field" deve ser preenchido com o nome do campo que contem o tenant do cliente. Nos projetos Web o tenant é extraido através do header ou DNS, 
já nos projetos AMQ o tenant é extraido de um campo da mensagem.

```
spring.cloud.multitenancy.catalog.refresh=1000
spring.cloud.multitenancy.mongodb.strategy=collection
spring.cloud.multitenancy.mysql.strategy=scheme
```

Essas três configurações são utilizadas nos projetos "spring-cloud-multitenancy-starter-mysql" e "spring-cloud-multitenancy-starter-mongo"
definem a estrategia de multi-tenant e o tempo de atualização do catalago de banco de dados.


## Como configurar meu projeto ?
  
  Varia de acordo com o contexto, de modo geral são necessarias duas dependencias, a primeira é referente ao tipo de projeto e a segunda referente ao banco de dados:
  
   EX: Projeto WEB

  ```xml
    <dependency>
		    <groupId>org.springframework.cloud</groupId>
		    <artifactId>spring-cloud-multitenancy-web</artifactId>
		    <version>2.17.7.1</version>
		</dependency>
		
		<dependency>
		    <groupId>org.springframework.cloud</groupId>
		    <artifactId>spring-cloud-multitenancy-starter-mongo</artifactId>
		    <version>2.17.7.1</version>
		</dependency>
  ```
  
  EX: Projeto AMQ
  
  ```xml
    <dependency>
		    <groupId>org.springframework.cloud</groupId>
		    <artifactId>spring-cloud-multitenancy-stream</artifactId>
		    <version>2.17.7.1</version>
		</dependency>
		
		<dependency>
		    <groupId>org.springframework.cloud</groupId>
		    <artifactId>spring-cloud-multitenancy-starter-mysql</artifactId>
		    <version>2.17.7.1</version>
		</dependency>
  ```
  
  Após adicionar as dependencias no pom.xml é necessario adicionar as anotações habilitando o multitenancy
 

 ```java
@SpringBootApplication
@EnableMultitenancyWeb
@EnableMultitenancyMongo
public class ApplicationStart {
	
	public static void main(String[] args) {
		SpringApplication.run(ApplicationStart.class, args);
	}
}
```





### Autor
- Wesley Ramos de Paula
- wesley-ramos-de-paula@hotmail.com

# Spring Cloud Multitenancy

Spring Cloud Multitenancy é uma library java integrada com o Spring Cloud que implementa a arquitetura multi-tenant, na qual    
uma única instância de um aplicativo de software serve vários clientes.


## Como utilizar?
A versão corrente da library é a "2.17.7.1" e possui suporte a dois tipos de arquitetura multi-tenant:

- Orientada a schema.
- Orientada a repositorio("Collection ou Tabela").


### Módulos

O projeto é subdividido em 4 módulos:
- **spring-cloud-multitenancy-web**: Projeto responsável por recuperar o tenant do cliente automaticamente interceptando a requisição em projetos Web.
- **spring-cloud-multitenancy-stream**: Projeto responsável por recuperar o tenant do cliente automaticamente interceptando uma mensagem da fila em projetos AMQ.
- **spring-cloud-multitenancy-starter-mysql**: Projeto responsável por implementar a arquitetura multi-tenant no banco MySQL. 
- **spring-cloud-multitenancy-starter-mongo**: Projeto responsável por implementar a arquitetura multi-tenant no MongoDB.



### Configurações


```
spring.cloud.multitenancy.tenant.field=tenant
spring.cloud.multitenancy.tenant.dns=.meu.dominio

```
Essas duas configurações são utilizadas nos projetos "spring-cloud-multitenancy-web" e "spring-cloud-multitenancy-stream", 
o "field" deve ser preenchido com o nome do campo que contém o tenant do cliente. Nos projetos Web o tenant é extraído através do header ou DNS, 
já nos projetos AMQ o tenant é extraído de um campo da mensagem.

```
spring.cloud.multitenancy.catalog.refresh=1000
spring.cloud.multitenancy.mongodb.strategy=collection
spring.cloud.multitenancy.mysql.strategy=scheme
```

Essas três configurações são utilizadas nos projetos "spring-cloud-multitenancy-starter-mysql" e "spring-cloud-multitenancy-starter-mongo"
definem a estrátegia de multi-tenant e o tempo de atualização do catálago de banco de dados.


## Como configurar meu projeto ?
  
  Varia de acordo com o contexto, de modo geral são necessárias duas dependências, a primeira é referente ao tipo de projeto e a segunda referente ao banco de dados:
  
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
  
  Após adicionar as dependências no pom.xml é necessário adicionar as anotações habilitando o multitenancy
 

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

Por último configurar o catálogo, o qual o multitenancy utiliza para consultar as informações de autenticação

Configurar o bean:

 ```java
@Configuration
public class MultitenancyConfig {
	
	@Bean
    public MultitenancyMongoDataSourceCatalog catalog(){
        return new MultitenancyDatabaseProvider();
    }
}

```
Implementar o catálogo
```java
public class MultitenancyDatabaseProvider implements MultitenancyMongoDataSourceCatalog {

	@Override
	public MultitenancyMongoTenantInformation getDataSourceByTenant(String tenant) {
		return MultitenancyMongoTenantInformationBuilder.builder()
                .url("127.0.0.1")
                .database("database_gps")
                .writeConcern(WriteConcern.ACKNOWLEDGED)
                .connectionsPerHost(10)
                .socketKeepAlive(true)
                .build();
	}
}

```




### Autor
- Wesley Ramos de Paula
- wesley-ramos-de-paula@hotmail.com

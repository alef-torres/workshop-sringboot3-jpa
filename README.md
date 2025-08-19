# Projeto Spring Boot — Web Service com JPA / Hibernate

> API REST de exemplo (e-commerce simples) construída com Spring Boot, JPA/Hibernate e banco de teste H2. Implementa Users, Orders, Products, Categories, OrderItems e Payment. Este README está pronto para ser colocado em `README.md` do seu repositório.

---

## Sumário
- [Sobre](#sobre)  
- [Tecnologias](#tecnologias)  
- [Modelo de domínio (resumo)](#modelo-de-dom%C3%ADnio-resumo)   
- [Entidades principais](#entidades-principais)  
- [Dados de exemplo (seed)](#dados-de-exemplo-seed)  
- [Endpoints principais (exemplos)](#endpoints-principais-exemplos)  
- [Como rodar (H2)](#como-rodar-h2)  
- [Como rodar (PostgreSQL)](#como-rodar-postgresql)  
- [Acessando o H2 Console](#acessando-o-h2-console)    
- [Tratamento de exceções](#tratamento-de-exce%C3%A7%C3%B5es)  
- [Boas práticas / Observações](#boas-pr%C3%A1ticas--observa%C3%A7%C3%B5es)  
- [Contribuindo](#contribuindo)  
- [Licença](#licen%C3%A7a)  

---

# Sobre
Projeto didático que segue o workshop do Dr. Nélio Alves. Serve como base para aprender a construir uma API REST com Spring Boot + JPA/Hibernate, popular o banco com dados iniciais (seeds) e estruturar camadas (Resource/Service/Repository). Ideal para estudo e deploy simples.

---

# Tecnologias
- Java 17  
- Spring Boot (Spring Web, Spring Data JPA)  
- Hibernate (JPA)  
- H2 (banco em memória — perfil `test`)  
- PostgreSQL (opcional, produção)  
- Maven  
- Postman (testes)  

---

# Modelo de domínio (resumo)
- **User** cria **Order**(s)  
- **Order** tem vários **OrderItem** (cada `OrderItem` associa `Order` + `Product` com `quantity` e `price`)  
- **Product** pertence a várias **Category** (Many-to-Many)  
- **Order** tem opcionalmente um **Payment** (One-to-One)  
- **OrderStatus** é um enum: `WAITING_PAYMENT`, `PAID`, `SHIPPED`, `DELIVERED`, `CANCELED`

> Os diagramas de domínio e instância (UML) foram gerados e podem ser colocados em `/docs` (ex.: `domain-model.png`, `domain-instance.png`, `logical-layers.png`).

---

# Entidades principais (resumo)
- **User**: `id`, `name`, `email`, `phone`, `password`  
- **Order**: `id`, `moment: Instant`, `orderStatus: OrderStatus`, `client: User`  
- **Product**: `id`, `name`, `description`, `price`, `imgUrl`  
- **Category**: `id`, `name`  
- **OrderItem**: PK composta (`order`, `product`), `quantity`, `price` -> `subTotal()`  
- **Payment**: `id`, `moment: Instant`, relacionamento One-to-One com Order  
- **OrderStatus**: enum com valores listados acima

---

# Dados de exemplo (seed)
Objetos criados automaticamente no perfil de teste (exemplos):
- Users:
  - Maria Brown — `maria@gmail.com`
  - Alex Green — `alex@gmail.com`
- Orders:
  - `2019-06-20T19:53:07Z` (client: Maria)
  - `2019-07-21T03:42:10Z` (client: Alex)
  - `2019-07-22T15:21:22Z` (client: Maria)
- Categories: Electronics, Books, Computers
- Products: The Lord of the Rings, Smart TV, Macbook Pro, PC Gamer, Rails for Dummies
- Exemplos de OrderItems e um Payment de exemplo ligado ao primeiro pedido.

---

# Endpoints principais (exemplos)
> Ajuste os caminhos aos nomes reais dos seus controllers antes de publicar.

## Users
- `GET /users` — listar
- `GET /users/{id}` — buscar por id
- `POST /users` — criar
- `PUT /users/{id}` — atualizar
- `DELETE /users/{id}` — deletar

Exemplo de body `POST /users`:
```json
{
  "name": "Bob Brown",
  "email": "bob@gmail.com",
  "phone": "977557755",
  "password": "123456"
}
```

## Orders
- `GET /orders`
- `GET /orders/{id}`
- `POST /orders`
- `PUT /orders/{id}`

## Products
- `GET /products`
- `GET /products/{id}`
- `POST /products`
- `PUT /products/{id}`
- `DELETE /products/{id}`

## Categories
- `GET /categories`
- `GET /categories/{id}`

---

# Como rodar (H2 — perfil `test`)
1. Build:
```bash
mvn clean package
```
2. Rodar com perfil `test` (usa H2 em memória e seeds):
```bash
mvn spring-boot:run -Dspring-boot.run.profiles=test
```
ou:
```bash
export SPRING_PROFILES_ACTIVE=test
mvn spring-boot:run
```
O serviço inicia em `http://localhost:8080` por padrão. H2 será populado automaticamente com os dados de seed.

---

# Como rodar (PostgreSQL)
1. Configure um banco PostgreSQL e crie uma database (ex.: `mydb`).  
2. Ajuste `application.properties` (ou `application-prod.properties`) com as credenciais:
```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/mydb
spring.datasource.username=seu_user
spring.datasource.password=sua_senha
spring.jpa.database-platform=org.hibernate.dialect.PostgreSQLDialect
spring.jpa.hibernate.ddl-auto=update
```
3. Rode:
```bash
mvn spring-boot:run
```
ou empacote e rode o JAR:
```bash
mvn clean package
java -jar target/<seu-app>.jar
```

---

# Acessando o H2 Console
- URL: `http://localhost:8080/h2-console`  
- JDBC URL (padrão `application-test.properties`): `jdbc:h2:mem:testdb`  
- User: `sa`  
- Password: (em branco)

---

# Tratamento de exceções
Implementado tratamento global com classes como:
- `ResourceNotFoundException` — 404 quando entidade não existe  
- `DatabaseException` — para violações de integridade (ex.: delete referenciado)  
- `ResourceExceptionHandler` + `StandardError` — padronizam respostas de erro (timestamp, status, mensagem, path)

---

# Boas práticas / Observações
- Use `equals` e `hashCode` baseados em `id`.  
- Para coleções (ex.: `products`, `categories`) exponha apenas getter (evite sobrescrever a coleção).  
- Considere criar DTOs para os endpoints públicos, evitando expor entidades JPA diretamente.  
- Evite ciclos na serialização: `@JsonIgnore` / `@JsonManagedReference` / `@JsonBackReference` conforme necessário.  
- Configure `spring.jpa.show-sql=true` em ambiente de desenvolvimento para debug.

---

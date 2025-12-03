# Order Processing Management API

API REST completa para gerenciamento de pedidos de e-commerce desenvolvida com Spring Boot.

## Funcionalidades

### Produto
- ✅ Criar produto
- ✅ Consultar produto por ID
- ✅ Listar todos os produtos
- ✅ Atualizar produto
- ✅ Excluir produto

### Pedido
- ✅ Criar pedido
- ✅ Consultar pedido por ID
- ✅ Listar todos os pedidos
- ✅ Listar pedidos por cliente
- ✅ Atualizar status do pedido

### Cliente
- ✅ Criar cliente
- ✅ Consultar cliente por ID
- ✅ Listar todos os clientes

## Tecnologias

- **Java 25**
- **Spring Boot 4.0.0**
- **Spring Data JPA**
- **PostgreSQL**
- **Liquibase** (Migrações de banco de dados)
- **SpringDoc OpenAPI (Swagger)** (Documentação da API)
- **Lombok**
- **JUnit 5** (Testes unitários)
- **Mockito**

## Pré-requisitos

- Java 25 ou superior
- Maven 3.6+
- Docker e Docker Compose (para PostgreSQL e WSO2)
- PostgreSQL 15+ (se não usar Docker)

## Configuração e Execução

### 1. Iniciar PostgreSQL e WSO2

```bash
docker-compose up -d
```

### 2. Configurar Banco de Dados

As migrações do Liquibase serão executadas automaticamente na primeira execução.

### 3. Executar a Aplicação

```bash
./mvnw spring-boot:run
```

Ou:

```bash
mvn spring-boot:run
```

A aplicação estará disponível em: `http://localhost:8080`

### 4. Acessar Documentação Swagger

Após iniciar a aplicação, acesse a documentação interativa da API:

- **Swagger UI**: http://localhost:8080/swagger-ui.html
- **API Docs (JSON)**: http://localhost:8080/api-docs
- **API Docs (YAML)**: http://localhost:8080/api-docs.yaml

A interface Swagger permite:
- Visualizar todos os endpoints disponíveis
- Testar os endpoints diretamente no navegador
- Ver exemplos de requisições e respostas
- Entender os modelos de dados utilizados

### 5. Executar Testes

```bash
./mvnw test
```

## Estrutura da API

### Endpoints de Cliente

```
POST   /api/clientes          - Criar cliente
GET    /api/clientes          - Listar todos os clientes
GET    /api/clientes/{id}     - Buscar cliente por ID
```

### Endpoints de Produto

```
POST   /api/produtos          - Criar produto
GET    /api/produtos          - Listar todos os produtos
GET    /api/produtos/{id}     - Buscar produto por ID
PUT    /api/produtos/{id}     - Atualizar produto
DELETE /api/produtos/{id}     - Excluir produto
```

### Endpoints de Pedido

```
POST   /api/pedidos                    - Criar pedido
GET    /api/pedidos                    - Listar todos os pedidos
GET    /api/pedidos/{id}               - Buscar pedido por ID
GET    /api/pedidos/cliente/{clienteId} - Listar pedidos por cliente
PATCH  /api/pedidos/{id}/status        - Atualizar status do pedido
```

## Exemplos de Uso

### Criar Cliente

```bash
curl -X POST http://localhost:8080/api/clientes \
  -H "Content-Type: application/json" \
  -d '{
    "nome": "João Silva",
    "email": "joao@email.com",
    "cpf": "123.456.789-00"
  }'
```

### Criar Produto

```bash
curl -X POST http://localhost:8080/api/produtos \
  -H "Content-Type: application/json" \
  -d '{
    "nome": "Produto Teste",
    "descricao": "Descrição do produto",
    "preco": 99.99,
    "quantidadeEstoque": 100
  }'
```

### Criar Pedido

```bash
curl -X POST http://localhost:8080/api/pedidos \
  -H "Content-Type: application/json" \
  -d '{
    "clienteId": 1,
    "itens": [
      {
        "produtoId": 1,
        "quantidade": 2
      }
    ]
  }'
```

### Atualizar Status do Pedido

```bash
curl -X PATCH http://localhost:8080/api/pedidos/1/status \
  -H "Content-Type: application/json" \
  -d '{
    "status": "CONFIRMADO"
  }'
```

## Status do Pedido

Os status possíveis são:
- `PENDENTE`
- `CONFIRMADO`
- `PREPARANDO`
- `ENVIADO`
- `ENTREGUE`
- `CANCELADO`

## Integração com WSO2 API Manager

O projeto está configurado para usar **WSO2 API Manager 4.5.0**.

Consulte os guias disponíveis:
- **[WSO2_4.5.0_GUIDE.md](./WSO2_4.5.0_GUIDE.md)** - Guia completo para WSO2 4.5.0
- **[WSO2_INTEGRATION.md](./WSO2_INTEGRATION.md)** - Integração geral
- **[RATE_LIMIT_PASSO_A_PASSO_FINAL.md](./RATE_LIMIT_PASSO_A_PASSO_FINAL.md)** - Configurar rate limiting passo a passo

Para instruções sobre como:
- Publicar a API no WSO2 Gateway
- Configurar rate limiting
- Gerar credentials (API Keys)
- Testar através do gateway

## Estrutura do Projeto

```
src/
├── main/
│   ├── java/
│   │   └── com/challenge/OrderProcessingManagement/
│   │       ├── controller/     # Controllers REST
│   │       ├── service/        # Lógica de negócio
│   │       ├── repository/     # Repositórios JPA
│   │       ├── model/          # Entidades JPA
│   │       ├── dto/            # Data Transfer Objects
│   │       └── exception/      # Tratamento de exceções
│   └── resources/
│       ├── db/
│       │   └── changelog/      # Scripts Liquibase
│       └── application.yaml    # Configurações
└── test/
    └── java/                   # Testes unitários
```

## Validações

A API inclui validações para:
- Campos obrigatórios
- Formato de email
- Valores positivos para preços e quantidades
- Estoque disponível ao criar pedidos
- Unicidade de email e CPF de clientes

## Tratamento de Erros

A API retorna respostas padronizadas de erro:
- `400 Bad Request` - Erros de validação
- `404 Not Found` - Recurso não encontrado
- `500 Internal Server Error` - Erros internos

## Banco de Dados

O banco de dados é criado automaticamente via Liquibase com as seguintes tabelas:
- `clientes`
- `produtos`
- `pedidos`
- `itens_pedido`

## Testes

Os testes unitários cobrem:
- Criação, busca e listagem de recursos
- Validações de negócio
- Tratamento de exceções

Execute os testes com:

```bash
./mvnw test
```

## Contribuindo

1. Faça fork do projeto
2. Crie uma branch para sua feature
3. Commit suas mudanças
4. Push para a branch
5. Abra um Pull Request

## Licença

Este projeto é um desafio técnico de desenvolvimento.


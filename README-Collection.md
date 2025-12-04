# Collection Postman - Order Processing Management API

## 📋 Descrição

Esta collection contém todos os endpoints da API de gerenciamento de pedidos, produtos e clientes.

## 🚀 Como Importar

### Postman
1. Abra o Postman
2. Clique em **Import** (canto superior esquerdo)
3. Selecione o arquivo `Order-Processing-Management.postman_collection.json`
4. A collection será importada com todas as requisições organizadas

### Insomnia
1. Abra o Insomnia
2. Clique em **Create** > **Import From** > **File**
3. Selecione o arquivo `Order-Processing-Management.postman_collection.json`
4. A collection será convertida e importada

## ⚙️ Variáveis de Ambiente

A collection utiliza as seguintes variáveis que podem ser configuradas:

- **baseUrl**: URL base da API (padrão: `http://localhost:8080/api`)
- **customerId**: ID do cliente para testes (padrão: `1`)
- **productId**: ID do produto para testes (padrão: `1`)
- **orderId**: ID do pedido para testes (padrão: `1`)

### Como Configurar Variáveis no Postman

1. Clique no nome da collection
2. Vá na aba **Variables**
3. Edite os valores conforme necessário
4. Salve as alterações

## 📚 Endpoints Disponíveis

### 👥 Customers (Clientes)
- **GET** `/customers` - Listar todos os clientes
- **GET** `/customers/{id}` - Buscar cliente por ID
- **POST** `/customers` - Criar novo cliente

### 📦 Products (Produtos)
- **GET** `/products` - Listar todos os produtos
- **GET** `/products/{id}` - Buscar produto por ID
- **POST** `/products` - Criar novo produto
- **PUT** `/products/{id}` - Atualizar produto
- **DELETE** `/products/{id}` - Deletar produto

### 🛒 Orders (Pedidos)
- **GET** `/orders` - Listar todos os pedidos
- **GET** `/orders/{id}` - Buscar pedido por ID
- **GET** `/orders/customer/{customerId}` - Buscar pedidos por cliente
- **POST** `/orders` - Criar novo pedido
- **PATCH** `/orders/{id}/status` - Atualizar status do pedido

## 📝 Exemplos de Uso

### Criar um Cliente
```json
{
  "name": "João Silva",
  "email": "joao.silva@email.com",
  "cpf": "123.456.789-00"
}
```

### Criar um Produto
```json
{
  "name": "Notebook Gamer",
  "description": "Notebook Gamer com placa de vídeo dedicada",
  "price": 2999.99,
  "stockQuantity": 10
}
```

### Criar um Pedido
```json
{
  "customerId": 1,
  "items": [
    {
      "productId": 1,
      "quantity": 2
    },
    {
      "productId": 2,
      "quantity": 1
    }
  ]
}
```

### Atualizar Status do Pedido
```json
{
  "status": "CONFIRMED"
}
```

**Status válidos:**
- `PENDING` - Pendente
- `CONFIRMED` - Confirmado
- `PREPARING` - Em preparação
- `SHIPPED` - Enviado
- `DELIVERED` - Entregue
- `CANCELLED` - Cancelado

## 🔧 Pré-requisitos

1. A aplicação Spring Boot deve estar rodando
2. O banco de dados PostgreSQL deve estar configurado e acessível
3. A porta padrão é `8080` (pode ser alterada nas variáveis da collection)

## 📖 Documentação Swagger

A documentação interativa da API está disponível em:
- **Swagger UI**: `http://localhost:8080/api/swagger-ui.html`
- **OpenAPI JSON**: `http://localhost:8080/api/v3/api-docs`

## 🐛 Troubleshooting

### Erro de Conexão
- Verifique se a aplicação está rodando
- Confirme se a URL base está correta nas variáveis
- Verifique se a porta está correta (padrão: 8080)

### Erro 404
- Verifique se o context-path está correto (`/api`)
- Confirme se o endpoint existe na documentação Swagger

### Erro de Validação
- Verifique se todos os campos obrigatórios estão preenchidos
- Confirme os tipos de dados (ex: price deve ser número, não string)

## 📞 Suporte

Para mais informações, consulte a documentação Swagger da API ou o código-fonte do projeto.


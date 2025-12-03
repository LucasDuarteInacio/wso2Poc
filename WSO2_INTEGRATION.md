# Documentação de Integração com WSO2 API Manager

## Visão Geral

Este documento descreve como integrar a API REST de Gerenciamento de Pedidos com o WSO2 API Manager para publicação, gerenciamento e proteção das APIs.

## Pré-requisitos

1. Docker e Docker Compose instalados
2. WSO2 API Manager rodando (via docker-compose)
3. Aplicação Spring Boot rodando na porta 8080

## Passo 1: Iniciar os Serviços

```bash
docker-compose up -d
```

Isso iniciará:
- PostgreSQL na porta 5432
- WSO2 API Manager na porta 9443 (Console) e 8243 (Gateway)

## Passo 2: Acessar o Console do WSO2

1. Abra o navegador e acesse: `https://localhost:9443/publisher`
2. Aceite o certificado SSL (auto-assinado)
3. Faça login com as credenciais padrão:
   - **Usuário**: admin
   - **Senha**: admin

## Passo 3: Criar uma Aplicação

1. No Publisher, vá para **Applications** → **Create Application**
2. Preencha:
   - **Name**: OrderProcessingApp
   - **Throttling Tier**: Unlimited (ou escolha um tier apropriado)
3. Clique em **Create**

## Passo 4: Gerar Credenciais (API Keys)

1. Na aplicação criada, clique em **Production Keys** ou **Sandbox Keys**
2. Clique em **Generate Keys**
3. Anote as credenciais geradas:
   - **Consumer Key**
   - **Consumer Secret**
   - **Access Token**

## Passo 5: Publicar a API no Gateway

### 5.1 Criar a API

1. No Publisher, clique em **Create API** → **Import OpenAPI Definition**
2. Ou clique em **Design a New REST API**
3. Configure:
   - **Name**: OrderProcessingAPI
   - **Context**: /order-processing
   - **Version**: 1.0.0
   - **Endpoint**: http://host.docker.internal:8080/api

### 5.2 Definir Endpoints

Configure os seguintes endpoints:

- `GET /api/clientes` - Listar clientes
- `GET /api/clientes/{id}` - Buscar cliente por ID
- `POST /api/clientes` - Criar cliente
- `GET /api/produtos` - Listar produtos
- `GET /api/produtos/{id}` - Buscar produto por ID
- `POST /api/produtos` - Criar produto
- `PUT /api/produtos/{id}` - Atualizar produto
- `DELETE /api/produtos/{id}` - Excluir produto
- `GET /api/pedidos` - Listar pedidos
- `GET /api/pedidos/{id}` - Buscar pedido por ID
- `POST /api/pedidos` - Criar pedido
- `PATCH /api/pedidos/{id}/status` - Atualizar status do pedido

### 5.3 Configurar Rate Limiting

1. Na seção **Manage** → **Policies** → **Throttling**
2. Crie uma política de throttling ou use uma existente:
   - **Policy Name**: BasicRateLimit
   - **Request Count**: 100
   - **Unit Time**: 1 minuto (60000 ms)
   - **Stop on Quota Reach**: true

3. Na seção **Resources**, aplique a política aos endpoints desejados

### 5.4 Publicar a API

1. Vá para **Lifecycle** → **Publish**
2. Selecione os ambientes (Production e/ou Sandbox)
3. Clique em **Publish**

## Passo 6: Assinar a Aplicação à API

1. No Developer Portal (https://localhost:9443/devportal)
2. Faça login com admin/admin
3. Vá para **APIs** → Selecione **OrderProcessingAPI**
4. Clique em **Subscribe**
5. Selecione a aplicação **OrderProcessingApp**
6. Escolha o throttling tier
7. Clique em **Subscribe**

## Passo 7: Testar a API com Rate Limit

### 7.1 Usando cURL

```bash
# Substitua YOUR_ACCESS_TOKEN pelo token gerado
export TOKEN="YOUR_ACCESS_TOKEN"
export WSO2_GATEWAY="https://localhost:8243"

# Fazer requisições através do WSO2 Gateway
curl -k -H "Authorization: Bearer $TOKEN" \
  $WSO2_GATEWAY/order-processing/1.0.0/api/clientes

# Testar rate limit (executar múltiplas vezes rapidamente)
for i in {1..150}; do
  curl -k -H "Authorization: Bearer $TOKEN" \
    $WSO2_GATEWAY/order-processing/1.0.0/api/clientes
  echo "Request $i"
done
```

### 7.2 Usando Postman

1. Importe a coleção da API
2. Configure a variável de ambiente:
   - `WSO2_GATEWAY`: https://localhost:8243
   - `ACCESS_TOKEN`: Seu access token
3. Configure a autenticação como **Bearer Token**
4. Faça requisições através do gateway

## Passo 8: Monitorar o Rate Limit

1. No Publisher, vá para **Analytics** (se configurado)
2. Monitore:
   - Taxa de requisições
   - Limites atingidos
   - Erros de throttling

## Estrutura de URLs

### Direto para a API (sem WSO2)
```
http://localhost:8080/api/{resource}
```

### Através do WSO2 Gateway
```
https://localhost:8243/{context}/{version}/api/{resource}
```

Exemplo:
```
https://localhost:8243/order-processing/1.0.0/api/clientes
```

## Exemplo de Requisição Completa

```bash
curl -k -X POST \
  https://localhost:8243/order-processing/1.0.0/api/clientes \
  -H "Authorization: Bearer YOUR_ACCESS_TOKEN" \
  -H "Content-Type: application/json" \
  -d '{
    "nome": "João Silva",
    "email": "joao@email.com",
    "cpf": "123.456.789-00"
  }'
```

## Configurações Avançadas

### Customizar Rate Limits por Endpoint

1. Na API, vá para **Resources**
2. Clique no endpoint específico
3. Configure throttling policies específicas

### Configurar CORS

1. Na API, vá para **API Configurations** → **CORS Configuration**
2. Habilite CORS e configure os headers permitidos

### Adicionar Medição de Analytics

1. Configure o Analytics Server do WSO2
2. Habilite analytics na configuração da API

## Troubleshooting

### Erro de Conexão

Se a aplicação não conseguir conectar ao endpoint:
- Verifique se a aplicação está rodando
- Use `host.docker.internal` ao invés de `localhost` no endpoint do WSO2

### Erro 401 Unauthorized

- Verifique se o token está correto
- Verifique se o token não expirou
- Regenerate o token se necessário

### Rate Limit atingido

- Aguarde o período de throttling
- Verifique as políticas de rate limit configuradas
- Considere aumentar o limite se necessário

## Referências

- [WSO2 API Manager Documentation](https://apim.docs.wso2.com/)
- [WSO2 API Manager Docker Guide](https://apim.docs.wso2.com/en/latest/deploy-and-publish/docker/deploying-wso2-api-manager-using-docker/)


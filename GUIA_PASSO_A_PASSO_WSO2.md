# Guia Passo a Passo: Publicar API no WSO2 4.5.0 e Configurar Rate Limiting

Este guia prático mostra como publicar a API Order Processing no WSO2 API Manager 4.5.0 e configurar rate limiting básico.

## Sobre o WSO2 4.5.0

Esta versão inclui melhorias significativas:
- Interface mais moderna e intuitiva
- Melhor performance
- Correções de bugs (incluindo problemas com Admin Console)
- Novas funcionalidades: IA generativa, Multi-gateway, B2B

## Pré-requisitos

1. WSO2 API Manager 4.5.0 rodando (via docker-compose)
2. Aplicação Spring Boot rodando na porta 8080
3. Acesso ao navegador web
4. Docker Compose configurado com `user: root` no serviço wso2am (para evitar problemas de permissão)

## Passo 1: Acessar o Publisher do WSO2

1. Abra o navegador e acesse: `https://localhost:9443/publisher`
2. Aceite o certificado SSL (clique em "Avançado" → "Continuar")
3. Faça login com:
   - **Usuário**: `admin`
   - **Senha**: `admin`

## Passo 2: Criar a API (Importar OpenAPI)

### Opção A: Importar via arquivo OpenAPI (Recomendado)

1. No Publisher, clique em **"Create API"** → **"Import OpenAPI Definition"**
2. Selecione **"I have an OpenAPI definition"**
3. Clique em **"Import via File"**
4. Faça upload do arquivo `openapi.yaml` do projeto
5. Ou cole o conteúdo do arquivo na opção **"Import via Text"**

### Opção B: Criar API manualmente

1. Clique em **"Create API"** → **"Design a New REST API"**
2. Preencha:
   - **Name**: `OrderProcessingAPI`
   - **Context**: `/order-processing`
   - **Version**: `1.0.0`
   - **Endpoint**: `http://host.docker.internal:8080/api`
3. Clique em **"Create"**

## Passo 3: Configurar Endpoints da API

1. Após criar a API, você será redirecionado para a página de overview
2. No menu lateral, vá para **"Endpoints"**
3. Configure:
   - **Production Endpoint**: `http://host.docker.internal:8080/api`
   - **Sandbox Endpoint**: `http://host.docker.internal:8080/api` (opcional)
4. Clique em **"Save"**

## Passo 4: Configurar Rate Limiting

### 4.1 Usar Tiers Pré-configurados (Método Simples - Recomendado)

No WSO2 4.5.0, o rate limiting é configurado através de **Throttling Tiers** (níveis de throttling) que já vêm pré-configurados. Você pode usar tiers existentes ou criar novos através do Admin Console.

**Tiers pré-configurados disponíveis:**
- `Unlimited` - Sem limite
- `Bronze` - 1000 requisições/minuto
- `Silver` - 2000 requisições/minuto  
- `Gold` - 5000 requisições/minuto
- `10KPerMin` - 10000 requisições/minuto
- `20KPerMin` - 20000 requisições/minuto

### 4.2 Usar Tiers Pré-configurados (Recomendado)

No WSO2 4.5.0, o Admin Console pode estar disponível. No entanto, a **melhor abordagem** é usar os tiers pré-configurados que já vêm com o sistema.

**Tiers Disponíveis para Teste:**

| Tier | Limite | Uso Recomendado |
|------|--------|-----------------|
| `Bronze` | 1000/min | Para testes gerais |
| `Silver` | 2000/min | Para testes com mais volume |
| `Gold` | 5000/min | Para testes de carga |
| `10KPerMin` | 10000/min | Para testes intensivos |

**Para demonstrar rate limiting:**

1. Use o tier **`Bronze`** (1000 requisições/minuto)
2. Faça mais de 1000 requisições em 1 minuto
3. Após o limite, você verá erro 429/503

### 4.3 Criar Tier Personalizado (NÃO NECESSÁRIO - Use Tiers Pré-configurados!)

**⚠️ IMPORTANTE**: No WSO2 4.5.0, o Admin Console pode estar funcional. No entanto, **NÃO é necessário criar tiers customizados!** Os tiers pré-configurados são suficientes.

**Use os tiers pré-configurados que já vêm com o WSO2:**
- `Bronze` = 1000 req/min ← **Use este para testes!**
- `Silver` = 2000 req/min
- `Gold` = 5000 req/min

Isso é **suficiente para demonstrar o rate limiting funcionando**.

**Se realmente precisar de um tier customizado (ex: 10 req/min):**

**Opção: Via API REST do WSO2 (Bypass do Console)**

Se o console não funcionar, você pode criar tiers via API REST, mas é mais complexo e não é necessário para a demonstração.

```bash
# Autenticar e obter cookie
curl -k -X POST https://localhost:9443/services/authentication/admin \
  -H "Content-Type: application/json" \
  -d '{"username":"admin","password":"admin"}'

# Criar tier (usar cookie obtido acima)
curl -k -X POST https://localhost:9443/api/am/admin/v3/throttling/subscription-policies \
  -H "Cookie: JSESSIONID=xxx" \
  -H "Content-Type: application/json" \
  -d '{
    "name": "BasicRateLimit10",
    "displayName": "Basic Rate Limit 10",
    "description": "10 requests per minute",
    "rateLimitCount": 10,
    "rateLimitTimeUnit": "min",
    "stopOnQuotaReach": true
  }'
```

**Opção B: Usar tiers existentes**

Para a maioria dos casos, os tiers pré-configurados são suficientes. Use `Bronze` para testes de rate limiting.

## Passo 5: Publicar a API

1. Na página da API, clique no botão **"Deploy"** (canto superior direito)
2. Ou vá em **"Lifecycle"** → **"Publish"**
3. Selecione os ambientes:
   - ✅ **Production**
   - ✅ **Sandbox** (opcional)
4. Clique em **"Deploy"** ou **"Publish"**
5. Aguarde a confirmação de publicação

## Passo 6: Criar Aplicação e Gerar Credenciais

### 6.1 Criar Aplicação

No WSO2 4.5.0, a opção de criar aplicações pode estar em diferentes lugares:

**Opção A: Via Menu "Apps" (Mais Comum)**

1. No Publisher, no **menu lateral esquerdo**, procure por **"Apps"** ou **"Applications"**
2. Se não estiver visível, tente:
   - Clique no ícone de menu (☰) no canto superior esquerdo
   - Procure por "Apps" no menu lateral
3. Clique em **"Apps"** → **"+ Add Application"** ou **"Create Application"**
4. Preencha:
   - **Name**: `OrderProcessingApp`
   - **Description**: (opcional)
5. Clique em **"Create"**

**Opção B: Via Developer Portal**

1. Acesse o **Developer Portal**: `https://localhost:9443/devportal`
2. Faça login com `admin` / `admin`
3. No menu, vá para **"Applications"** ou **"My Apps"**
4. Clique em **"+ Create New Application"** ou **"Add Application"**
5. Preencha:
   - **Name**: `OrderProcessingApp`
6. Clique em **"Create"**

**Opção C: Diretamente ao Subscrever à API**

Você também pode criar uma aplicação diretamente ao subscrever à API (ver Passo 7).

### 6.2 Gerar Credenciais (API Keys)

1. Na aplicação criada, vá para **"Production Keys"** ou **"Sandbox Keys"**
2. Clique em **"Generate Keys"**
3. **ANOTE** as credenciais geradas:
   - **Consumer Key**: `xxxxxxxxxxxxx`
   - **Consumer Secret**: `yyyyyyyyyyyyy`
   - **Access Token**: `zzzzzzzzzzzzz`
   - **Token Validity**: (tempo de expiração)

## Passo 7: Subscrever a Aplicação à API e Configurar Rate Limit

**IMPORTANTE**: No WSO2 4.5.0, o rate limiting é configurado **na subscription** entre a aplicação e a API.

**Método 1: Via Publisher (Recomendado)**

1. Na página da API (`OrderProcessingAPI`), vá para **"Subscriptions"** no menu lateral
2. Clique em **"+ Add Subscription"** ou **"Subscribe"**
3. Selecione ou crie:
   - **Application**: 
     - Se já criou: Selecione `OrderProcessingApp` da lista
     - Se não criou: Clique em **"+ Create New Application"** e crie `OrderProcessingApp`
   - **Throttling Tier**: Escolha um tier para rate limiting:
     - Para teste: Use `Bronze` (1000/min) ← **Recomendado**
     - Outras opções: `Silver` (2000/min), `Gold` (5000/min), `10KPerMin` (10000/min)
4. Clique em **"Subscribe"**

**Método 2: Via Developer Portal**

1. Acesse o **Developer Portal**: `https://localhost:9443/devportal`
2. Faça login com `admin` / `admin`
3. Vá para **"APIs"** → Selecione `OrderProcessingAPI`
4. Clique em **"Subscribe"**
5. Selecione a aplicação `OrderProcessingApp` e o tier `Bronze`
6. Clique em **"Subscribe"**

**O rate limiting será aplicado automaticamente** através do tier selecionado na subscription.

## Passo 8: Testar a API através do Gateway

### 8.1 Obter a URL do Gateway

A URL base do gateway será:
```
https://localhost:8243/order-processing/1.0.0/api
```

### 8.2 Testar sem Rate Limit

```bash
# Definir variáveis
export GATEWAY_URL="https://localhost:8243/order-processing/1.0.0/api"
export TOKEN="SEU_ACCESS_TOKEN_AQUI"

# Testar endpoint de clientes
curl -k -H "Authorization: Bearer $TOKEN" \
  $GATEWAY_URL/clientes
```

### 8.3 Testar Rate Limit (Demonstrar Funcionamento)

Execute múltiplas requisições rapidamente para testar o rate limit:

```bash
# Script para testar rate limit (tier Bronze = 1000/min)
# Para demonstrar, vamos fazer 1010 requisições
for i in {1..1010}; do
  http_code=$(curl -k -s -w "%{http_code}" -o /dev/null \
    -H "Authorization: Bearer $TOKEN" \
    $GATEWAY_URL/clientes)
  
  if [ "$http_code" = "200" ]; then
    echo "✓ Requisição $i: HTTP $http_code"
  elif [ "$http_code" = "429" ] || [ "$http_code" = "503" ]; then
    echo "✗ Requisição $i: HTTP $http_code (Rate Limit Atingido!)"
    break
  fi
  
  # Mostrar progresso a cada 100 requisições
  if [ $((i % 100)) -eq 0 ]; then
    echo "Progresso: $i requisições..."
  fi
  
  sleep 0.1
done
```

**Resultado Esperado (com tier Bronze - 1000/min):**
- Primeiras ~1000 requisições: HTTP 200 (sucesso)
- Requisições após o limite: HTTP 429 (Too Many Requests) ou HTTP 503 (Service Unavailable)

**Nota**: Para demonstrar rate limiting mais rapidamente, use um tier com limite menor ou faça muitas requisições rapidamente.

## Passo 9: Verificar Rate Limit Funcionando

### 9.1 Via Logs do WSO2

```bash
docker-compose logs -f wso2am | grep -i "throttle\|rate\|429"
```

### 9.2 Via Console do WSO2

1. No Publisher, na página da API, vá para **"Subscriptions"**
2. Verifique o **Throttling Tier** aplicado na subscription
3. Você também pode ver estatísticas em **"Analytics"** (se configurado)

### 9.3 Verificar Tier Configurado (Opcional - WSO2 4.5.0)

No WSO2 4.5.0, o Admin Console pode estar funcional:

1. Acesse **Admin Console**: `https://localhost:9443/console` ou `https://localhost:9443/carbon/admin/login.jsp`
2. Login: `admin` / `admin`
3. Vá em **"Main"** → **"Throttling"** → **"Subscription Tiers"**
4. Clique no tier que você está usando para ver a configuração exata

**Nota**: Se o console não estiver acessível, você pode verificar os tiers diretamente na interface do Publisher ao criar a subscription.

### 9.3 Resposta HTTP quando Rate Limit é Atingido

Quando o limite é excedido, a resposta será:

```json
{
  "fault": {
    "code": 900802,
    "message": "Message throttled out",
    "description": "You have exceeded your quota"
  }
}
```

Ou simplesmente HTTP 429/503.

## Exemplo Completo de Teste

```bash
#!/bin/bash

# Configurações
GATEWAY_URL="https://localhost:8243/order-processing/1.0.0/api"
TOKEN="SEU_ACCESS_TOKEN_AQUI"

echo "=== Teste de Rate Limiting ==="
echo ""
echo "Configuração: tier Bronze (1000 requisições por minuto)"
echo "Testando 1010 requisições para demonstrar rate limiting..."
echo ""

# Contadores
success=0
throttled=0

for i in {1..1010}; do
  http_code=$(curl -k -s -w "%{http_code}" -o /dev/null \
    -H "Authorization: Bearer $TOKEN" \
    $GATEWAY_URL/clientes 2>&1)
  
  if [ "$http_code" = "200" ]; then
    success=$((success + 1))
    # Mostrar apenas a cada 100 requisições para não poluir o output
    if [ $((i % 100)) -eq 0 ]; then
      echo "✓ Progresso: $i requisições (Sucessos: $success)"
    fi
  elif [ "$http_code" = "429" ] || [ "$http_code" = "503" ]; then
    throttled=$((throttled + 1))
    echo "✗ Requisição $i: HTTP $http_code (Rate Limit Atingido!)"
    # Continuar algumas requisições para confirmar o rate limit
    if [ $throttled -gt 5 ]; then
      break
    fi
  else
    echo "? Requisição $i: HTTP $http_code (Erro inesperado)"
  fi
  
  sleep 0.1
done

echo ""
echo "=== Resultado ==="
echo "Sucessos: $success"
echo "Rate Limited: $throttled"
echo ""
echo "Esperado: ~1000 sucessos + algumas rate limited (com tier Bronze)"
```

## Troubleshooting

### API não aparece no Gateway

1. Verifique se a API foi publicada (Status: "Published")
2. Verifique se o endpoint está correto
3. Verifique logs: `docker-compose logs wso2am`

### Rate Limit não está funcionando

1. Verifique se a **Subscription** está ativa na API
2. Verifique se o **Throttling Tier** foi selecionado na subscription (Passo 7)
3. Verifique se está usando o Access Token correto da aplicação
4. Aguarde alguns segundos para o throttle ser aplicado
5. Se estiver usando tier `Unlimited`, não haverá rate limit - mude para outro tier

### Erro 401 Unauthorized

1. Verifique se o Access Token está correto
2. Verifique se o token não expirou
3. Gere um novo token se necessário

### Não encontro a opção "Applications" ou "Apps"

No WSO2 4.5.0, a interface pode variar:

1. **No Publisher**: Procure por "Apps" no menu lateral esquerdo
2. **No Developer Portal**: Acesse `https://localhost:9443/devportal` → "Applications"
3. **Ao criar Subscription**: Você pode criar a aplicação diretamente ao subscrever à API
4. **Menu alternativo**: Alguns layouts podem ter "Applications" no menu superior ou dropdown

### Erro de conexão com a API

1. Verifique se a aplicação Spring Boot está rodando na porta 8080
2. Teste o endpoint diretamente: `curl http://localhost:8080/api/clientes`
3. Verifique se o endpoint no WSO2 usa `http://host.docker.internal:8080/api` (importante para Docker)
4. Verifique logs: `docker-compose logs wso2am`

### Problemas de Permissão (WSO2 4.5.0)

Se encontrar erros de permissão ao iniciar:

1. Verifique se o docker-compose.yml tem `user: root` configurado
2. Limpe volumes: `docker-compose down -v`
3. Reinicie: `docker-compose up -d`

## Resumo dos Endpoints

- **Publisher**: https://localhost:9443/publisher
- **Developer Portal**: https://localhost:9443/devportal
- **Gateway (Produção)**: https://localhost:8243/order-processing/1.0.0/api
- **Gateway (Sandbox)**: https://localhost:8243/order-processing/1.0.0/api
- **API Direta (sem gateway)**: http://localhost:8080/api

## Checklist Final

- [ ] WSO2 4.5.0 rodando e acessível
- [ ] API criada no WSO2 Publisher
- [ ] Endpoints configurados corretamente (`http://host.docker.internal:8080/api`)
- [ ] Throttling Tier selecionado (ex: `Bronze` = 1000/min)
- [ ] API publicada (Status: "Published")
- [ ] Aplicação criada (`OrderProcessingApp`)
- [ ] Credenciais (Access Token) geradas e copiadas
- [ ] Aplicação subscrevida à API com Throttling Tier configurado
- [ ] Teste de requisições bem-sucedidas através do gateway (até o limite)
- [ ] Teste de rate limit funcionando (após exceder o limite do tier)


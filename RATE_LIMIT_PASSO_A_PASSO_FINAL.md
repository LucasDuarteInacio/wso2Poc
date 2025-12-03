# Rate Limiting no WSO2 - Passo a Passo Final (SEM Admin Console)

## ⚠️ Problema Conhecido
O Admin Console do WSO2 4.0.0 retorna erro 405 após login. **Não se preocupe!** Você não precisa do console.

## ✅ Solução: Use Tiers Pré-configurados

Todos os tiers necessários já vêm instalados no WSO2. Você só precisa selecioná-los ao criar a subscription!

---

## Passo a Passo Completo

### 1. Criar e Publicar a API

1. Acesse: `https://localhost:9443/publisher`
2. Login: `admin` / `admin`
3. Clique em **"Create API"** → **"Import OpenAPI Definition"**
4. Faça upload do arquivo `openapi.yaml` ou cole o conteúdo
5. Configure o **Endpoint**: `http://host.docker.internal:8080/api`
6. Clique em **"Save"**
7. Publique a API: clique em **"Deploy"** ou **"Lifecycle"** → **"Publish"**

### 2. Criar Aplicação

1. No Publisher, vá em **"Applications"** (menu superior)
2. Clique em **"+ Add Application"**
3. Preencha:
   - **Name**: `OrderProcessingApp`
   - **Throttling Tier**: `Unlimited` (para a aplicação em si)
4. Clique em **"Create"**

### 3. Gerar Access Token

1. Na aplicação criada, vá em **"Production Keys"**
2. Clique em **"Generate Keys"**
3. **COPIE E SALVE** o **Access Token** gerado
   - Você vai precisar dele para testar!

### 4. 🔑 CONFIGURAR RATE LIMITING (Passo Principal!)

**Este é o passo onde você configura o rate limiting:**

1. Volte para a página da API (clique em **"APIs"** no menu superior)
2. Selecione sua API (`OrderProcessingAPI`)
3. No menu lateral esquerdo, clique em **"Subscriptions"**
4. Clique no botão **"+ Add Subscription"** ou **"Subscribe"**
5. No formulário, selecione:
   - **Application**: `OrderProcessingApp`
   - **Throttling Tier**: **Selecione `Bronze`** 
     - `Bronze` = **1000 requisições por minuto**
     - Isso é suficiente para demonstrar rate limiting!
6. Clique em **"Subscribe"**

**✅ Pronto! O rate limiting está configurado!**

### 5. Testar Rate Limiting

#### 5.1 Teste Básico

```bash
# Substitua YOUR_TOKEN pelo token gerado
export TOKEN="YOUR_TOKEN"
export URL="https://localhost:8243/order-processing/1.0.0/api/clientes"

# Fazer algumas requisições
curl -k -H "Authorization: Bearer $TOKEN" $URL
```

#### 5.2 Teste para Demonstrar Rate Limiting

Como o tier `Bronze` permite 1000 requisições/minuto, para demonstrar o rate limiting você precisa fazer mais de 1000 requisições rapidamente:

```bash
export TOKEN="YOUR_TOKEN"
export URL="https://localhost:8243/order-processing/1.0.0/api/clientes"

# Script para testar (mostra progresso a cada 100 requisições)
success=0
throttled=0

for i in {1..1010}; do
  http_code=$(curl -k -s -w "%{http_code}" -o /dev/null \
    -H "Authorization: Bearer $TOKEN" \
    $URL)
  
  if [ "$http_code" = "200" ]; then
    success=$((success + 1))
  elif [ "$http_code" = "429" ] || [ "$http_code" = "503" ]; then
    throttled=$((throttled + 1))
    echo "✓ Requisição $i: HTTP $http_code (Rate Limit atingido!)"
    break
  fi
  
  # Mostrar progresso
  if [ $((i % 100)) -eq 0 ]; then
    echo "Progresso: $i requisições (Sucessos: $success)"
  fi
done

echo ""
echo "=== Resultado ==="
echo "Sucessos: $success"
echo "Rate Limited: $throttled"
```

**Resultado Esperado:**
- Primeiras ~1000 requisições: HTTP 200 (sucesso)
- Após o limite: HTTP 429 ou 503 (rate limit excedido)

### 6. Usar Script Automatizado (Mais Fácil)

```bash
# Use o script fornecido no projeto
./test-rate-limit.sh YOUR_ACCESS_TOKEN
```

---

## Tiers Disponíveis (Pré-configurados)

| Tier | Limite | Quando Usar |
|------|--------|-------------|
| `Unlimited` | Sem limite | Desenvolvimento |
| **`Bronze`** | **1000/min** | **✅ Use para testes/demonstração** |
| `Silver` | 2000/min | Produção leve |
| `Gold` | 5000/min | Produção média |
| `10KPerMin` | 10000/min | Produção alta |

---

## Resumo Visual

```
1. Criar API → Publicar
   ↓
2. Criar Aplicação
   ↓
3. Gerar Access Token
   ↓
4. 🔑 SUBSCRIPTION (aqui está o rate limiting!)
   - Application: OrderProcessingApp
   - Throttling Tier: Bronze (1000/min)
   ↓
5. Testar!
```

---

## Checklist Final

- [ ] API criada e publicada
- [ ] Aplicação criada
- [ ] Access Token gerado e copiado
- [ ] **Subscription criada com Throttling Tier `Bronze`** ← RATE LIMIT AQUI!
- [ ] Testado: primeiras requisições funcionam (HTTP 200)
- [ ] Testado: após 1000+ requisições, recebe HTTP 429/503

---

## Dicas

1. **Não precisa do Admin Console** - tudo é feito pelo Publisher
2. **Use tiers pré-configurados** - Bronze é perfeito para demonstração
3. **Rate limiting = Throttling Tier na Subscription**
4. **Para testar:** faça mais requisições que o limite do tier

---

## Troubleshooting

**Erro 401 ao testar?**
- Verifique se o Access Token está correto
- Regenerate o token se necessário

**Rate limit não funciona?**
- Verifique se a subscription está ativa
- Verifique se o tier `Bronze` foi selecionado na subscription
- Aguarde alguns segundos após criar a subscription

**Erro 405 no console?**
- Ignore! Não é necessário para configurar rate limiting
- Use apenas o Publisher


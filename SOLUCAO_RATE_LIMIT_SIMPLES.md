# Solução Simples: Rate Limiting sem Admin Console

## Problema
O Admin Console (`/console`) retorna erro 405 **após fazer login** no WSO2 4.0.0. Este é um problema conhecido nesta versão.

## ✅ Solução: Você NÃO precisa do Console!

**Use os tiers pré-configurados** - é muito mais simples e funciona perfeitamente!

## Solução: Use Tiers Pré-configurados

Você **NÃO precisa** criar tiers customizados! Use os tiers que já vêm com o WSO2.

## Passo a Passo Simplificado

### 1. Criar API e Publicar
1. Acesse: `https://localhost:9443/publisher`
2. Crie ou importe a API
3. Publique a API

### 2. Criar Aplicação
1. Vá em **"Applications"** → **"+ Add Application"**
2. Nome: `OrderProcessingApp`
3. Clique em **"Create"**

### 3. Gerar Access Token
1. Na aplicação → **"Production Keys"**
2. Clique em **"Generate Keys"**
3. **Copie o Access Token**

### 4. Configurar Rate Limit (Onde está o rate limiting!)

1. Na API criada, vá em **"Subscriptions"**
2. Clique em **"+ Add Subscription"**
3. Selecione:
   - **Application**: `OrderProcessingApp`
   - **Throttling Tier**: **Selecione `Bronze`** (1000 requisições/minuto)
4. Clique em **"Subscribe"**

**Pronto! O rate limiting está configurado!**

## Testar Rate Limiting com Tier Bronze (1000/min)

Para demonstrar o funcionamento, você precisa fazer mais de 1000 requisições em 1 minuto:

```bash
# Use o script fornecido (ajustado para 1000 req/min)
export TOKEN="seu_access_token_aqui"
export URL="https://localhost:8243/order-processing/1.0.0/api/clientes"

# Fazer 1010 requisições
for i in {1..1010}; do
  http_code=$(curl -k -s -w "%{http_code}" -o /dev/null \
    -H "Authorization: Bearer $TOKEN" \
    $URL)
  
  if [ "$http_code" = "200" ]; then
    echo "✓ $i: HTTP 200"
  elif [ "$http_code" = "429" ] || [ "$http_code" = "503" ]; then
    echo "✗ $i: HTTP $http_code (Rate Limit!)"
    break
  fi
  
  # A cada 100 requisições, mostrar progresso
  if [ $((i % 100)) -eq 0 ]; then
    echo "Progresso: $i requisições..."
  fi
done
```

## Tiers Disponíveis

| Tier | Limite | Para Usar Quando |
|------|--------|------------------|
| `Unlimited` | Sem limite | Desenvolvimento/local |
| `Bronze` | **1000/min** | **Recomendado para testes** |
| `Silver` | 2000/min | Produção leve |
| `Gold` | 5000/min | Produção média |
| `10KPerMin` | 10000/min | Produção alta |

## Resumo

✅ **Não precisa criar tier customizado**
✅ **Use `Bronze` na subscription** (1000 req/min)
✅ **Teste fazendo mais de 1000 requisições**
✅ **Rate limiting funcionará automaticamente**

O rate limiting está **configurado na subscription**, não em um menu separado!


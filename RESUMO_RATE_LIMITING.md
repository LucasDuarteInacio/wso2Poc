# Resumo Rápido: Configurar Rate Limiting no WSO2 4.0.0

## Como Funciona o Rate Limiting no WSO2 4.0.0

No WSO2 API Manager 4.0.0, o rate limiting **NÃO** é configurado em um menu "Policies". 
Ele é configurado através de **Throttling Tiers** na **Subscription** entre a aplicação e a API.

## Passos Essenciais

### 1. Criar API no Publisher
- Acesse: `https://localhost:9443/publisher`
- Crie ou importe a API
- Publique a API

### 2. Criar Aplicação
- Vá em **"Applications"** → **"+ Add Application"**
- Nome: `OrderProcessingApp`
- Gere as **Production Keys** (copie o Access Token)

### 3. Configurar Rate Limit (Onde está o rate limiting!)

**Este é o passo importante:**

1. Na página da API, vá para **"Subscriptions"**
2. Clique em **"+ Add Subscription"**
3. Selecione:
   - **Application**: `OrderProcessingApp`
   - **Throttling Tier**: **AQUI você escolhe o rate limit!**
     - Use um tier pré-configurado: `Bronze` (1000/min), `Silver` (2000/min), etc.
     - Ou crie um tier customizado (ver abaixo)

### 4. Usar Tier Pré-configurado (Mais Simples!)

**NÃO é necessário criar tier customizado!** Use os tiers que já existem:

- Use **`Bronze`** na subscription = 1000 requisições/minuto
- Para testar: faça mais de 1000 requisições rapidamente
- Após o limite, receberá HTTP 429/503

**Tiers disponíveis:**
- `Bronze` → 1000/min ← **Use este para testes!**
- `Silver` → 2000/min
- `Gold` → 5000/min

## Testar Rate Limit

```bash
# Use o script fornecido
./test-rate-limit.sh YOUR_ACCESS_TOKEN

# Ou teste manualmente
export TOKEN="seu_token"
for i in {1..15}; do
  curl -k -w "\nHTTP: %{http_code}\n" \
    -H "Authorization: Bearer $TOKEN" \
    https://localhost:8243/order-processing/1.0.0/api/clientes
done
```

## Onde Está o Rate Limiting?

❌ **NÃO está em**: Menu "Policies" (não existe na interface do Publisher)

✅ **ESTÁ em**: 
1. **Subscription Tier** - quando você subscreve aplicação à API ← **USE ESTE!**
2. ~~Admin Console~~ → **IGNORE** (erro 405 após login - não é necessário!)

## Tiers Pré-configurados

- `Unlimited` - Sem limite
- `Bronze` - 1000/min
- `Silver` - 2000/min
- `Gold` - 5000/min
- `10KPerMin` - 10000/min
- `20KPerMin` - 20000/min

## Checklist

- [ ] API criada e publicada
- [ ] Aplicação criada
- [ ] Access Token gerado
- [ ] **Subscription criada com Throttling Tier selecionado** ← RATE LIMIT AQUI!
- [ ] Testado e funcionando


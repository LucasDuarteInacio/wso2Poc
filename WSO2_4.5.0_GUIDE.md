# Guia WSO2 API Manager 4.5.0

## Mudanças da 4.0.0 para 4.5.0

A versão 4.5.0 do WSO2 API Manager inclui melhorias significativas:

### Novas Funcionalidades
- **Governança de APIs** aprimorada
- **Federação de Gateways** e gerenciamento multi-gateway
- **Suporte a APIs de IA** com roteamento multi-modelo
- **Assistente de Design com IA** generativa
- **Gerenciamento B2B** com suporte a organizações

### Melhorias na Interface
- Interface mais moderna e intuitiva
- Melhor performance
- Correções de bugs (incluindo problemas com Admin Console)

## Como Usar WSO2 4.5.0

### 1. Atualizar Docker Compose

O arquivo `docker-compose.yml` já foi atualizado para usar a versão 4.5.0:

```yaml
wso2am:
  image: wso2/wso2am:4.5.0
  volumes:
    - wso2_data:/home/wso2carbon/wso2am-4.5.0/repository
```

### 2. Reiniciar os Containers

```bash
# Parar containers atuais
docker-compose down

# Remover volumes antigos (opcional - apaga dados)
# docker-compose down -v

# Subir com nova versão
docker-compose up -d

# Verificar logs
docker-compose logs -f wso2am
```

### 3. Acessar o Publisher

Após aguardar a inicialização (2-3 minutos):

```
https://localhost:9443/publisher
```

- **Usuário**: `admin`
- **Senha**: `admin`

## Configurar Rate Limiting no 4.5.0

### Interface Melhorada

A versão 4.5.0 tem uma interface mais intuitiva para configurar rate limiting:

### Passo a Passo:

1. **Criar/Importar API**
   - Publisher → "Create API" → "Import OpenAPI Definition"
   - Ou criar manualmente

2. **Publicar API**
   - Clique em "Deploy" ou "Lifecycle" → "Publish"

3. **Criar Aplicação**
   - "Applications" → "+ Add Application"
   - Nome: `OrderProcessingApp`

4. **Gerar Credenciais**
   - Na aplicação → "Production Keys" → "Generate Keys"
   - Copie o **Access Token**

5. **Configurar Rate Limit (Subscription)**
   - API → "Subscriptions" → "+ Add Subscription"
   - Selecione:
     - **Application**: `OrderProcessingApp`
     - **Throttling Tier**: `Bronze` (1000/min) ou outro tier
   - Clique em "Subscribe"

### Tiers Disponíveis (4.5.0)

| Tier | Limite | Descrição |
|------|--------|-----------|
| `Unlimited` | Sem limite | Desenvolvimento |
| `Bronze` | 1000/min | **Recomendado para testes** |
| `Silver` | 2000/min | Produção leve |
| `Gold` | 5000/min | Produção média |
| `10KPerMin` | 10000/min | Alta produção |
| `20KPerMin` | 20000/min | Muito alta produção |

## Admin Console no 4.5.0

A versão 4.5.0 **pode ter corrigido** o problema do Admin Console (erro 405). Tente acessar:

```
https://localhost:9443/console
```

Ou o Carbon Admin:

```
https://localhost:9443/carbon/admin/login.jsp
```

Se ainda apresentar problemas, continue usando os tiers pré-configurados (não é necessário criar tiers customizados).

## Testar Rate Limiting

### Teste Básico

```bash
export TOKEN="seu_access_token"
export URL="https://localhost:8243/order-processing/1.0.0/api/clientes"

curl -k -H "Authorization: Bearer $TOKEN" $URL
```

### Teste de Rate Limit (Bronze = 1000/min)

```bash
# Use o script fornecido
./test-rate-limit.sh YOUR_ACCESS_TOKEN

# Ou teste manualmente
export TOKEN="seu_token"
export URL="https://localhost:8243/order-processing/1.0.0/api/clientes"

success=0
for i in {1..1010}; do
  http_code=$(curl -k -s -w "%{http_code}" -o /dev/null \
    -H "Authorization: Bearer $TOKEN" \
    $URL)
  
  if [ "$http_code" = "200" ]; then
    success=$((success + 1))
  elif [ "$http_code" = "429" ] || [ "$http_code" = "503" ]; then
    echo "✓ Requisição $i: Rate Limit atingido (HTTP $http_code)"
    break
  fi
  
  if [ $((i % 100)) -eq 0 ]; then
    echo "Progresso: $i requisições (Sucessos: $success)"
  fi
done

echo "Total de sucessos: $success"
```

## Diferenças Principais: 4.0.0 vs 4.5.0

| Aspecto | 4.0.0 | 4.5.0 |
|---------|-------|-------|
| **Admin Console** | Erro 405 conhecido | Pode estar corrigido |
| **Interface** | Padrão | Mais moderna |
| **Performance** | Boa | Melhorada |
| **Funcionalidades** | Básicas | + IA, B2B, Multi-gateway |
| **Caminho Volume** | `/wso2am-4.0.0/repository` | `/wso2am-4.5.0/repository` |

## Migração de Dados (Opcional)

Se você tinha dados/configurações na 4.0.0 e quer migrar:

```bash
# 1. Backup do volume antigo
docker run --rm -v orderprocessingmanagement_wso2_data:/data -v $(pwd):/backup \
  alpine tar czf /backup/wso2_backup.tar.gz -C /data .

# 2. Depois de subir 4.5.0, restaurar (se necessário)
# Cuidado: pode não ser totalmente compatível
```

**Recomendação**: Para um novo projeto, comece limpo com 4.5.0.

## Checklist

- [ ] Docker-compose atualizado para 4.5.0
- [ ] Containers reiniciados
- [ ] WSO2 4.5.0 iniciado e acessível
- [ ] API criada e publicada
- [ ] Aplicação criada
- [ ] Access Token gerado
- [ ] Subscription criada com Throttling Tier
- [ ] Rate limiting testado e funcionando

## Troubleshooting

### Erro ao iniciar

```bash
# Verificar logs
docker-compose logs wso2am

# Verificar se porta está em uso
lsof -i :9443
```

### Erro de conexão

- Aguarde mais tempo (4.5.0 pode levar 3-5 minutos para iniciar)
- Verifique se PostgreSQL está rodando: `docker-compose ps`

### Problemas com volume

```bash
# Remover volume e começar limpo
docker-compose down -v
docker-compose up -d
```

## URLs Importantes

- **Publisher**: https://localhost:9443/publisher
- **Developer Portal**: https://localhost:9443/devportal
- **Gateway**: https://localhost:8243
- **Console**: https://localhost:9443/console (pode funcionar agora!)
- **Carbon Admin**: https://localhost:9443/carbon/admin/login.jsp

## Documentação Oficial

- [WSO2 API Manager 4.5.0 Docs](https://apim.docs.wso2.com/en/4.5.0/)
- [Release Notes 4.5.0](https://github.com/wso2/product-apim/releases/tag/v4.5.0)


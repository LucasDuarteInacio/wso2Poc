#!/bin/bash

# Script para testar Rate Limiting no WSO2 API Gateway
# Uso: ./test-rate-limit.sh YOUR_ACCESS_TOKEN

# Cores para output
GREEN='\033[0;32m'
RED='\033[0;31m'
YELLOW='\033[1;33m'
NC='\033[0m' # No Color

# Configurações
GATEWAY_URL="https://localhost:8243/order-processing/1.0.0/api"
ENDPOINT="$GATEWAY_URL/clientes"

# Verificar se o token foi fornecido
if [ -z "$1" ]; then
    echo -e "${RED}Erro: Access Token não fornecido${NC}"
    echo "Uso: ./test-rate-limit.sh YOUR_ACCESS_TOKEN"
    echo ""
    echo "Para obter o token:"
    echo "1. Acesse https://localhost:9443/publisher"
    echo "2. Vá em Applications → Selecione sua aplicação"
    echo "3. Em Production Keys, gere ou copie o Access Token"
    exit 1
fi

TOKEN=$1

echo -e "${YELLOW}=== Teste de Rate Limiting ===${NC}"
echo ""
echo "Gateway: $GATEWAY_URL"
echo "Endpoint: $ENDPOINT"
echo "Configuração esperada: 10 requisições por minuto"
echo ""
echo -e "${YELLOW}Executando 15 requisições rapidamente...${NC}"
echo ""

# Contadores
success=0
throttled=0
errors=0

# Função para testar uma requisição
test_request() {
    local num=$1
    local response=$(curl -k -s -w "\n%{http_code}" \
        -H "Authorization: Bearer $TOKEN" \
        -H "Content-Type: application/json" \
        "$ENDPOINT" 2>&1)
    
    local http_code=$(echo "$response" | tail -1)
    local body=$(echo "$response" | sed '$d')
    
    case "$http_code" in
        200)
            success=$((success + 1))
            echo -e "${GREEN}✓ Requisição $num: HTTP $http_code (Sucesso)${NC}"
            return 0
            ;;
        429|503)
            throttled=$((throttled + 1))
            echo -e "${RED}✗ Requisição $num: HTTP $http_code (Rate Limit Atingido)${NC}"
            if echo "$body" | grep -q "throttled\|quota"; then
                echo "   Mensagem: Rate limit excedido"
            fi
            return 1
            ;;
        401)
            errors=$((errors + 1))
            echo -e "${RED}✗ Requisição $num: HTTP $http_code (Não Autorizado)${NC}"
            echo "   Verifique se o Access Token está correto"
            return 2
            ;;
        *)
            errors=$((errors + 1))
            echo -e "${YELLOW}? Requisição $num: HTTP $http_code (Resposta inesperada)${NC}"
            return 3
            ;;
    esac
}

# Executar requisições
for i in {1..15}; do
    test_request $i
    sleep 0.3  # Pequeno delay entre requisições
done

echo ""
echo -e "${YELLOW}=== Resultado ===${NC}"
echo -e "${GREEN}Sucessos: $success${NC}"
echo -e "${RED}Rate Limited: $throttled${NC}"
if [ $errors -gt 0 ]; then
    echo -e "${RED}Erros: $errors${NC}"
fi
echo ""

# Validação do resultado
if [ $success -eq 10 ] && [ $throttled -eq 5 ]; then
    echo -e "${GREEN}✓ Teste PASSOU! Rate limiting funcionando corretamente.${NC}"
    echo "   (10 requisições permitidas + 5 bloqueadas = comportamento esperado)"
elif [ $success -le 10 ] && [ $throttled -gt 0 ]; then
    echo -e "${GREEN}✓ Rate limiting está funcionando!${NC}"
    echo "   Algumas requisições foram bloqueadas após o limite."
else
    echo -e "${YELLOW}⚠ Verifique a configuração do rate limiting.${NC}"
    echo "   Esperado: ~10 sucessos + algumas requisições bloqueadas"
fi

echo ""
echo "Para testar novamente após 1 minuto (reset do rate limit):"
echo "  ./test-rate-limit.sh $TOKEN"


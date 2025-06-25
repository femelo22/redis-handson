set -e

echo "⚠️ Deletando todos os recursos..."

kubectl delete -f k8s/

echo "🗑️ Tudo removido!"
#!/bin/bash
set -e

echo "🗂  Aplicando namespace..."
kubectl apply -f k8s/namespace.yaml

echo "🔐 Aplicando configmap..."
kubectl apply -f k8s/envs/configmap.yaml

echo "🔐 Aplicando secret..."
kubectl apply -f k8s/envs/secret.yaml

echo "🔐 Aplicando Banco de Dados [Postgres]..."
kubectl apply -f k8s/db/postgres-deployment.yaml
kubectl apply -f k8s/db/postgres-service.yaml
kubectl apply -f k8s/db/postgres-pvc.yaml

echo "🔐 Aplicando Cache [Redis]..."
kubectl apply -f k8s/redis/redis-deployment.yaml
kubectl apply -f k8s/redis/redis-service.yaml

echo "🚀 Aplicando App [api-redis]..."
kubectl apply -f k8s/app-deployment.yaml
kubectl apply -f k8s/app-service.yaml

echo "✅ Deploy finalizado com sucesso!"
#!/bin/bash

# Espera o PostgreSQL iniciar
echo "Aguardando PostgreSQL iniciar..."
while ! nc -z postgres 5432; do
  sleep 0.1
done
echo "PostgreSQL iniciado"

# Inicia a aplicação
./gradlew bootRun

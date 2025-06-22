# Etapa de build (sem usar Alpine para evitar problemas com compartibilidade)
FROM eclipse-temurin:21-jdk AS build
WORKDIR /app
COPY . .
RUN ./mvnw clean package -DskipTests

# Etapa de runtime, garantir que sempre será construído um .jar atualizado
FROM eclipse-temurin:21-jdk
WORKDIR /app
COPY --from=build /app/target/*.jar api-redis.jar

# Expõe a porta padrão do Spring Boot
EXPOSE 8080

# Comando para rodar a aplicação
ENTRYPOINT ["java", "-jar", "api-redis.jar"]
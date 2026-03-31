# ================================
# Stage 1: Build
# ================================
FROM maven:3.9-eclipse-temurin-21 AS builder

WORKDIR /app

# Copia o pom.xml e baixa as dependências primeiro (cache eficiente)
COPY pom.xml .
RUN mvn dependency:go-offline -B

# Copia o restante do código e empacota
COPY src ./src
RUN mvn package -DskipTests -B

# ================================
# Stage 2: Runtime
# ================================
FROM eclipse-temurin:21-jre-jammy

WORKDIR /app

# Cria usuário não-root por segurança
RUN addgroup --system appgroup && adduser --system --ingroup appgroup appuser

# Copia o JAR gerado no estágio anterior
COPY --from=builder /app/target/*.jar app.jar

RUN chown appuser:appgroup app.jar

USER appuser

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]
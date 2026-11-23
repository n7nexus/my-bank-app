# Этап 1: Сборка и распаковка по слоям
FROM gradle:9-jdk21-alpine AS builder
WORKDIR /app
COPY . .
RUN gradle bootJar --no-daemon

# Этап 2: Финальный образ (только JRE)
FROM amazoncorretto:21-alpine
WORKDIR /application
RUN addgroup -S spring && adduser -S spring -G spring
USER spring:spring

COPY --from=builder /app/build/libs/*-SNAPSHOT.jar ./application.jar

ARG APP_PORT=8100
ENV PORT=${APP_PORT}
EXPOSE ${PORT}
ENTRYPOINT ["java", "-jar", "application.jar"]
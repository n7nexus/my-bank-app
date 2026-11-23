---

# Взаимодействие компонентов

### UI (webui)
- Аутентифицирует пользователя через Keycloak.
- Получает user-token (JWT).
- Делает запросы к Gateway с пользовательским токеном.
- Показывает страницу перевода и результат операции.

### Gateway API (gateway)
- Проверяет JWT как Resource Server.
- Выполняет кастомный Token Relay.
- Проксирует запросы в account-service.
- Проксирует запросы в cash-service.
- Проксирует запросы в transfer-service.

### transfer-service
- Принимает запросы с user-token.
- Проверяет роли пользователя (`USER`, `transfer.write`).
- Для вызова accounts-service получает service-token (Client Credentials Flow).
- Проверяет владельца счёта и выполняет перевод.

### cash-service
- Принимает запросы с user-token.
- Проверяет роли пользователя (`USER`, `cash.ops`).
- Для вызова accounts-service получает service-token (Client Credentials Flow).
- Проверяет владельца счёта и производит операции со счетом.

### account-service
- Принимает запросы с service-token.
- Проверяет роль (`USER`) для работы с данными пользователя
- Проверяет роли (`SERVICE`, `accounts.write`) для операций с деньгами.
- Выполняет перевод.

### notification-service
- Принимает запросы с service-token.
- Выводит в лог уведомления от остальных сервисов
- Проверяет роли (`SERVICE`,`notification.send`) 

### Keycloak
- Управляет пользователями, ролями и клиентами.
- Выдаёт JWT для UI и сервисов.

### Consul
- Осуществляет Service Discovery
- External Configuration



---

# Запуск проекта

## 1. Keycloak

В проекте есть экспорт realm’а:

[bank-realm.json](keycloak/realms/bank-realm.json)

Он включает:

- realm `bank-realm`;
- пользователей;
- роли (`USER`, `TRANSFER_WRITE`, `ACCOUNTS_WRITE`);
- клиентов (`bank-ui`, `transfer-service`);
- протокольные мапперы.

**Создавать realm, роли, пользователей или клиентов вручную НЕ нужно.**
При запуске контейнера Keycloak автоматически применяет этот экспорт,
и вы сразу получаете полностью готовый к работе Keycloak без ручной настройки.

### Запуск Keycloak

```bash
docker compose up
```

Keycloak поднимется на:

```
http://localhost:8080
```

---

## 2. Запуск сервисов

### bank-gateway

```bash
./gradlew :bank-gateway:bootRun
```

### accounts-service

```bash
./gradlew :accounts-service:bootRun
```

### transfer-service

```bash
./gradlew :transfer-service:bootRun
```

### bank-ui

```bash
./gradlew :bank-ui:bootRun
```

UI будет доступен по адресу:

```
http://localhost:8084
```

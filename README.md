# Микросервисное приложение «Банк»

Учебный микросервисный проект, реализующий функциональность виртуального банка с веб-интерфейсом. Пользователь (клиент банка) может редактировать данные своего аккаунта, пополнять счёт и снимать с него виртуальные деньги, а также переводить средства на счёт другого клиента.

Проект демонстрирует применение паттернов микросервисной архитектуры и интеграций Spring Cloud: Gateway API, Service Discovery, Externalized/Distributed Config, OAuth 2.0 (Authorization Code Flow и Client Credentials Flow), Database per Service, Circuit Breaker, Transactional Outbox, Contract Testing и др.

## Состав приложения

| Компонент | Назначение |
|---|---|
| **Front UI** | Веб-интерфейс (одна HTML-страница после аутентификации). Выполняет запросы в микросервисы **только через Gateway API**. Авторизация — OAuth 2.0 Authorization Code Flow. |
| **Gateway API** | Самописный сервис на Spring Cloud Gateway. Пробрасывает пользовательский JWT в микросервисы. |
| **Accounts** | Хранит аккаунты и счета: логин, фамилия и имя, дата рождения, сумма на счету. Ходит в **Notifications** по Client Credentials Flow. |
| **Cash** | Пополнение и снятие денег. Ходит в **Accounts** и **Notifications** по Client Credentials Flow. |
| **Transfer** | Перевод денег между счетами разных пользователей. Ходит в **Accounts** и **Notifications** по Client Credentials Flow. |
| **Notifications** | Отправляет уведомления о выполненных действиях (лог). |
| **Service Discovery** | Consul |
| **Config Server** | Consul |
| **OAuth 2.0 Server** | Keycloak |

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

[bank-realm.json](.dev/keycloak/realms/bank-realm-realm.json)

Он включает:

- realm `bank-realm`;
- пользователей (`tu1`, `tu2`, `tu3`); 
- роли (`Service`, `USER`, `TRANSFER_WRITE`, `CASH_OPS`, `ACCOUNTS_WRITE`, `NOTIFICATION_SEND`);
- клиентов (`bank-ui`, `transfer-service`, `cash-service`, `account-service`, `notification-service`);
- протокольные мапперы.

**Создавать realm, роли, пользователей или клиентов вручную НЕ нужно.**
При запуске контейнера Keycloak автоматически применяет этот экспорт,
и вы сразу получаете полностью готовый к работе Keycloak без ручной настройки.

### Запуск Keycloak, Consul, Postgres

```bash
cd .dev
docker compose up
```

Keycloak поднимется на:

```
http://keycloak:8080
```

Перед паботой с Keycloak необходимо прописать 
```
127.0.0.1    keycloak
```
в hosts и сбросить DNS кэш командой (Windows)
```
ipconfig /flushdns
```
Эти действия необходимы, чтобы докер образы с сервисами могли корректно взаимодействовать с Keycloak.

Consul поднимется на:

```
http://localhost:8500/
```

PosgreSQL поднимется на:

```
http://localhost:5432/
```

---

## 2. Запуск сервисов

Сервисы можно сразу развернуть в контейнерном окружении

```bash
docker compose up
```

UI будет доступен по адресу:

```
http://localhost:8084
```

# Дополнительная информация

### Настройки для Externalized Configuration нужно внести в Consul K\V Storage

- Ключ: `config/{имя сервиса}/data`
- Данные добавляются в формате YAML, примеры можно найти в `.dev/consul`

### Данные пользователей можно заполнить самостоятельно для каждого пользователя через веб-интерфейс или применить к БД `.dev/test-data.sql`

### У тестовых пользователей одинаковые пароли в keycloak (`12345`)

# Возможные проблемы

В ходе импорта данных в Keycloak могут сбиться `credentials` для сервисов. 
Если это произошло, нужно залогиниться в Keycloak под `admin\admin`, перегенерировать `credentials` и прописать их в `docker-compose.yaml
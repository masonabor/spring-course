# Лабораторна робота 2 - RESTful Web Service

## Виконані вимоги

### ✅ 2.1 CRUD операції для ресурсу "Student"

Реалізовані всі чотири операції:

- **CREATE**: `POST /api/greet` - створення нового привітання (201 Created)
- **READ**: `GET /api/user/{id}` - отримання користувача за ID (200 OK / 404 Not Found)
- **UPDATE**: `PUT /api/user/{id}` - повне оновлення користувача (200 OK)
- **DELETE**: `DELETE /api/user/{id}` - видалення користувача (204 No Content)

### ✅ 2.2 Фільтрація та пагінація

Endpoint: `GET /api/users`

**Параметри пагінації:**
- `page` - номер сторінки (за замовчуванням 0)
- `size` - розмір сторінки (за замовчуванням 10)

**Параметри фільтрації:**
- `status` - фільтр за статусом (active/inactive)
- `name` - фільтр за ім'ям (часткове співпадіння)

**Приклади:**
```
GET /api/users?page=0&size=5
GET /api/users?status=active
GET /api/users?name=alex
GET /api/users?page=1&size=10&status=active&name=a
```

### ✅ 2.3 Часткове оновлення (RFC 7386 - JSON Merge Patch)

Endpoint: `PATCH /api/user/{id}`

Дозволяє оновлювати лише вказані поля без необхідності передавати всі дані.

**Приклад:**
```http
PATCH /api/user/123
Content-Type: application/json

{
  "email": "newemail@example.com"
}
```

### ✅ 2.4 HTTP статус коди

Всі операції повертають відповідні статус коди:

- **200 OK** - успішний GET, PUT, PATCH
- **201 Created** - успішний POST (створення ресурсу)
- **204 No Content** - успішний DELETE
- **404 Not Found** - ресурс не знайдено

### ✅ 2.5 Безпека

Функції безпеки не реалізовані згідно з вимогами завдання.

## Як запустити

1. Перейдіть в директорію проекту:
```bash
cd lab1/hello-world-web
```

2. Запустіть додаток:
```bash
./gradlew bootRun
```

3. Додаток буде доступний на `http://localhost:5045`

## Як тестувати

### Варіант 1: IntelliJ IDEA HTTP Client

Відкрийте файл `api-tests.http` у IntelliJ IDEA та натисніть зелену стрілку біля потрібного запиту.

### Варіант 2: Postman

Імпортуйте колекцію запитів з файлу `api-tests.http` або створіть запити вручну.

### Варіант 3: curl (командний рядок)

**GET запити:**
```bash
curl http://localhost:5045/api/users
curl http://localhost:5045/api/user/1
curl "http://localhost:5045/api/users?page=0&size=5&status=active"
```

**POST запит:**
```bash
curl -X POST http://localhost:5045/api/greet \
  -H "Content-Type: application/json" \
  -d '{"name":"Тарас"}'
```

**PUT запит:**
```bash
curl -X PUT http://localhost:5045/api/user/123 \
  -H "Content-Type: application/json" \
  -d '{"name":"Updated","email":"new@email.com"}'
```

**PATCH запит:**
```bash
curl -X PATCH http://localhost:5045/api/user/123 \
  -H "Content-Type: application/json" \
  -d '{"email":"newemail@example.com"}'
```

**DELETE запит:**
```bash
curl -X DELETE http://localhost:5045/api/user/123
```

## Endpoints

| Метод | Endpoint | Опис | Статус |
|-------|----------|------|--------|
| GET | /api/hello | Просте привітання | 200 |
| GET | /api/hello?name={name} | Привітання з параметром | 200 |
| GET | /hello/{name} | Привітання з path variable | 200 |
| GET | /user/{name} | Інформація про користувача | 200 |
| POST | /api/greet | Створення привітання | 201 |
| GET | /api/user/{id} | Отримання користувача | 200/404 |
| GET | /api/users | Список користувачів (пагінація/фільтрація) | 200 |
| PUT | /api/user/{id} | Повне оновлення користувача | 200 |
| PATCH | /api/user/{id} | Часткове оновлення користувача | 200 |
| DELETE | /api/user/{id} | Видалення користувача | 204 |

## Структура проекту

```
src/main/java/com/lab1/helloworldweb/
├── controller/
│   ├── HelloController.java        # REST endpoints
│   ├── MainController.java         # Thymeleaf контролер
│   └── StudentRestController.java  # CRUD для Student
├── model/
│   └── Student.java
├── service/
│   └── StudentService.java
├── repository/
│   └── StudentRepository.java
├── dto/
│   ├── StudentRequestDto.java
│   └── StudentResponseDto.java
└── exception/
    ├── GlobalExceptionHandler.java
    └── StudentNotFoundException.java
```

## Технології

- Spring Boot 2.7.18
- Spring Web
- Spring Validation
- Java 11
- Gradle


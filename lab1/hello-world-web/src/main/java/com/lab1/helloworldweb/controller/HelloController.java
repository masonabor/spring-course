package com.lab1.helloworldweb.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
public class HelloController {

    /**
     * 2.1 - Простий GET запит без параметрів
     * GET /api/hello
     */
    @GetMapping("/api/hello")
    public Map<String, String> hello() {
        Map<String, String> response = new HashMap<>();
        response.put("message", "Hello, World!");
        return response;
    }

    /**
     * 2.2 - GET запит з query параметром
     * GET /api/hello?name=Іван
     */
    @GetMapping(value = "/api/hello", params = "name")
    public Map<String, String> helloWithParam(@RequestParam String name) {
        Map<String, String> response = new HashMap<>();
        response.put("message", "Hello, " + name + "!");
        return response;
    }

    /**
     * 2.3 - GET запит з path variable
     * GET /hello/{name}
     */
    @GetMapping("/hello/{name}")
    public Map<String, String> helloWithPathVariable(@PathVariable String name) {
        Map<String, String> response = new HashMap<>();
        response.put("message", "Hello, " + name + "!");
        response.put("path", "/hello/" + name);
        return response;
    }

    /**
     * 2.3 - GET запит з path variable для user
     * GET /user/{name}
     */
    @GetMapping("/user/{name}")
    public Map<String, String> userGreeting(@PathVariable String name) {
        Map<String, String> response = new HashMap<>();
        response.put("message", "Welcome, user " + name + "!");
        response.put("user", name);
        response.put("status", "active");
        return response;
    }

    /**
     * 2.4 - POST запит з JSON body (статус 201 Created)
     * POST /api/greet
     * Body: {"name":"Тарас"}
     */
    @PostMapping("/api/greet")
    public ResponseEntity<Map<String, String>> greetUser(@RequestBody Map<String, String> request) {
        String name = request.getOrDefault("name", "Guest");
        Map<String, String> response = new HashMap<>();
        response.put("greeting", "Greetings, " + name + "!");
        response.put("timestamp", java.time.LocalDateTime.now().toString());
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    /**
     * 2.5 - PUT запит для оновлення (статус 200 OK)
     * PUT /api/user/{id}
     */
    @PutMapping("/api/user/{id}")
    public ResponseEntity<Map<String, Object>> updateUser(@PathVariable Long id, @RequestBody Map<String, String> updates) {
        Map<String, Object> response = new HashMap<>();
        response.put("id", id);
        response.put("updates", updates);
        response.put("status", "updated");
        response.put("timestamp", java.time.LocalDateTime.now().toString());
        return ResponseEntity.ok(response);
    }

    /**
     * 2.5 - DELETE запит (статус 204 No Content)
     * DELETE /api/user/{id}
     */
    @DeleteMapping("/api/user/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        // В реальному проекті тут буде логіка видалення з бази даних
        return ResponseEntity.noContent().build();
    }

    /**
     * 2.2 - GET з пагінацією та фільтрацією
     * GET /api/users?page=0&size=10&status=active&name=Alex
     */
    @GetMapping("/api/users")
    public ResponseEntity<Map<String, Object>> getUsers(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String status,
            @RequestParam(required = false) String name) {

        // Симуляція даних (в реальному проекті - з бази даних)
        List<Map<String, Object>> users = new ArrayList<>();
        users.add(Map.of("id", 1, "name", "Alex", "email", "alex@example.com", "status", "active"));
        users.add(Map.of("id", 2, "name", "Maria", "email", "maria@example.com", "status", "active"));
        users.add(Map.of("id", 3, "name", "Ivan", "email", "ivan@example.com", "status", "inactive"));
        users.add(Map.of("id", 4, "name", "Olena", "email", "olena@example.com", "status", "active"));

        // Фільтрація
        List<Map<String, Object>> filteredUsers = users;
        if (status != null) {
            filteredUsers = filteredUsers.stream()
                    .filter(u -> status.equals(u.get("status")))
                    .collect(Collectors.toList());
        }
        if (name != null) {
            filteredUsers = filteredUsers.stream()
                    .filter(u -> ((String) u.get("name")).toLowerCase().contains(name.toLowerCase()))
                    .collect(Collectors.toList());
        }

        // Пагінація
        int start = page * size;
        int end = Math.min(start + size, filteredUsers.size());
        List<Map<String, Object>> paginatedUsers = filteredUsers.subList(
                Math.min(start, filteredUsers.size()),
                end
        );

        // Формування відповіді
        Map<String, Object> response = new HashMap<>();
        response.put("content", paginatedUsers);
        response.put("page", page);
        response.put("size", size);
        response.put("totalElements", filteredUsers.size());
        response.put("totalPages", (int) Math.ceil((double) filteredUsers.size() / size));

        // Додаємо інформацію про фільтри
        Map<String, String> appliedFilters = new HashMap<>();
        if (status != null) appliedFilters.put("status", status);
        if (name != null) appliedFilters.put("name", name);
        if (!appliedFilters.isEmpty()) {
            response.put("filters", appliedFilters);
        }

        return ResponseEntity.ok(response);
    }

    /**
     * 2.3 - PATCH для часткового оновлення (RFC 7386 - JSON Merge Patch)
     * PATCH /api/user/{id}
     * Body: {"email": "newemail@example.com"}
     */
    @PatchMapping("/api/user/{id}")
    public ResponseEntity<Map<String, Object>> partialUpdateUser(
            @PathVariable Long id,
            @RequestBody Map<String, Object> updates) {

        // В реальному проекті тут буде логіка часткового оновлення в БД
        Map<String, Object> response = new HashMap<>();
        response.put("id", id);
        response.put("updatedFields", updates);
        response.put("status", "partially updated");
        response.put("timestamp", java.time.LocalDateTime.now().toString());

        return ResponseEntity.ok(response);
    }

    /**
     * 2.4 - GET конкретного користувача з перевіркою існування
     * GET /api/user/{id}
     * Повертає 200 OK якщо знайдено, 404 Not Found якщо не існує
     */
    @GetMapping("/api/user/{id}")
    public ResponseEntity<Map<String, Object>> getUserById(@PathVariable Long id) {
        // Симуляція перевірки існування (в реальному проекті - запит до БД)
        if (id > 1000) {
            return ResponseEntity.notFound().build(); // 404 Not Found
        }

        Map<String, Object> user = new HashMap<>();
        user.put("id", id);
        user.put("name", "User" + id);
        user.put("email", "user" + id + "@example.com");
        user.put("status", "active");

        return ResponseEntity.ok(user); // 200 OK
    }
}


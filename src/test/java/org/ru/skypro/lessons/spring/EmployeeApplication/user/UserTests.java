package org.ru.skypro.lessons.spring.EmployeeApplication.user;// Подключаем нужные библиотеки для написания тестов
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

// Библиотека для создания юнит-тестов в java
import org.json.JSONObject;
import org.junit.jupiter.api.Test;

// Аннотация позволяющая использовать функции IoC-контейнера Spring
// в юнит-тестах
import org.springframework.beans.factory.annotation.Autowired;

// Аннотация говорит Spring, что мы хотим автоматически настроить MockMvc
// (активирует автоматическую настройку MockMvc)
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;

// Аннотация позволяет тестировать веб-приложение в Spring
import org.springframework.boot.test.context.SpringBootTest;

// Класс для эмуляции HTTP-запросов
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;

// Объявляем класс UserTests 
// Он предназначен для тестирования контроллера для работы с пользователями
@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)

public class UserTests {

    // Аннотация Autowired позволяет автоматически установить значение поля
    // В данном случае, мы автоматически подключаем MVC-объект,
    // который помогает с симуляцией HTTP-запросов для тестирования
    @Autowired
    MockMvc mockMvc;

    // Объявляем тестовый метод givenNoUsersInDatabase_whenGetUsers_thenEmptyJsonArray
    // Этот метод проверяет сценарий, когда в базе данных нет пользователей
    // и результат запроса GET должен быть пустым JSON-массивом
    @Test
    void givenNoUsersInDatabase_whenGetUsers_thenEmptyJsonArray() throws Exception {
        // Имитируем GET-запрос к "/user"
        mockMvc.perform(get("/admin/user"))
                // Проверяем, что статус ответа — 200 (OK)
                .andExpect(status().isOk())
                // Проверяем, что тело ответа — массив
                .andExpect(jsonPath("$").isArray())
                // Проверяем, что массив пуст
                .andExpect(jsonPath("$").isEmpty());
    }

    // Объявляем тест
    // Метод тестирования, который проверяет добавление нового пользователя
    // в систему

//    @Test
//    void givenNoUsersInDatabase_whenUserAdded_thenItExistsInList() throws Exception {
//
//        // Создаем JSON объект, который будет использован
//        // для отправки запроса на добавление пользователя
//        JSONObject jsonObject = new JSONObject();
//        jsonObject.put("name", "test_name");
//
//        // Выполняем POST запрос на адрес "/user",
//        // передаем в теле запроса наши JSON данные
//        mockMvc.perform(post("/user")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        // указываем тип контента, который мы отправляем
//                        .content(jsonObject.toString()))
//                // конвертируем наши данные в строку и отправляем
//                .andExpect(status().isOk())
//                // Ожидаем, что статус ответа будет 200 (OK)
//                .andExpect(jsonPath("$.id").isNotEmpty())
//                // Ожидаем, что поле "id" будет не пустым
//                .andExpect(jsonPath("$.id").isNumber())
//                // Ожидаем, что поле "id" будет числом
//                .andExpect(jsonPath("$.name").value("test_name"));
//        // Ожидаем, что поле "name" будет иметь значение "test_name"
//
//        // Выполняем GET запрос на адрес "/user", чтобы проверить,
//        // что наш пользователь был добавлен
//        mockMvc.perform(get("/user"))
//                .andExpect(status().isOk())
//                // Ожидаем, что статус ответа будет 200 (OK)
//                .andExpect(jsonPath("$").isArray())
//                // Ожидаем, что JSON ответ будет массивом
//                .andExpect(jsonPath("$.length()").value(1))
//                // Ожидаем, что длина массива будет равна 1
//                // (так как мы только что добавили одного пользователя)
//                .andExpect(jsonPath("$[0].name").value("test_name"));
//        // Ожидаем, что поле "name" первого (и единственного) объекта
//        // в массиве будет иметь значение "test_name"
//    }
}
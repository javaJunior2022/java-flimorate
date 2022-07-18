package ru.yandex.practicum.filmorate.controllers;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.FilmorateApplication;
import ru.yandex.practicum.filmorate.model.User;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDate;
import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class UserControllerTest {
    private static final String url = "http://localhost:8080";
    private static final Gson gson = FilmorateApplication.getGson();
    private static HttpClient client = HttpClient.newHttpClient();


    @BeforeAll
    public static void init() {
        FilmorateApplication.start();
    }
    @Test
    void addNewUser() {
        User user = new User();
        user.setName("User");
        user.setEmail("mail@mail.ru");
        user.setLogin("user");
        user.setBirthday(LocalDate.of(1997,12,12));

        String gson1 = gson.toJson(user);

        HttpRequest requestPost = HttpRequest.newBuilder()
                .uri(URI.create(url + "/users"))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(gson1))
                .build();

        // adding a film

        try {

            HttpResponse<String> responsePost = client.send(requestPost, HttpResponse.BodyHandlers.ofString());
            assertEquals(200, responsePost.statusCode());

        } catch (Exception e) {
            assertNotNull(e);
        }

        // getting the data
        HttpRequest requestGet = HttpRequest.newBuilder()
                .uri(URI.create(url + "/users"))
                .GET()
                .build();

        try {
            HttpResponse<String> responseGet = client.send(requestGet, HttpResponse.BodyHandlers.ofString());
            assertEquals(200, responseGet.statusCode());
            HashSet<User> restoredUsers =
                    gson.fromJson(responseGet.body(),
                            new TypeToken<HashSet<User>>() {
                            }.getType());
            assertEquals(1, restoredUsers.size());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        FilmorateApplication.stop();
    }


}
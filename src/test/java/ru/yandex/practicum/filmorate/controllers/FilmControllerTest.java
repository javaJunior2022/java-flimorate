package ru.yandex.practicum.filmorate.controllers;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.FilmorateApplication;
import ru.yandex.practicum.filmorate.model.Film;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDate;
import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;


class FilmControllerTest {
    private static final String url = "http://localhost:8080";
    private static final Gson gson = FilmorateApplication.getGson();
    private static HttpClient client = HttpClient.newHttpClient();


    @BeforeAll
    public static void init() {
        FilmorateApplication.start();
    }

    @Test
    void addFilmAndGetData() {
        Film film = new Film();
        film.setName("Film");
        film.setId(1);
        film.setDescription("Films description");
        film.setReleaseDate(LocalDate.of(1967, 12, 12));
        film.setDuration(120);

        String gson1 = gson.toJson(film);


        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url + "/films"))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(gson1))
                .build();

        // adding a film

        try {

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            assertEquals(200, response.statusCode());

        } catch (Exception e) {
            assertNotNull(e);
        }

        // getting the data
        HttpRequest request1 = HttpRequest.newBuilder()
                .uri(URI.create(url + "/films"))
                .GET()
                .build();

        try {
            HttpResponse<String> response1 = client.send(request1, HttpResponse.BodyHandlers.ofString());
            assertEquals(200, response1.statusCode());
            HashSet<Film> restoredFilms =
                    gson.fromJson(response1.body(),
                            new TypeToken<HashSet<Film>>() {
                            }.getType());
            assertEquals(1, restoredFilms.size());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        FilmorateApplication.stop();
    }

    @Test
    void addWrongFilmName() {
        Film film = new Film();
        film.setName("");
        film.setId(1);
        film.setDescription("Films description");
        film.setReleaseDate(LocalDate.of(1967, 12, 12));
        film.setDuration(120);

        String gson1 = gson.toJson(film);


        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url + "/films"))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(gson1))
                .build();

        // adding a film
        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            assertEquals(500, response.statusCode());
        } catch (Exception e) {
            assertNotNull(e);
        }
        FilmorateApplication.stop();
    }

    @Test
    void addWrongFilmDescription() {
        Film film = new Film();
        film.setName("Film1");
        film.setId(1);
        film.setDescription("Пятеро друзей ( комик-группа «Шарло»), приезжают в город Бризуль. Здесь они хотят разыскать господина Огюста Куглова, который задолжал им деньги, а именно 20 миллионов. о Куглов, который за время «своего отсутствия», стал кандидатом Коломбани.");
        film.setReleaseDate(LocalDate.of(1967, 12, 12));
        film.setDuration(120);

        String gson1 = gson.toJson(film);


        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url + "/films"))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(gson1))
                .build();

        // adding a film
        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            assertEquals(500, response.statusCode());
        } catch (Exception e) {
            assertNotNull(e);
        }
        FilmorateApplication.stop();
    }

    @Test
    void addWrongFilmReleaseDate() {
        Film film = new Film();
        film.setName("Film1");
        film.setId(1);
        film.setDescription("just description");
        film.setReleaseDate(LocalDate.of(1167, 12, 12));
        film.setDuration(120);

        String gson1 = gson.toJson(film);


        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url + "/films"))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(gson1))
                .build();

        // adding a film
        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            assertEquals(500, response.statusCode());
        } catch (Exception e) {
            assertNotNull(e);
        }
        FilmorateApplication.stop();
    }

    @Test
    void addWrongFilmDuration() {
        Film film = new Film();
        film.setName("Film1");
        film.setId(1);
        film.setDescription("just description");
        film.setReleaseDate(LocalDate.of(1967, 12, 12));
        film.setDuration(-4);

        String gson1 = gson.toJson(film);


        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url + "/films"))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(gson1))
                .build();

        // adding a film
        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            assertEquals(500, response.statusCode());
        } catch (Exception e) {
            assertNotNull(e);
        }
        FilmorateApplication.stop();
    }

    @Test
    void updateFilmAndGetData() {
        Film film = new Film();
        film.setName("Film");
        film.setId(1);
        film.setDescription("Films description");
        film.setReleaseDate(LocalDate.of(1967, 12, 12));
        film.setDuration(120);

        String gson1 = gson.toJson(film);


        HttpRequest requestPost = HttpRequest.newBuilder()
                .uri(URI.create(url + "/films"))
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
                .uri(URI.create(url + "/films"))
                .GET()
                .build();

        try {
            HttpResponse<String> responseGet = client.send(requestGet, HttpResponse.BodyHandlers.ofString());
            assertEquals(200, responseGet.statusCode());
            HashSet<Film> restoredFilms =
                    gson.fromJson(responseGet.body(),
                            new TypeToken<HashSet<Film>>() {
                            }.getType());
            assertEquals(1, restoredFilms.size());
            Film film2 = restoredFilms.stream().findFirst().get();
            assertEquals("Film", film2.getName());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        Film film1 = new Film();
        film1.setName("Film name new");
        film1.setId(1);
        film1.setDescription("Films description");
        film1.setReleaseDate(LocalDate.of(1967, 12, 12));
        film1.setDuration(120);

        gson1 = gson.toJson(film1);


        HttpRequest requestPut = HttpRequest.newBuilder()
                .uri(URI.create(url + "/films"))
                .header("Content-Type", "application/json")
                .PUT(HttpRequest.BodyPublishers.ofString(gson1))
                .build();

        try {

            HttpResponse<String> responsePut = client.send(requestPut, HttpResponse.BodyHandlers.ofString());
            assertEquals(200, responsePut.statusCode());

            HttpResponse<String> responseGet = client.send(requestGet, HttpResponse.BodyHandlers.ofString());
            assertEquals(200, responseGet.statusCode());
            HashSet<Film> restoredFilms =
                    gson.fromJson(responseGet.body(),
                            new TypeToken<HashSet<Film>>() {
                            }.getType());
            assertEquals(1, restoredFilms.size());
            Film film2 = restoredFilms.stream().findFirst().get();
            assertEquals(film1.getName(), film2.getName());

        } catch (Exception e) {
            assertNotNull(e);
        }

        // adding a film
        FilmorateApplication.stop();
    }

}
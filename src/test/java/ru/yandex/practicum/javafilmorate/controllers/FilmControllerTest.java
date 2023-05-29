package ru.yandex.practicum.javafilmorate.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import ru.yandex.practicum.javafilmorate.model.Film;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDate;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
class FilmControllerTest {

    private static final int PORT = 8080;

    private final ObjectMapper objectMapper = new ObjectMapper()
            .findAndRegisterModules()
            .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

    @Test
    public void shouldReturnFilmNameNullOrEmptyException() throws IOException, InterruptedException {

        final Film terminatorWithEmptyName = Film.builder().id(1)
                .name("")
                .description("Test description")
                .duration(90)
                .releaseDate(LocalDate.of(1984, 10, 26))
                .build();

        HttpClient client = HttpClient.newHttpClient();
        URI uri = URI.create("http://localhost:" + PORT + "/films");
        String requestBody = objectMapper.writeValueAsString(terminatorWithEmptyName);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(uri)
                .header("Content-Type", "application/json")
                .POST(HttpRequest
                        .BodyPublishers
                        .ofString(requestBody))
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        assertEquals(400, response.statusCode());
    }

    @Test
    public void shouldReturnFilmDescriptionTooLongException() throws IOException, InterruptedException {

        final Film terminatorWithTooLongDescription = Film.builder().id(1)
                .name("Terminator")
                .description(generateLetterString())
                .duration(90)
                .releaseDate(LocalDate.of(1984, 10, 26))
                .build();

        HttpClient client = HttpClient.newHttpClient();
        URI uri = URI.create("http://localhost:" + PORT + "/films");
        String requestBody = objectMapper.writeValueAsString(terminatorWithTooLongDescription);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(uri)
                .header("Content-Type", "application/json")
                .POST(HttpRequest
                        .BodyPublishers
                        .ofString(requestBody))
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        assertEquals(400, response.statusCode());
    }

    @Test
    public void shouldReturnFilmDurationIsNegativeValueException() throws IOException, InterruptedException {

        final Film terminatorWithNegativeDuration = Film.builder().id(1)
                .name("Terminator")
                .description("Test Description")
                .duration(-90)
                .releaseDate(LocalDate.of(1984, 10, 26))
                .build();

        HttpClient client = HttpClient.newHttpClient();
        URI uri = URI.create("http://localhost:" + PORT + "/films");
        String requestBody = objectMapper.writeValueAsString(terminatorWithNegativeDuration);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(uri)
                .header("Content-Type", "application/json")
                .POST(HttpRequest
                        .BodyPublishers
                        .ofString(requestBody))
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        assertEquals(400, response.statusCode());
    }

    @Test
    public void shouldReturnFilmReleaseDateException() throws IOException, InterruptedException {

        final Film terminatorWithTooOldReleaseDate = Film.builder().id(1)
                .name("Terminator")
                .description("Test Description")
                .duration(90)
                .releaseDate(LocalDate.of(1797, 1, 23))
                .build();

        HttpClient client = HttpClient.newHttpClient();
        URI uri = URI.create("http://localhost:" + PORT + "/films");
        String requestBody = objectMapper.writeValueAsString(terminatorWithTooOldReleaseDate);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(uri)
                .header("Content-Type", "application/json")
                .POST(HttpRequest
                        .BodyPublishers
                        .ofString(requestBody))
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        assertEquals(400, response.statusCode());
    }

    String generateLetterString() {
        int leftLimit = 97;
        int rightLimit = 122;
        Random random = new Random();

        return random.ints(leftLimit, rightLimit + 1)
                .limit(201)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
    }

}
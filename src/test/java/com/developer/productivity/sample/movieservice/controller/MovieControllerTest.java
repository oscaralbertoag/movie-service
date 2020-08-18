package com.developer.productivity.sample.movieservice.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import org.junit.jupiter.api.Test;
import org.springframework.http.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MovieControllerTest extends BaseControllerTest {

    @Test
    public void createMovieReturns201AndNewResource() {
        // GIVEN: a request to create a movie
        String request =
                "{\"name\": \"Some Awesome Movie 3\","
                        + "\"summary\": \"Super awesome movie! Better than the previous 2!\"}";

        // WHEN: a create movie request is received
        ResponseEntity<String> responseEntity = createMovie(request);

        // THEN: a 201 is received
        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        // AND: the new resource is returned and all expected fields are present
        JsonNode jsonResponse = getJsonResponse(responseEntity.getBody());
        assertNotNull(jsonResponse);
        assertTrue(jsonResponse.has("id"));
        assertEquals("Some Awesome Movie 3", jsonResponse.get("name").asText());
        assertEquals(
                "Super awesome movie! Better than the previous 2!", jsonResponse.get("summary").asText());
    }

    @Test
    public void getAllMoviesReturns200AndAllMappedResources() {
        // GIVEN: existing movies
        String request =
                "{\"name\": \"Some Awesome Movie 3\","
                        + "\"summary\": \"Super awesome movie! Better than the previous 2!\"}";
        String request2 =
                "{\"name\": \"Some Awesome Movie 4\","
                        + "\"summary\": \"Super awesome movie! Better than the previous 3!\"}";
        createMovie(request);
        createMovie(request2);

        // WHEN: all movies are fetched
        ResponseEntity<String> responseEntity =
                restTemplate.getForEntity(getUrl("/movies"), String.class);

        // THEN: a 200 is returned
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        // AND: all resources contain the expected fields
        ArrayNode arrayNode = (ArrayNode) getJsonResponse(responseEntity.getBody());
        assertNotNull(arrayNode);
        List<String> movieNames = new ArrayList<>();
        arrayNode.forEach(
                jsonNode -> {
                    assertTrue(jsonNode.has("id"));
                    assertTrue(jsonNode.has("name"));
                    assertTrue(jsonNode.has("summary"));
                    movieNames.add(jsonNode.get("name").textValue());
                });
        //  AND: all expected values are present in the response
        List<String> expectedMovieNames = Arrays.asList("Some Awesome Movie 3", "Some Awesome Movie 4");
        assertTrue(movieNames.containsAll(expectedMovieNames));
    }

    @Test
    public void getMovieByIdReturns200AndExpectedMappedResource() {
        // GIVEN: an existing movie
        String request =
                "{\"name\": \"Some Awesome Movie 3\","
                        + "\"summary\": \"Super awesome movie! Better than the previous 2!\"}";
        JsonNode existingMovie = getJsonResponse(createMovie(request).getBody());

        // WHEN: a movie is retrieved by ID
        ResponseEntity<String> responseEntity =
                restTemplate.getForEntity(
                        getUrl("/movies/" + existingMovie.get("id").asText()), String.class);

        // THEN: a 200 is returned
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        // AND: all fields are present
        JsonNode jsonResponse = getJsonResponse(responseEntity.getBody());
        assertNotNull(jsonResponse);
        assertEquals(existingMovie.get("id").asText(), jsonResponse.get("id").asText());
        assertEquals("Some Awesome Movie 3", jsonResponse.get("name").asText());
        assertEquals(
                "Super awesome movie! Better than the previous 2!", jsonResponse.get("summary").asText());
    }

    private ResponseEntity<String> createMovie(String request) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        HttpEntity<String> httpEntity = new HttpEntity<>(request, headers);
        return restTemplate.exchange(getUrl("/movies"), HttpMethod.POST, httpEntity, String.class);
    }
}

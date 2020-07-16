package com.developer.productivity.sample.movieservice.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class ContributorControllerTest {

  @LocalServerPort private int port;

  @Autowired private TestRestTemplate restTemplate;

  @Test
  public void getAllContributorTypesReturns200() {
    // WHEN: a request is made to fetch all contributor types
    ResponseEntity<String> responseEntity =
        restTemplate.getForEntity(getUrl("/contributors/types"), String.class);

    // THEN: an HTTP response is received
    assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    // AND: a list of 7 types is returned
    ArrayNode arrayNode = (ArrayNode) getJsonResponse(responseEntity.getBody());
    assertNotNull(arrayNode);
    assertEquals(7, arrayNode.size());
    // AND: every contributor type has the expected fields
    arrayNode.forEach(
        jsonNode -> {
          assertTrue(jsonNode.has("id"));
          assertTrue(jsonNode.has("name"));
        });
  }

  @Test
  public void createContributorReturns201AndNewResource() {
    // GIVEN: a contributor request
    String request =
        "{"
            + "    \"firstName\": \"Jane\","
            + "    \"lastName\": \"Smith\","
            + "    \"contributorType\": {"
            + "        \"id\": 3"
            + "    }"
            + "}";

    // WHEN: a request to create a contributor is made
    ResponseEntity<String> responseEntity = createContributor(request);

    // THEN: a 201 is returned
    assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
    // AND: the returned resource contains all expected fields
    JsonNode jsonResponse = getJsonResponse(responseEntity.getBody());
    assertNotNull(jsonResponse);
    assertTrue(jsonResponse.has("id"));
    assertEquals("Jane", jsonResponse.get("firstName").asText());
    assertEquals("Smith", jsonResponse.get("lastName").asText());
    assertEquals(3L, jsonResponse.get("contributorType").get("id").asLong());
    assertEquals("Producer", jsonResponse.get("contributorType").get("name").asText());
  }

  @Test
  public void getAllContributorsReturns200AndMappedResources() {
    // GIVEN: existing contributors
    String request1 =
        "{"
            + "    \"firstName\": \"Jane\","
            + "    \"lastName\": \"Smith\","
            + "    \"contributorType\": {"
            + "        \"id\": 1"
            + "    }"
            + "}";
    String request2 =
        "{"
            + "    \"firstName\": \"John\","
            + "    \"lastName\": \"Smith\","
            + "    \"contributorType\": {"
            + "        \"id\": 2"
            + "    }"
            + "}";
    createContributor(request1);
    createContributor(request2);

    // WHEN: all contributors are fetched
    ResponseEntity<String> responseEntity =
        restTemplate.getForEntity(getUrl("/contributors"), String.class);

    // THEN: a 200 is returned
    assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    // AND: 2 contributors are returned
    ArrayNode arrayNode = (ArrayNode) getJsonResponse(responseEntity.getBody());
    assertNotNull(arrayNode);
    // AND: all resources have the expected fields
    arrayNode.forEach(
        jsonNode -> {
          assertTrue(jsonNode.has("id"));
          assertTrue(jsonNode.has("firstName"));
          assertTrue(jsonNode.has("lastName"));
          assertTrue(jsonNode.has("contributorType"));
          assertTrue(jsonNode.get("contributorType").has("id"));
          assertTrue(jsonNode.get("contributorType").has("name"));
        });
  }

  @Test
  public void getContributorByIdReturns200AndResource() {
    // GIVEN: an existing resource
    String request =
        "{"
            + "    \"firstName\": \"Jane\","
            + "    \"lastName\": \"Smith\","
            + "    \"contributorType\": {"
            + "        \"id\": 7"
            + "    }"
            + "}";
    JsonNode existingContributor = getJsonResponse(createContributor(request).getBody());

    // WHEN: a contributor is retrieved by ID
    ResponseEntity<String> responseEntity =
        restTemplate.getForEntity(
            getUrl("/contributors/" + existingContributor.get("id").asText()), String.class);

    // THEN: a 200 is returned
    assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    // AND: all expected fields are present
    JsonNode jsonResponse = getJsonResponse(responseEntity.getBody());
    assertNotNull(jsonResponse);
    assertTrue(jsonResponse.has("id"));
    assertEquals("Jane", jsonResponse.get("firstName").asText());
    assertEquals("Smith", jsonResponse.get("lastName").asText());
    assertEquals(7L, jsonResponse.get("contributorType").get("id").asLong());
    assertEquals("Costume Designer", jsonResponse.get("contributorType").get("name").asText());
  }

  private String getUrl(String uri) {
    return "http://localhost:" + port + uri;
  }

  private JsonNode getJsonResponse(String responseBody) {
    ObjectMapper mapper = new ObjectMapper();
    try {
      return mapper.readTree(responseBody);
    } catch (JsonProcessingException e) {
      fail(
          String.format(
              "Unexpected error while trying to parse response JSON. Raw response body: %s",
              responseBody),
          e);
      return null;
    }
  }

  private ResponseEntity<String> createContributor(String request) {
    HttpHeaders headers = new HttpHeaders();
    headers.add("Content-Type", "application/json");
    HttpEntity<String> httpEntity = new HttpEntity<>(request, headers);
    return restTemplate.exchange(
        getUrl("/contributors"), HttpMethod.POST, httpEntity, String.class);
  }
}

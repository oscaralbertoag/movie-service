package com.developer.productivity.sample.movieservice.dao;

import com.developer.productivity.sample.movieservice.model.Contributor;
import com.developer.productivity.sample.movieservice.model.ContributorType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;

import javax.sql.DataSource;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@JdbcTest
class ContributorDaoImplTest {

  private final ContributorDao contributorDao;

  @Autowired
  ContributorDaoImplTest(DataSource dataSource) {
    this.contributorDao = new ContributorDaoImpl(dataSource);
  }

  @Test
  public void createContributorInsertsAndReturnsNewRecord() {
    // GIVEN: a new contributor to insert
    Contributor contributor =
        new Contributor()
            .setContributorType(new ContributorType().setId(1L))
            .setFirstName("Brie")
            .setLastName("Larson");

    // WHEN: a new contributor is created
    Contributor result = contributorDao.createContributor(contributor);

    // THEN: a new record is inserted and returned
    assertNotNull(result.getId());
    assertEquals("Brie", result.getFirstName());
    assertEquals("Larson", result.getLastName());
    assertEquals(1L, result.getContributorType().getId());
    assertEquals("Actor", result.getContributorType().getName());
  }

  @Test
  public void getAllContributorTypesReturnsAllExistingTypesWithNames() {
    // WHEN: all contributor types are fetched
    List<ContributorType> results = contributorDao.getAllContributorTypes();

    // THEN: all expected types are returned
    assertEquals(7, results.size());
    // AND: all contain a name
    results.forEach(result -> assertNotNull(result.getName()));
  }

  @Test
  public void getContributorByIdReturnsExpectedRecordWithTypeAndName() {
    // GIVEN: an existing contributor
    Contributor contributor =
        contributorDao.createContributor(
            new Contributor()
                .setFirstName("Hayley")
                .setLastName("Atwell")
                .setContributorType(new ContributorType().setId(1L)));

    // WHEN: a contributor is retrieved
    Contributor result = contributorDao.getContributorById(contributor.getId());

    // THEN: the expected record is found
    assertEquals(contributor.getId(), result.getId());
    assertEquals("Hayley", result.getFirstName());
    assertEquals("Atwell", result.getLastName());
    assertEquals(1L, result.getContributorType().getId());
    assertEquals("Actor", result.getContributorType().getName());
  }

  @Test
  public void getAllContributorsReturnsAllRecords() {
    // GIVEN: existing contributors
    contributorDao.createContributor(
        new Contributor()
            .setFirstName("Chadwick")
            .setLastName("Boseman")
            .setContributorType(new ContributorType().setId(1L)));
    contributorDao.createContributor(
        new Contributor()
            .setFirstName("Letitia")
            .setLastName("Wright")
            .setContributorType(new ContributorType().setId(1L)));
    contributorDao.createContributor(
        new Contributor()
            .setFirstName("Ryan")
            .setLastName("Coogler")
            .setContributorType(new ContributorType().setId(2L)));

    // WHEN: all contributors are fetched
    List<Contributor> results = contributorDao.getAllContributors();

    // THEN; all records are retrieved and correctly hydrated
    assertEquals(3, results.size());
    results.forEach(
        result -> {
          assertNotNull(result.getId());
          assertNotNull(result.getFirstName());
          assertNotNull(result.getLastName());
          assertNotNull(result.getContributorType().getId());
          assertNotNull(result.getContributorType().getName());
        });
  }
}

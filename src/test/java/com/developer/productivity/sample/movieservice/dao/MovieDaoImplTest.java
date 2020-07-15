package com.developer.productivity.sample.movieservice.dao;

import com.developer.productivity.sample.movieservice.model.Contributor;
import com.developer.productivity.sample.movieservice.model.ContributorType;
import com.developer.productivity.sample.movieservice.model.Movie;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;

import javax.sql.DataSource;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@JdbcTest
class MovieDaoImplTest {

  private final MovieDao movieDao;
  private final ContributorDao contributorDao;

  @Autowired
  public MovieDaoImplTest(DataSource dataSource) {
    movieDao = new MovieDaoImpl(dataSource);
    contributorDao = new ContributorDaoImpl(dataSource);
  }

  @Test
  public void createMovieReturnsNewRecord() {
    // GIVEN: a new movie to insert
    Movie movie =
        new Movie()
            .setName("The Avengers")
            .setSummary(
                "Earth's mightiest heroes must come together and learn to "
                    + "fight as a team if they are going to stop the mischievous"
                    + " Loki and his alien army from attacking humanity.");

    // WHEN: a new movie is inserted into the database
    Movie result = movieDao.createMovie(movie);

    // THEN: a new record is inserted and returned with the correct fields
    assertNotNull(result.getId());
    assertEquals(
        "Earth's mightiest heroes must come together and learn to "
            + "fight as a team if they are going to stop the mischievous"
            + " Loki and his alien army from attacking humanity.",
        result.getSummary());
    assertEquals("The Avengers", result.getName());
  }

  @Test
  public void findAllReturnsAllAvailableRecords() {
    // GIVEN: 3 existing movies
    Movie avengers =
        new Movie()
            .setName("The Avengers")
            .setSummary(
                "Earth's mightiest heroes must come together and learn to "
                    + "fight as a team if they are going to stop the mischievous"
                    + " Loki and his alien army from attacking humanity.");

    Movie ageOfUltron =
        new Movie()
            .setName("Avengers: Age of Ultron")
            .setSummary(
                "When Tony Stark and Bruce Banner try to jump-start a dormant "
                    + "peacekeeping program called Ultron, things go horribly "
                    + "wrong and it's up to Earth's mightiest heroes to stop "
                    + "the villainous Ultron from enacting his terrible plan.");
    Movie infinityWar =
        new Movie()
            .setName("Avengers: Infinity War")
            .setSummary(
                "After the devastating events of Avengers: Infinity War, "
                    + "the universe is in ruins. With the help of remaining "
                    + "allies, the Avengers assemble once more in order to "
                    + "reverse Thanos' actions and restore balance to the universe.");
    movieDao.createMovie(avengers);
    movieDao.createMovie(ageOfUltron);
    movieDao.createMovie(infinityWar);

    // WHEN: all movies are fetched
    List<Movie> results = movieDao.findAll();

    // THEN: all existing movies are returned
    assertEquals(3, results.size());
    results.forEach(
        movie -> {
          assertNotNull(movie.getId());
          assertNotNull(movie.getName());
          assertNotNull(movie.getSummary());
        });
  }

  @Test
  public void getByIdReturnsExpectedRecord() {
    // GIVEN: an existing movie
    Movie captainAmerica =
        new Movie()
            .setName("Captain America: The First Avenger")
            .setSummary(
                "Steve Rogers, a rejected military soldier, transforms into"
                    + " Captain America after taking a dose of a"
                    + " \\\"Super-Soldier serum\\\". But being Captain America "
                    + "comes at a price as he attempts to take down a war "
                    + "monger and a terrorist organization.");
    movieDao.createMovie(captainAmerica);

    // WHEN: a movie is retrieved by its ID
    Movie result = movieDao.getById(captainAmerica.getId());

    // THEN: the expected movie is returned
    assertNotNull(result.getId());
    assertEquals("Captain America: The First Avenger", result.getName());
    assertEquals(
        "Steve Rogers, a rejected military soldier, transforms into"
            + " Captain America after taking a dose of a"
            + " \\\"Super-Soldier serum\\\". But being Captain America "
            + "comes at a price as he attempts to take down a war "
            + "monger and a terrorist organization.",
        result.getSummary());
  }

  @Test
  public void deleteByIdRemovesRecordWhenIdMatches() {
    // GIVEN: an existing movie
    Movie winterSoldier =
        movieDao.createMovie(
            new Movie()
                .setName("Captain America: The Winter Soldier")
                .setSummary(
                    "As Steve Rogers struggles to embrace his role in the modern "
                        + "world, he teams up with a fellow Avenger and"
                        + " S.H.I.E.L.D agent, Black Widow, to battle a new "
                        + "threat from history: an assassin known as the Winter Soldier. "));

    // WHEN: the movie is deleted
    boolean result = movieDao.deleteById(winterSoldier.getId());

    // THEN: the record was deleted successfully
    assertTrue(result);
  }

  @Test
  public void deleteByIdDoesNotRemoveRecordWhenIdDoesNotMatch() {
    // GIVEN: an existing movie
    Movie winterSoldier =
        movieDao.createMovie(
            new Movie()
                .setName("Captain America: The Winter Soldier")
                .setSummary(
                    "As Steve Rogers struggles to embrace his role in the modern "
                        + "world, he teams up with a fellow Avenger and"
                        + " S.H.I.E.L.D agent, Black Widow, to battle a new "
                        + "threat from history: an assassin known as the Winter Soldier. "));

    // WHEN: a delete is attempted with the incorrect ID
    boolean result = movieDao.deleteById("INCORRECT-ID-1234");

    // THEN: no records are deleted
    assertFalse(result);
  }

  @Test
  public void updateMovieModifiesExistingRecord() {
    // GIVEN: an existing movie
    Movie captainMarvel =
        movieDao.createMovie(new Movie().setName("Captain something").setSummary("TBD"));

    // WHEN: it is updated
    Movie result =
        movieDao.updateMovie(
            new Movie()
                .setId(captainMarvel.getId())
                .setName("Captain Marvel")
                .setSummary(
                    "Carol Danvers becomes one of the universe's most powerful heroes "
                        + "when Earth is caught in the middle of a galactic war between two alien races."));

    // THEN: the existing record is updated
    assertEquals(captainMarvel.getId(), result.getId());
    assertEquals("Captain Marvel", result.getName());
    assertEquals(
        "Carol Danvers becomes one of the universe's most powerful heroes "
            + "when Earth is caught in the middle of a galactic war between two alien races.",
        result.getSummary());
  }

  @Test
  public void addMovieContributorsReturnsAllAddedRecords() {
    // GIVEN: a movie
    Movie blackPanther =
        movieDao.createMovie(
            new Movie()
                .setName("Black Panther")
                .setSummary(
                    "T'Challa, heir to the hidden but advanced kingdom of Wakanda,"
                        + " must step forward to lead his people into a new "
                        + "future and must confront a challenger from his country's past."));
    // AND: a few contributors
    Contributor rCoogler =
        contributorDao.createContributor(
            new Contributor()
                .setFirstName("Ryan")
                .setLastName("Coogler")
                .setContributorType(new ContributorType().setId(2L)));
    Contributor lWright =
        contributorDao.createContributor(
            new Contributor()
                .setFirstName("Letitia")
                .setLastName("Wright")
                .setContributorType(new ContributorType().setId(1L)));
    Contributor hAtwell =
        contributorDao.createContributor(
            new Contributor()
                .setFirstName("Hayley")
                .setLastName("Atwell")
                .setContributorType(new ContributorType().setId(1L)));

    // WHEN: contributors are added to a movie
    List<Contributor> results =
        movieDao.addMovieContributors(
            blackPanther.getId(), Arrays.asList(rCoogler.getId(), lWright.getId()));

    // THEN: only this movie's records are returned
    assertEquals(2, results.size());
    Map<String, Contributor> resultsMap =
        results.stream().collect(Collectors.toMap(Contributor::getFirstName, Function.identity()));
    // AND: their fields are mapped correctly
    Contributor ryanResult = resultsMap.get("Ryan");
    assertEquals("Ryan", ryanResult.getFirstName());
    assertEquals("Coogler", ryanResult.getLastName());
    assertEquals("Director", ryanResult.getContributorType().getName());
    assertEquals(2L, ryanResult.getContributorType().getId());
    Contributor letitia = resultsMap.get("Letitia");
    assertEquals("Letitia", letitia.getFirstName());
    assertEquals("Wright", letitia.getLastName());
    assertEquals("Actor", letitia.getContributorType().getName());
    assertEquals(1L, letitia.getContributorType().getId());
  }

  @Test
  public void removeMovieContributorRemovesRecordAndReturnsUpdatedContributors() {
    // GIVEN: a movie
    Movie blackPanther =
        movieDao.createMovie(
            new Movie()
                .setName("Black Panther")
                .setSummary(
                    "T'Challa, heir to the hidden but advanced kingdom of Wakanda,"
                        + " must step forward to lead his people into a new "
                        + "future and must confront a challenger from his country's past."));
    // AND: a few contributors
    Contributor rCoogler =
        contributorDao.createContributor(
            new Contributor()
                .setFirstName("Ryan")
                .setLastName("Coogler")
                .setContributorType(new ContributorType().setId(2L)));
    Contributor lWright =
        contributorDao.createContributor(
            new Contributor()
                .setFirstName("Letitia")
                .setLastName("Wright")
                .setContributorType(new ContributorType().setId(1L)));
    List<Contributor> movieContributors =
        movieDao.addMovieContributors(
            blackPanther.getId(), Arrays.asList(rCoogler.getId(), lWright.getId()));

    // WHEN: a movie contributor is removed
    List<Contributor> results =
        movieDao.removeMovieContributor(blackPanther.getId(), rCoogler.getId());

    // THEN: the record is removed and the remaining records are returned
    assertEquals(1, results.size());
    assertEquals("Letitia", results.get(0).getFirstName());
    assertEquals("Wright", results.get(0).getLastName());
    assertEquals("Actor", results.get(0).getContributorType().getName());
    assertEquals(1L, results.get(0).getContributorType().getId());
  }
}

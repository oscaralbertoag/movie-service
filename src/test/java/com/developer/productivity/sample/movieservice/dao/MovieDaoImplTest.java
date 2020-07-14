package com.developer.productivity.sample.movieservice.dao;

import com.developer.productivity.sample.movieservice.model.Movie;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;

import javax.sql.DataSource;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@JdbcTest
class MovieDaoImplTest {

  private final MovieDao movieDao;

  @Autowired
  public MovieDaoImplTest(DataSource dataSource) {
    movieDao = new MovieDaoImpl(dataSource);
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
}

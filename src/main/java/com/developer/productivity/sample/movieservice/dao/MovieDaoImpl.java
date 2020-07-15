package com.developer.productivity.sample.movieservice.dao;

import com.developer.productivity.sample.movieservice.model.Contributor;
import com.developer.productivity.sample.movieservice.model.ContributorType;
import com.developer.productivity.sample.movieservice.model.Movie;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;
import java.util.UUID;

@Repository
public class MovieDaoImpl implements MovieDao {

  private final JdbcTemplate jdbcTemplate;
  private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;
  private final RowMapper<Movie> movieRowMapper =
      (rs, rowNum) ->
          new Movie()
              .setId(rs.getString("id"))
              .setName(rs.getString("name"))
              .setSummary(rs.getString("summary"));

  public MovieDaoImpl(DataSource dataSource) {
    this.jdbcTemplate = new JdbcTemplate(dataSource);
    this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
  }

  @Override
  public List<Movie> findAll() {
    return jdbcTemplate.query("SELECT * FROM movie", movieRowMapper);
  }

  @Override
  public Movie createMovie(Movie movie) {
    movie.setId(generateId());
    namedParameterJdbcTemplate.update(
        "INSERT INTO movie (id, name, summary) VALUES (:id, :name, :summary)",
        new MapSqlParameterSource()
            .addValue("id", movie.getId())
            .addValue("name", movie.getName())
            .addValue("summary", movie.getSummary()));
    return getById(movie.getId());
  }

  @Override
  public boolean deleteById(String id) {
    int modifiedRows =
        namedParameterJdbcTemplate.update(
            "DELETE FROM movie WHERE id = :id", new MapSqlParameterSource().addValue("id", id));
    return modifiedRows > 0;
  }

  @Override
  public Movie updateMovie(Movie movie) {
    namedParameterJdbcTemplate.update(
        "UPDATE movie SET name = :name, summary = :summary WHERE id = :id",
        new MapSqlParameterSource()
            .addValue("id", movie.getId())
            .addValue("name", movie.getName())
            .addValue("summary", movie.getSummary()));
    return getById(movie.getId());
  }

  @Override
  public Movie getById(String id) {
    return namedParameterJdbcTemplate.queryForObject(
        "SELECT id, name, summary FROM movie WHERE id = :id",
        new MapSqlParameterSource().addValue("id", id),
        movieRowMapper);
  }

  @Override
  public List<Contributor> addMovieContributors(String movieId, List<String> contributorIds) {
    SqlParameterSource[] batchValues = new SqlParameterSource[contributorIds.size()];

    for (int i = 0; i < contributorIds.size(); i++) {
      batchValues[i] =
          new MapSqlParameterSource()
              .addValue("id", generateId())
              .addValue("movieId", movieId)
              .addValue("contributorId", contributorIds.get(i));
    }
    namedParameterJdbcTemplate.batchUpdate(
        "INSERT INTO movie_contributor ( id, movie_id, contributor_id)"
            + " VALUES (:id, :movieId, :contributorId)",
        batchValues);

    return getAllMovieContributors(movieId);
  }

  @Override
  public List<Contributor> getAllMovieContributors(String movieId) {
    return namedParameterJdbcTemplate.query(
        "SELECT mc.id as movie_contributor_id, mc.movie_id, mc.contributor_id, c.first_name, c.last_name, c.contributor_type_id, ct.name  FROM movie_contributor mc "
            + "JOIN contributor c ON c.id = mc.contributor_id "
            + "JOIN contributor_type ct ON ct.id = c.contributor_type_id "
            + "WHERE movie_id = :movieId",
        new MapSqlParameterSource().addValue("movieId", movieId),
        ((rs, rowNum) ->
            new Contributor()
                .setId(rs.getString("contributor_id"))
                .setFirstName(rs.getString("first_name"))
                .setLastName(rs.getString("last_name"))
                .setContributorType(
                    new ContributorType(rs.getLong("contributor_type_id"), rs.getString("name")))));
  }

  @Override
  public List<Contributor> removeMovieContributor(String movieId, String contributorId) {
    namedParameterJdbcTemplate.update(
        "DELETE FROM movie_contributor WHERE movie_id = :movieId AND contributor_id = :contributorId",
        new MapSqlParameterSource()
            .addValue("movieId", movieId)
            .addValue("contributorId", contributorId));
    return getAllMovieContributors(movieId);
  }

  private String generateId() {
    return UUID.randomUUID().toString();
  }
}

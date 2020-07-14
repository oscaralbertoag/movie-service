package com.developer.productivity.sample.movieservice.dao;

import com.developer.productivity.sample.movieservice.model.Movie;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;
import java.util.UUID;

@Repository
public class MovieDaoImpl implements MovieDao {

  private final JdbcTemplate jdbcTemplate;
  private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

  public MovieDaoImpl(DataSource dataSource) {
    this.jdbcTemplate = new JdbcTemplate(dataSource);
    this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
  }

  @Override
  public List<Movie> findAll() {
    return jdbcTemplate.query(
        "SELECT * FROM movie",
        ((rs, rowNum) ->
            new Movie()
                .setId(rs.getString("id"))
                .setName(rs.getString("name"))
                .setSummary(rs.getString("summary"))));
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
        (rs, rowNum) ->
            new Movie()
                .setId(rs.getString("id"))
                .setName(rs.getString("name"))
                .setSummary(rs.getString("summary")));
  }

  private String generateId() {
    return UUID.randomUUID().toString();
  }
}

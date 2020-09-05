package com.developer.productivity.sample.movieservice.dao;

import com.developer.productivity.sample.movieservice.model.Contributor;
import com.developer.productivity.sample.movieservice.model.Movie;

import java.util.List;

public interface MovieDao {

  /**
   * Fetch all existing movies in the database
   *
   * @return non-null list containing all movie records
   */
  List<Movie> findAll();

  /**
   * Inserts a new movie into the database
   *
   * @param movie object to be inserted
   * @return newly inserted object
   */
  Movie createMovie(Movie movie);

  /**
   * Delete a movie record by its given ID
   *
   * @param id ID of the record to delete
   * @return true if any records were impacted; false otherwise
   */
  boolean deleteById(String id);

  /**
   * Update an existing movie record
   *
   * @param movie record to update
   * @return updated movie record
   */
  Movie updateMovie(Movie movie);

  /**
   * Fetch a movie record by its given ID
   *
   * @param id ID of the movie record to fetch
   * @return object matching the given ID
   */
  Movie getById(String id);

  /**
   * Create a new relationship between a contributor and a movie
   *
   * @param movieId ID of the movie to associate
   * @param contributorIds ID of the contributor to associate
   * @return non-null list of contributors currently associated with the provided movie ID
   */
  List<Contributor> addMovieContributors(String movieId, List<String> contributorIds);

  /**
   * Fetch all contributor records currently associated with a movie
   *
   * @param movieId ID of the movie
   * @return all contributor records currently associated with the provided movie ID
   */
  List<Contributor> getAllMovieContributors(String movieId);

  /**
   * Delete relationship between a contributor and a movie
   *
   * @param movieId ID of the movie to dissociate
   * @param contributorId ID of the contributor to dissociate
   * @return non-null list with the updated contributors associated with the provided movie ID
   */
  List<Contributor> removeMovieContributor(String movieId, String contributorId);
}

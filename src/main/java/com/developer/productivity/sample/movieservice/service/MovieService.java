package com.developer.productivity.sample.movieservice.service;

import com.developer.productivity.sample.movieservice.model.Contributor;
import com.developer.productivity.sample.movieservice.model.Movie;

import java.util.List;

public interface MovieService {

  /**
   * Retrieves all movies in the system
   *
   * @return non-null list of all movies in the system
   */
  List<Movie> getAllMovies();

  /**
   * Creates a new movie
   *
   * @param movie object to create
   * @return new movie
   */
  Movie createMovie(Movie movie);

  /**
   * Delete a movie (and its related data) by its given ID.
   *
   * @param id ID of movie to be deleted
   */
  void deleteMovie(String id);

  /**
   * Retrieve a movie by its given ID
   *
   * @param id ID of movie to fetch
   * @return movie matching the provided ID
   */
  Movie getMovieById(String id);

  /**
   * Replace a movie completely with the provided movie
   *
   * @param movie object to replace the existing one
   * @return the modified version of the movie object
   */
  Movie replaceMovie(Movie movie);

  /**
   * Adds a contributor to an existing movie
   *
   * @param movieId ID of the movie that will receive the contributor
   * @param contributorIds ID of the contributor to be added
   * @return non-list of contributors currently associated with the provided movie ID
   */
  List<Contributor> addMovieContributors(String movieId, List<String> contributorIds);

  /**
   * Retrieve all contributors currently associated with a particular movie
   *
   * @param movieId ID of the movie for which contributors will be fetched
   * @return non-null list of contributors currently associated with the provided movie ID
   */
  List<Contributor> getMovieContributors(String movieId);

  /**
   * Remove a contributor from a particular movie
   *
   * @param movieId ID of the movie from which a contributor will be removed
   * @param contributorId ID of the contributor to remove
   * @return non-null list of contributors currently associated with the provided movie ID
   */
  List<Contributor> removeMovieContributor(String movieId, String contributorId);
}

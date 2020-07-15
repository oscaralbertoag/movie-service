package com.developer.productivity.sample.movieservice.dao;

import com.developer.productivity.sample.movieservice.model.Contributor;
import com.developer.productivity.sample.movieservice.model.Movie;

import java.util.List;

public interface MovieDao {

  List<Movie> findAll();

  Movie createMovie(Movie movie);

  boolean deleteById(String id);

  Movie updateMovie(Movie movie);

  Movie getById(String id);

  List<Contributor> addMovieContributors(String movieId, List<String> contributorIds);

  List<Contributor> getAllMovieContributors(String movieId);

  List<Contributor> removeMovieContributor(String movieId, String contributorId);
}

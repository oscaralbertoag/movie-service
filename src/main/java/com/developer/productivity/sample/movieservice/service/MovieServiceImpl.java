package com.developer.productivity.sample.movieservice.service;

import com.developer.productivity.sample.movieservice.dao.MovieDao;
import com.developer.productivity.sample.movieservice.model.Contributor;
import com.developer.productivity.sample.movieservice.model.Movie;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MovieServiceImpl implements MovieService {

  private final MovieDao movieDao;

  public MovieServiceImpl(MovieDao movieDao) {
    this.movieDao = movieDao;
  }

  @Override
  public List<Movie> getAllMovies() {
    return movieDao.findAll();
  }

  @Override
  public Movie createMovie(Movie movie) {
    return movieDao.getById(movieDao.createMovie(movie).getId());
  }

  @Override
  public void deleteMovie(String id) {
    movieDao
        .getAllMovieContributors(id)
        .forEach(movieContributor -> movieDao.removeMovieContributor(id, movieContributor.getId()));
    movieDao.deleteById(id);
  }

  @Override
  public Movie getMovieById(String id) {
    return movieDao.getById(id);
  }

  @Override
  public Movie replaceMovie(Movie movie) {
    Movie original = movieDao.getById(movie.getId());
    if (original == null) {
      throw new IllegalArgumentException(
          String.format("Movie id provided does not exist! ID:%s", movie.getId()));
    }
    return movieDao.updateMovie(movie);
  }

  @Override
  public List<Contributor> addMovieContributors(String movieId, List<String> contributorIds) {
    return movieDao.addMovieContributors(movieId, contributorIds);
  }

  @Override
  public List<Contributor> getMovieContributors(String movieId) {
    return movieDao.getAllMovieContributors(movieId);
  }

  @Override
  public List<Contributor> removeMovieContributor(String movieId, String contributorId) {
    return movieDao.removeMovieContributor(movieId, contributorId);
  }
}

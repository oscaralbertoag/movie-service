package com.developer.productivity.sample.movieservice.service;

import com.developer.productivity.sample.movieservice.dao.MovieDao;
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
}

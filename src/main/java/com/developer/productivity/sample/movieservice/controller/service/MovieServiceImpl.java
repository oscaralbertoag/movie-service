package com.developer.productivity.sample.movieservice.controller.service;

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
    return movieDao.getById(movieDao.save(movie).getId());
  }

  @Override
  public void deleteMovie(Long id) {
    movieDao.deleteById(id);
  }

  @Override
  public Movie getMovieById(Long id) {
    return movieDao.getById(id);
  }

  @Override
  public Movie replaceMovie(Movie movie) {
    Movie original = movieDao.getById(movie.getId());
    if (original == null) {
      throw new IllegalArgumentException(
          String.format("Movie id provided does not exist! ID:%d", movie.getId()));
    }
    return movieDao.save(movie);
  }
}

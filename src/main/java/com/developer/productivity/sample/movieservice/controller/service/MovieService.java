package com.developer.productivity.sample.movieservice.controller.service;

import com.developer.productivity.sample.movieservice.dto.MovieDto;
import com.developer.productivity.sample.movieservice.model.Movie;

import java.util.List;

public interface MovieService {

    List<Movie> getAllMovies();

    Movie createMovie(Movie movie);

    void deleteMovie(Long id);

    Movie getMovieById(Long id);

    Movie replaceMovie(Movie movie);
}

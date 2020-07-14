package com.developer.productivity.sample.movieservice.service;

import com.developer.productivity.sample.movieservice.model.Movie;

import java.util.List;

public interface MovieService {

    List<Movie> getAllMovies();

    Movie createMovie(Movie movie);

    void deleteMovie(String id);

    Movie getMovieById(String id);

    Movie replaceMovie(Movie movie);
}

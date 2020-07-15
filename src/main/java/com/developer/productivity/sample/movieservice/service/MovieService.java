package com.developer.productivity.sample.movieservice.service;

import com.developer.productivity.sample.movieservice.dto.ContributorDto;
import com.developer.productivity.sample.movieservice.model.Contributor;
import com.developer.productivity.sample.movieservice.model.Movie;

import java.util.List;

public interface MovieService {

    List<Movie> getAllMovies();

    Movie createMovie(Movie movie);

    void deleteMovie(String id);

    Movie getMovieById(String id);

    Movie replaceMovie(Movie movie);

    List<Contributor> addMovieContributors(String movieId, List<String> contributorIds);

    List<Contributor> getMovieContributors(String movieId);

    List<Contributor> removeMovieContributor(String movieId, String contributorId);
}

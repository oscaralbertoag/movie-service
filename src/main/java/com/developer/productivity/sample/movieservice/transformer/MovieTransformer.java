package com.developer.productivity.sample.movieservice.transformer;

import com.developer.productivity.sample.movieservice.dto.MovieDto;
import com.developer.productivity.sample.movieservice.model.Movie;

public class MovieTransformer {

  public static MovieDto toMovieDto(Movie movie) {
    return new MovieDto()
        .setId(movie.getId())
        .setName(movie.getName())
        .setSummary(movie.getSummary());
  }

  public static Movie toMovie(MovieDto movieDto) {
    return new Movie()
        .setId(movieDto.getId())
        .setName(movieDto.getName())
        .setSummary(movieDto.getSummary());
  }
}

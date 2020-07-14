package com.developer.productivity.sample.movieservice.controller;

import com.developer.productivity.sample.movieservice.dto.MovieDto;
import com.developer.productivity.sample.movieservice.model.Movie;
import com.developer.productivity.sample.movieservice.service.MovieService;
import com.developer.productivity.sample.movieservice.transformer.MovieTransformer;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class MovieController {

  private final MovieService movieService;

  public MovieController(MovieService movieService) {
    this.movieService = movieService;
  }

  @GetMapping("/movies")
  public List<MovieDto> getAllMovies() {
    return movieService.getAllMovies().stream()
        .map(MovieTransformer::toMovieDto)
        .collect(Collectors.toList());
  }

  @GetMapping("/movies/{id}")
  public MovieDto getMovieById(@PathVariable("id") String id) {
    return MovieTransformer.toMovieDto(movieService.getMovieById(id));
  }

  @PostMapping("/movies")
  public MovieDto createMovie(@RequestBody MovieDto movie) {
    return MovieTransformer.toMovieDto(movieService.createMovie(MovieTransformer.toMovie(movie)));
  }

  @DeleteMapping("/movies/{id}")
  public void deleteMovie(@PathVariable("id") String id) {
    movieService.deleteMovie(id);
  }

  @PutMapping("/movies/{id}")
  public Movie replaceMovie(@RequestBody Movie movie, @PathVariable("id") String id) {
    return movieService.replaceMovie(movie.setId(id));
  }
}

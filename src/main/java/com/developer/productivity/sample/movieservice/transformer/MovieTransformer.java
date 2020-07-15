package com.developer.productivity.sample.movieservice.transformer;

import com.developer.productivity.sample.movieservice.dto.ContributorDto;
import com.developer.productivity.sample.movieservice.dto.ContributorTypeDto;
import com.developer.productivity.sample.movieservice.dto.MovieDto;
import com.developer.productivity.sample.movieservice.model.Contributor;
import com.developer.productivity.sample.movieservice.model.ContributorType;
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

  public static ContributorDto toContributorDto(Contributor contributor) {
    return new ContributorDto()
        .setId(contributor.getId())
        .setFirstName(contributor.getFirstName())
        .setLastName(contributor.getLastName())
        .setContributorType(
            new ContributorTypeDto(
                contributor.getContributorType().getId(),
                contributor.getContributorType().getName()));
  }

  public static Contributor toContributor(ContributorDto contributorDto) {
    return new Contributor()
        .setId(contributorDto.getId())
        .setFirstName(contributorDto.getFirstName())
        .setLastName(contributorDto.getLastName())
        .setContributorType(
            new ContributorType(
                contributorDto.getContributorType().getId(),
                contributorDto.getContributorType().getName()));
  }
}

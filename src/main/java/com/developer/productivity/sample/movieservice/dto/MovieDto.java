package com.developer.productivity.sample.movieservice.dto;

import java.util.Objects;

public class MovieDto {
  private long id;
  private String name;
  private String summary;

  public MovieDto() {}

  public String getName() {
    return name;
  }

  public MovieDto setName(String name) {
    this.name = name;
    return this;
  }

  public long getId() {
    return id;
  }

  public MovieDto setId(long id) {
    this.id = id;
    return this;
  }

  public String getSummary() {
    return summary;
  }

  public MovieDto setSummary(String summary) {
    this.summary = summary;
    return this;
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, name, summary);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    MovieDto movieDto = (MovieDto) o;
    return id == movieDto.id
        && Objects.equals(name, movieDto.name)
        && Objects.equals(summary, movieDto.summary);
  }

  @Override
  public String toString() {
    return "MovieDto{"
        + "id="
        + id
        + ", name='"
        + name
        + '\''
        + ", summary='"
        + summary
        + '\''
        + '}';
  }
}

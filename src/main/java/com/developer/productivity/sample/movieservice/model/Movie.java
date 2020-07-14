package com.developer.productivity.sample.movieservice.model;

public class Movie {

  private String id;
  private String name;
  private String summary;

  public Movie() {}

  public Movie(String id, String name, String summary) {
    this.id = id;
    this.name = name;
    this.summary = summary;
  }

  public String getId() {
    return id;
  }

  public Movie setId(String id) {
    this.id = id;
    return this;
  }

  public String getName() {
    return name;
  }

  public Movie setName(String name) {
    this.name = name;
    return this;
  }

  public String getSummary() {
    return summary;
  }

  public Movie setSummary(String summary) {
    this.summary = summary;
    return this;
  }
}

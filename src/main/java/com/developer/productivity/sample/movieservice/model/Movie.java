package com.developer.productivity.sample.movieservice.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "movies")
public class Movie {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private long id;

  private String name;
  private String summary;

  public Movie() {}

  public Movie(long id, String name, String summary) {
    this.id = id;
    this.name = name;
    this.summary = summary;
  }

  public long getId() {
    return id;
  }

  public Movie setId(long id) {
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

package com.developer.productivity.sample.movieservice.model;

import java.util.Objects;

public class ContributorType {

  private Long id;
  private String name;

  public ContributorType() {
  }

  public ContributorType(Long id, String name) {
    this.id = id;
    this.name = name;
  }

  public Long getId() {
    return id;
  }

  public ContributorType setId(Long id) {
    this.id = id;
    return this;
  }

  public String getName() {
    return name;
  }

  public ContributorType setName(String name) {
    this.name = name;
    return this;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    ContributorType that = (ContributorType) o;
    return Objects.equals(id, that.id) && Objects.equals(name, that.name);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, name);
  }

  @Override
  public String toString() {
    return "ContributorType{" + "id=" + id + ", name='" + name + '\'' + '}';
  }
}

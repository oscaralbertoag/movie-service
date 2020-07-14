package com.developer.productivity.sample.movieservice.dto;

import java.util.Objects;

public class ContributorTypeDto {
  private Long id;
  private String name;

  public ContributorTypeDto() {}

  public ContributorTypeDto(Long id, String name) {
    this.id = id;
    this.name = name;
  }

  public Long getId() {
    return id;
  }

  public ContributorTypeDto setId(Long id) {
    this.id = id;
    return this;
  }

  public String getName() {
    return name;
  }

  public ContributorTypeDto setName(String name) {
    this.name = name;
    return this;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    ContributorTypeDto that = (ContributorTypeDto) o;
    return Objects.equals(id, that.id) && Objects.equals(name, that.name);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, name);
  }

  @Override
  public String toString() {
    return "ContributorTypeDto{" + "id=" + id + ", name='" + name + '\'' + '}';
  }
}

package com.developer.productivity.sample.movieservice.dto;

import java.util.Objects;

public class ContributorDto {
  private String id;
  private String firstName;
  private String lastName;
  private ContributorTypeDto contributorType;

  public ContributorDto() {}

  public String getId() {
    return id;
  }

  public ContributorDto setId(String id) {
    this.id = id;
    return this;
  }

  public String getFirstName() {
    return firstName;
  }

  public ContributorDto setFirstName(String firstName) {
    this.firstName = firstName;
    return this;
  }

  public String getLastName() {
    return lastName;
  }

  public ContributorDto setLastName(String lastName) {
    this.lastName = lastName;
    return this;
  }

  public ContributorTypeDto getContributorType() {
    return contributorType;
  }

  public ContributorDto setContributorType(ContributorTypeDto contributorType) {
    this.contributorType = contributorType;
    return this;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    ContributorDto that = (ContributorDto) o;
    return Objects.equals(id, that.id)
        && Objects.equals(firstName, that.firstName)
        && Objects.equals(lastName, that.lastName)
        && Objects.equals(contributorType, that.contributorType);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, firstName, lastName, contributorType);
  }

  @Override
  public String toString() {
    return "ContributorDto{"
        + "id="
        + id
        + ", firstName='"
        + firstName
        + '\''
        + ", lastName='"
        + lastName
        + '\''
        + ", contributorTypeDto="
        + contributorType
        + '}';
  }
}

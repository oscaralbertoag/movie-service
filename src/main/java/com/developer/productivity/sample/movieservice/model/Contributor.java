package com.developer.productivity.sample.movieservice.model;

import javax.persistence.OneToOne;
import java.util.Objects;

public class Contributor {

  private String id;
  private String firstName;
  private String lastName;
  private ContributorType contributorType;

  public Contributor() {}

  public String getId() {
    return id;
  }

  public Contributor setId(String id) {
    this.id = id;
    return this;
  }

  public String getFirstName() {
    return firstName;
  }

  public Contributor setFirstName(String firstName) {
    this.firstName = firstName;
    return this;
  }

  public String getLastName() {
    return lastName;
  }

  public Contributor setLastName(String lastName) {
    this.lastName = lastName;
    return this;
  }

  public ContributorType getContributorType() {
    return contributorType;
  }

  public Contributor setContributorType(ContributorType contributorType) {
    this.contributorType = contributorType;
    return this;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Contributor that = (Contributor) o;
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
    return "Contributor{"
        + "id="
        + id
        + ", firstName='"
        + firstName
        + '\''
        + ", lastName='"
        + lastName
        + '\''
        + ", contributorType="
        + contributorType
        + '}';
  }
}

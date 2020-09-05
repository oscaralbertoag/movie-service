package com.developer.productivity.sample.movieservice.service;

import com.developer.productivity.sample.movieservice.model.Contributor;
import com.developer.productivity.sample.movieservice.model.ContributorType;

import java.util.List;

public interface ContributorService {

  /**
   * Creates a new contributor
   *
   * @param contributor contributor to create
   * @return the new contributor
   */
  Contributor createContributor(Contributor contributor);

  /**
   * Returns all contributor types available in the system
   *
   * @return a non-null list of contributor types that exist in the system
   */
  List<ContributorType> getAllContributorTypes();

  /**
   * Returns all contributors in the system
   *
   * @return a non-null list of all contributors in the system
   */
  List<Contributor> getAllContributors();

  /**
   * Retrieves a contributor by its given ID
   *
   * @param id ID of the contributor to retrieve
   * @return contributor matching the given ID
   */
  Contributor getContributorById(String id);
}

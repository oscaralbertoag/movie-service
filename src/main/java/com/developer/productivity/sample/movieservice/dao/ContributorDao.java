package com.developer.productivity.sample.movieservice.dao;

import com.developer.productivity.sample.movieservice.model.Contributor;
import com.developer.productivity.sample.movieservice.model.ContributorType;

import java.util.List;

public interface ContributorDao {

  /**
   * Fetch all existing contributor types from the database
   *
   * @return non-null list of all contributor types available in the database
   */
  List<ContributorType> getAllContributorTypes();

  /**
   * Insert a new contributor to the database
   *
   * @param contributor object to insert
   * @return newly inserted object
   */
  Contributor createContributor(Contributor contributor);

  /**
   * Fetch a contributor by its associated ID
   *
   * @param id ID of the contributor to fetch from the database
   * @return mapped object matching the provided ID
   */
  Contributor getContributorById(String id);

  /**
   * Fetch all existing contributors in the database
   *
   * @return non-null list of contributors in the database
   */
  List<Contributor> getAllContributors();
}

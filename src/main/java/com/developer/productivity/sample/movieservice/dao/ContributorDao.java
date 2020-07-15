package com.developer.productivity.sample.movieservice.dao;

import com.developer.productivity.sample.movieservice.model.Contributor;
import com.developer.productivity.sample.movieservice.model.ContributorType;

import java.util.List;

public interface ContributorDao {

  List<ContributorType> getAllContributorTypes();

  Contributor createContributor(Contributor contributor);

  Contributor getContributorById(String id);

  List<Contributor> getAllContributors();
}

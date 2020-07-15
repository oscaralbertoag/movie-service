package com.developer.productivity.sample.movieservice.service;

import com.developer.productivity.sample.movieservice.dao.ContributorDao;
import com.developer.productivity.sample.movieservice.model.Contributor;
import com.developer.productivity.sample.movieservice.model.ContributorType;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ContributorServiceImpl implements ContributorService {

  private final ContributorDao contributorDao;

  public ContributorServiceImpl(ContributorDao contributorDao) {
    this.contributorDao = contributorDao;
  }

  @Override
  public Contributor createContributor(Contributor contributor) {
    return contributorDao.createContributor(contributor);
  }

  @Override
  public List<ContributorType> getAllContributorTypes() {
    return contributorDao.getAllContributorTypes();
  }

  @Override
  public List<Contributor> getAllContributors() {
    return contributorDao.getAllContributors();
  }

  @Override
  public Contributor getContributorById(String id) {
    return contributorDao.getContributorById(id);
  }
}

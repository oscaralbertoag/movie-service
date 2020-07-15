package com.developer.productivity.sample.movieservice.service;

import com.developer.productivity.sample.movieservice.dto.ContributorDto;
import com.developer.productivity.sample.movieservice.model.Contributor;
import com.developer.productivity.sample.movieservice.model.ContributorType;

import java.util.List;

public interface ContributorService {
    Contributor createContributor(Contributor contributor);

    List<ContributorType> getAllContributorTypes();

    List<Contributor> getAllContributors();

    Contributor getContributorById(String id);
}

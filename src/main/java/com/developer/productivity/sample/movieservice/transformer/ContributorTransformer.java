package com.developer.productivity.sample.movieservice.transformer;

import com.developer.productivity.sample.movieservice.dto.ContributorDto;
import com.developer.productivity.sample.movieservice.dto.ContributorTypeDto;
import com.developer.productivity.sample.movieservice.model.Contributor;
import com.developer.productivity.sample.movieservice.model.ContributorType;

public class ContributorTransformer {

  public static Contributor toContributor(ContributorDto contributor) {
    return new Contributor()
        .setId(contributor.getId())
        .setFirstName(contributor.getFirstName())
        .setLastName(contributor.getLastName())
        .setContributorType(toContributorType(contributor.getContributorType()));
  }

  public static ContributorType toContributorType(ContributorTypeDto contributorTypeDto) {
    return new ContributorType(contributorTypeDto.getId(), contributorTypeDto.getName());
  }

  public static ContributorDto toContributorDto(Contributor contributor) {
    return new ContributorDto()
        .setId(contributor.getId())
        .setFirstName(contributor.getFirstName())
        .setLastName(contributor.getLastName())
        .setContributorType(toContributorTypeDto(contributor.getContributorType()));
  }

  public static ContributorTypeDto toContributorTypeDto(ContributorType contributorType) {
    return new ContributorTypeDto(contributorType.getId(), contributorType.getName());
  }
}

package com.developer.productivity.sample.movieservice.controller;

import com.developer.productivity.sample.movieservice.dto.ContributorDto;
import com.developer.productivity.sample.movieservice.dto.ContributorTypeDto;
import com.developer.productivity.sample.movieservice.service.ContributorService;
import com.developer.productivity.sample.movieservice.transformer.ContributorTransformer;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class ContributorController {

  private final ContributorService contributorService;

  public ContributorController(ContributorService contributorService) {
    this.contributorService = contributorService;
  }

  @PostMapping("/contributors")
  public ContributorDto createContributor(@RequestBody ContributorDto contributor) {
    return ContributorTransformer.toContributorDto(
        contributorService.createContributor(ContributorTransformer.toContributor(contributor)));
  }

  @GetMapping("/contributors/types")
  public List<ContributorTypeDto> getAllContributorTypes() {
    return contributorService.getAllContributorTypes().stream()
        .map(ContributorTransformer::toContributorTypeDto)
        .collect(Collectors.toList());
  }
}

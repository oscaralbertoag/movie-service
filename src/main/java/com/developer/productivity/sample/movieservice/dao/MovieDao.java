package com.developer.productivity.sample.movieservice.dao;

import com.developer.productivity.sample.movieservice.model.Movie;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface MovieDao extends CrudRepository<Movie, Long> {

  List<Movie> findAll();

  Movie getById(Long id);
}

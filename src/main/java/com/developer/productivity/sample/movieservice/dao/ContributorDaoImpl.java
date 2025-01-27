package com.developer.productivity.sample.movieservice.dao;

import com.developer.productivity.sample.movieservice.model.Contributor;
import com.developer.productivity.sample.movieservice.model.ContributorType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;
import java.util.UUID;

@Repository
public class ContributorDaoImpl implements ContributorDao {

  private final JdbcTemplate jdbcTemplate;
  private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;
  private final RowMapper<Contributor> contributorRowMapper =
      (rs, rowNum) ->
          new Contributor()
              .setId(rs.getString("contributor_id"))
              .setLastName(rs.getString("last_name"))
              .setFirstName(rs.getString("first_name"))
              .setContributorType(
                  new ContributorType(rs.getLong("contributor_type_id"), rs.getString("name")));
  private Logger log = LoggerFactory.getLogger(ContributorDaoImpl.class);

  public ContributorDaoImpl(DataSource dataSource) {
    this.jdbcTemplate = new JdbcTemplate(dataSource);
    this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
  }

  @Override
  public List<ContributorType> getAllContributorTypes() {
    log.debug("Fetching all existing contributor types");
    return jdbcTemplate.query(
        "SELECT * FROM contributor_type",
        (rs, rowNum) -> new ContributorType(rs.getLong("id"), rs.getString("name")));
  }

  @Override
  public Contributor createContributor(Contributor contributor) {
    contributor.setId(generateId());
    namedParameterJdbcTemplate.update(
        "INSERT INTO contributor (id, last_name, first_name, contributor_type_id) "
            + "VALUES (:id, :lastName, :firstName, :contributorTypeId)",
        new MapSqlParameterSource()
            .addValue("id", contributor.getId())
            .addValue("lastName", contributor.getLastName())
            .addValue("firstName", contributor.getFirstName())
            .addValue("contributorTypeId", contributor.getContributorType().getId()));

    log.debug("Inserted new contributor with ID:{}", contributor.getId());
    return getContributorById(contributor.getId());
  }

  @Override
  public Contributor getContributorById(String id) {
    return namedParameterJdbcTemplate.queryForObject(
        "SELECT c.id as contributor_id, c.last_name, c.first_name, c.contributor_type_id, ct.id, ct.name "
            + "FROM contributor c "
            + "JOIN contributor_type ct ON ct.id = c.contributor_type_id "
            + "WHERE c.id = :id",
        new MapSqlParameterSource().addValue("id", id),
        contributorRowMapper);
  }

  @Override
  public List<Contributor> getAllContributors() {
    log.debug("Fetching all available contributors");
    return jdbcTemplate.query(
        "SELECT c.id as contributor_id, c.last_name, c.first_name, c.contributor_type_id, ct.id, ct.name "
            + "FROM contributor c "
            + "JOIN contributor_type ct ON ct.id = c.contributor_type_id ",
        contributorRowMapper);
  }

  private String generateId() {
    return UUID.randomUUID().toString();
  }
}

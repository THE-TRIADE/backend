package imd.ufrn.familyroutine.repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import imd.ufrn.familyroutine.model.Dependent;
import imd.ufrn.familyroutine.model.FamilyGroup;
import imd.ufrn.familyroutine.repository.mappers.DependentMapper;
import imd.ufrn.familyroutine.repository.mappers.FamilyGroupMapper;

@Repository
public class FamilyGroupRepositoryImpl implements FamilyGroupRepository {

  @Autowired
  private JdbcTemplate jdbcTemplate;

  @Override
  public List<FamilyGroup> findAll() { 
    String sql = "SELECT * FROM family_group";
    return jdbcTemplate.query(sql, new FamilyGroupMapper());
  }

  @Override
  public Optional<FamilyGroup> findById(Long id) {
    String sql = "SELECT * FROM family_group WHERE id = ?";
    try {
        return Optional.of(jdbcTemplate.queryForObject(sql, new FamilyGroupMapper(), id));
    }
    catch(EmptyResultDataAccessException ex) {
        return Optional.empty();
    }
  }

  @Override
  public Optional<FamilyGroup> findByDependentId(Long dependentId) {
    String sql = "SELECT * FROM family_group INNER JOIN dependent ON family_group.id = dependent.familyGroupId WHERE dependent.personId = ?";
    try {
        return Optional.of(jdbcTemplate.queryForObject(sql, new FamilyGroupMapper(), dependentId));
    }
    catch(EmptyResultDataAccessException ex) {
        return Optional.empty();
    }
  }

  @Override
  public FamilyGroup save(FamilyGroup familyGroup) {
    KeyHolder keyHolder = new GeneratedKeyHolder();
    String sql = "INSERT INTO family_group (`name`) VALUES (?)";
    
    
    jdbcTemplate.update(connection -> {
      PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
      ps.setString(1, familyGroup.getName());
      return ps;
    }, keyHolder);

    return this.findById(keyHolder.getKey().longValue()).get();
  }
  
  @Override
  public void deleteById(Long id) {
    String sql = "DELETE FROM family_group WHERE id = ?";
    jdbcTemplate.update(sql, new Object[] { id });
  }

  @Override
  public void deleteAll() {
    String sql = "DELETE FROM family_group";
    jdbcTemplate.update(sql);
  }

  @Override
  public List<Dependent> findDependentsByFamilyGroupId(Long familyGroupId){
      String sql = "SELECT * FROM PERSON INNER JOIN DEPENDENT ON PERSON.id = DEPENDENT.personId WHERE familyGroupId = ?";
      try {
        return jdbcTemplate.query(sql, new DependentMapper(), familyGroupId);
      }
      catch(EmptyResultDataAccessException ex) {
          return new ArrayList<>();
      }
  }

}

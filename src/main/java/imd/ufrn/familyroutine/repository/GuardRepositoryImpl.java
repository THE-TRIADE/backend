package imd.ufrn.familyroutine.repository;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import imd.ufrn.familyroutine.model.Guard;
import imd.ufrn.familyroutine.repository.mappers.GuardMapper;

@Repository
public class GuardRepositoryImpl implements GuardRepository {

  @Autowired
  private JdbcTemplate jdbcTemplate;

  @Override
  public List<Guard> findByGuardianId(Long id) {
    String sql = "SELECT * FROM guard WHERE guardianId = ?";
    return jdbcTemplate.query(sql, new GuardMapper(), id);
  }

  @Override
  public List<Guard> findByDependentId(Long id) {
    String sql = "SELECT * FROM guard WHERE dependentId = ?";
    return jdbcTemplate.query(sql, new GuardMapper(), id);
  }

  @Override
  public Guard save(Guard guard) {
    String sql = "INSERT INTO guard (dependentId, guardianId, daysOfWeek, guardianRole) VALUES (?,?,?,?)";
    jdbcTemplate.update(sql, 
        guard.getDependentId(), 
        guard.getGuardianId(), 
        guard.getDaysOfWeek().stream().map(Enum::toString).collect(Collectors.joining(",")),
        guard.getGuardianRole().toString()
    );
    return guard;
  }

  @Override
  public void deleteByGuardianIdAndDependentId(Long guardianId, Long dependentId) {
    String sql = "DELETE FROM guard WHERE guardianId = ? AND dependentId = ?";
    jdbcTemplate.update(sql, new Object[] { guardianId, dependentId });
  }

  @Override
  public void deleteAll() {
    String sql = "DELETE FROM guard";
    jdbcTemplate.update(sql);
  }

}

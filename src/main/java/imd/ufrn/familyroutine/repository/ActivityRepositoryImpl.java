package imd.ufrn.familyroutine.repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import imd.ufrn.familyroutine.model.Activity;
import imd.ufrn.familyroutine.repository.mappers.ActivityMapper;

@Repository
public class ActivityRepositoryImpl implements ActivityRepository {

  @Autowired
  private JdbcTemplate jdbcTemplate;

  @Override
  public List<Activity> findAll() { 
    String sql = "SELECT * FROM activity";
    return jdbcTemplate.query(sql, new ActivityMapper());
  }

  @Override
  public Optional<Activity> findById(Long id) {
    String sql = "SELECT * FROM activity WHERE id = ?";
    try {
        return Optional.of(jdbcTemplate.queryForObject(sql, new ActivityMapper(), id));
    }
    catch(EmptyResultDataAccessException ex) {
        return Optional.empty();
    }
  }

  @Override
  public Activity save(Activity activity) {
    KeyHolder keyHolder = new GeneratedKeyHolder();
    String sql = "INSERT INTO activity (`name`,`description`,dateStart,dateEnd,hourStart,hourEnd,`state`,commentary,dependentId,currentGuardianId,actorId,createdBy,groupActivityId) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?)";
    
    
    jdbcTemplate.update(connection -> {
      PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
      ps.setString(1, activity.getName());
      ps.setString(2, activity.getDescription());
      ps.setDate(3, activity.getDateStart());
      ps.setDate(4, activity.getDateEnd());
      ps.setTime(5, activity.getHourStart());
      ps.setTime(6, activity.getHourEnd());
      ps.setString(7, activity.getState().toString());
      ps.setString(8, activity.getCommentary());
      ps.setLong(9, activity.getDependentId());
      ps.setLong(10, activity.getCurrentGuardian());
      ps.setLong(11, activity.getActor());
      ps.setLong(12, activity.getCreatedBy());
      if (activity.getGroupActivityId() != null){
        ps.setLong(13, activity.getGroupActivityId());
      }
      else {
        ps.setNull(13, 0);
      }

      return ps;
    }, keyHolder);

    return this.findById(keyHolder.getKey().longValue()).get();
  }

  @Override
  public Activity update(Activity activity) {
    String sql = "UPDATE activity SET `name` = ?, `description` = ?, dateStart = ?, dateEnd = ?, hourStart = ?, hourEnd = ?, `state` = ?, commentary = ?, dependentId = ?, currentGuardianId = ?, actorId = ?, createdBy = ?, finishedBy = ? WHERE id = ?";
    
    jdbcTemplate.update(sql, 
            activity.getName(),
            activity.getDescription(),
            activity.getDateStart(),
            activity.getDateEnd(),
            activity.getHourStart(),
            activity.getHourEnd(),
            activity.getState().toString(),
            activity.getCommentary(),
            activity.getDependentId(),
            activity.getCurrentGuardian(),
            activity.getActor(),
            activity.getCreatedBy(),
            activity.getFinishedBy(),
            activity.getId());
    return activity;
  }
  
  @Override
  public void deleteById(Long id) {
    String sql = "DELETE FROM activity WHERE id = ?";
    jdbcTemplate.update(sql, new Object[] { id });
  }

  @Override
  public void deleteAll() {
    String sql = "DELETE FROM activity";
    jdbcTemplate.update(sql);
  }

}

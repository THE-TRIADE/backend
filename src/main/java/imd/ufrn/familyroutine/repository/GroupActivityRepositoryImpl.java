package imd.ufrn.familyroutine.repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import imd.ufrn.familyroutine.model.GroupActivity;
import imd.ufrn.familyroutine.repository.mappers.GroupActivityMapper;

@Repository
public class GroupActivityRepositoryImpl implements GroupActivityRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public Optional<GroupActivity> findById(Long id) {
        String sql = "SELECT * FROM group_activity WHERE id = ?";       
        try {
            return Optional.of(jdbcTemplate.queryForObject(sql, new GroupActivityMapper(), id));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        } 
    }

    @Override
    public GroupActivity save(GroupActivity activity) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        String sql = "INSERT INTO group_activity () VALUES ()";

        jdbcTemplate.update(connection -> { 
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            return ps;
        }, keyHolder);
    
        return this.findById(keyHolder.getKey().longValue()).get();
    }

    @Override
    public void deleteById(Long id) {
        String sql = "DELETE FROM group_activity WHERE id = ?";
        jdbcTemplate.update(sql, new Object[] { id });
    }

    @Override
    public void deleteAll() {
        String sql = "DELETE FROM group_activity";
        jdbcTemplate.update(sql);
    }
}

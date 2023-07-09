package imd.ufrn.familyroutine.repository.mappers;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.lang.Nullable;

import imd.ufrn.familyroutine.model.GroupActivity;

public class GroupActivityMapper implements RowMapper<GroupActivity> {

    @Override
    @Nullable
    public GroupActivity mapRow(ResultSet resultSet, int rowNumber) throws SQLException {
        GroupActivity groupActivity = new GroupActivity();
        groupActivity.setId(resultSet.getLong("id"));
        return groupActivity;
    }
    
}

package imd.ufrn.familyroutine.repository.mappers;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.lang.Nullable;

import imd.ufrn.familyroutine.model.GuardianInFamilyGroup;

public class GuardianInFamilyGroupMapper implements RowMapper<GuardianInFamilyGroup> {

    @Override
    @Nullable
    public GuardianInFamilyGroup mapRow(ResultSet resultSet, int rowNumber) throws SQLException {
        GuardianInFamilyGroup guardianinfamilygroup = new GuardianInFamilyGroup();
        guardianinfamilygroup.setGuardianId(resultSet.getBigDecimal("guardianId").longValue());  
        guardianinfamilygroup.setFamilyGroupId(resultSet.getBigDecimal("familyGroupId").longValue());  
        return guardianinfamilygroup;
    }
    
}

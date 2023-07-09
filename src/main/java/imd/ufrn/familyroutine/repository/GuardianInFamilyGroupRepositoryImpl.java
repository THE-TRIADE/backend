package imd.ufrn.familyroutine.repository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import imd.ufrn.familyroutine.model.GuardianInFamilyGroup;
import imd.ufrn.familyroutine.repository.mappers.GuardianInFamilyGroupMapper;

@Repository
public class GuardianInFamilyGroupRepositoryImpl implements GuardianInFamilyGroupRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public List<GuardianInFamilyGroup> findByGuardianId(Long guardianId) {
        String sql = "SELECT * FROM guardian_in_family_group WHERE guardianId = ?";
        try {
            return jdbcTemplate.query(sql, new GuardianInFamilyGroupMapper(), guardianId);
        }
        catch(EmptyResultDataAccessException ex) {
            return new ArrayList<>();
        }
    }

    @Override
    public List<GuardianInFamilyGroup> findByFamilyGroupId(Long familyGroupId) {
        String sql = "SELECT * FROM guardian_in_family_group WHERE familyGroupId = ?";
        try {
            return jdbcTemplate.query(sql, new GuardianInFamilyGroupMapper(), familyGroupId);
        }
        catch(EmptyResultDataAccessException ex) {
            return new ArrayList<>();
        }
    }

    @Override
    public GuardianInFamilyGroup save(GuardianInFamilyGroup guardianInFamilyGroup) {
        String sql = "INSERT INTO guardian_in_family_group (`guardianId`, `familyGroupId`) VALUES (?,?)";
        jdbcTemplate.update(sql, guardianInFamilyGroup.getGuardianId(), guardianInFamilyGroup.getFamilyGroupId());
        return guardianInFamilyGroup;
    }

    @Override
    public void deleteByGuardianId(Long guardianId) {
        String sql = "DELETE FROM guardian_in_family_group WHERE guardianId = ?";
        jdbcTemplate.update(sql, new Object[] { guardianId });
    }

    @Override
    public void deleteByFamilyGroupId(Long familyGroupId) {
        String sql = "DELETE FROM guardian_in_family_group WHERE familyGroupId = ?";
        jdbcTemplate.update(sql, new Object[] { familyGroupId });
    }
}

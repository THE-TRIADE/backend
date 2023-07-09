package imd.ufrn.familyroutine.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import imd.ufrn.familyroutine.model.GuardianInFamilyGroup;
import imd.ufrn.familyroutine.repository.GuardianInFamilyGroupRepository;

@Service
public class GuardianInFamilyGroupService {
    @Autowired
    private GuardianInFamilyGroupRepository guardianInFamilyGroupRepository;

    protected GuardianInFamilyGroup createGuardianInFamilyGroup(GuardianInFamilyGroup newGuardianInFamilyGroup) {
        return this.guardianInFamilyGroupRepository.save(newGuardianInFamilyGroup);
    }

    protected List<GuardianInFamilyGroup> findGuardianInFamilyGroupsByGuardianId(Long guardianId) {
        return this.guardianInFamilyGroupRepository.findByGuardianId(guardianId);
    }

    protected List<GuardianInFamilyGroup> findGuardianInFamilyGroupsByFamilyGroupId(Long familyGroupId) {
        return this.guardianInFamilyGroupRepository.findByFamilyGroupId(familyGroupId);
    }

    protected void deleteGuardianInFamilyGroupsByFamilyGroupId(Long familyGroupId) {
        this.guardianInFamilyGroupRepository.deleteByFamilyGroupId(familyGroupId);
    }

    protected void deleteGuardianInFamilyGroupsByGuardianId(Long guardianId) {
        this.guardianInFamilyGroupRepository.deleteByGuardianId(guardianId);
    }
}

package imd.ufrn.familyroutine.repository;

import java.util.List;

import imd.ufrn.familyroutine.model.GuardianInFamilyGroup;

public interface GuardianInFamilyGroupRepository {

  List<GuardianInFamilyGroup> findByGuardianId(Long guardianId);
  
  List<GuardianInFamilyGroup> findByFamilyGroupId(Long familyGroupId);

  GuardianInFamilyGroup save(GuardianInFamilyGroup guardianInFamilyGroup);

  void deleteByGuardianId(Long guardianId);

  void deleteByFamilyGroupId(Long familyGroupId);
}

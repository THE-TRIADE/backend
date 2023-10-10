package imd.ufrn.familyroutine.repository;


import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import imd.ufrn.familyroutine.model.FamilyGroup;

public interface FamilyGroupRepository extends JpaRepository<FamilyGroup, Long>{
  Optional<FamilyGroup> findFamilyGroupByDependentsId(Long dependentId);

  List<FamilyGroup> findFamilyGroupByGuardiansId(Long guardianId);
}

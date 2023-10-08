package imd.ufrn.familyroutine.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import imd.ufrn.familyroutine.model.FamilyGroup;

public interface FamilyGroupRepository extends JpaRepository<FamilyGroup, Long>{

  List<FamilyGroup> findFamilyGroupByGuardiansId(Long guardianId);
}

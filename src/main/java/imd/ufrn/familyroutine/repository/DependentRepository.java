package imd.ufrn.familyroutine.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import imd.ufrn.familyroutine.model.Dependent;
import imd.ufrn.familyroutine.model.FamilyGroup;

public interface DependentRepository extends JpaRepository<Dependent, Long> {
    Optional<FamilyGroup> findFamilyGroupById(Long dependentId);

    List<Dependent> findDependentsByFamilyGroupId(Long familyGroupId);
}
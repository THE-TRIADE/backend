package imd.ufrn.familyroutine.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import imd.ufrn.familyroutine.model.Guard;
import imd.ufrn.familyroutine.model.id.GuardId;

public interface GuardRepository extends JpaRepository<Guard, GuardId> {

  List<Guard> findByGuardianId(Long id);

  List<Guard> findByDependentId(Long id);

  void deleteByGuardianIdAndDependentId(Long guardianId, Long dependentId);

  Optional<Guard> findByGuardianIdAndDependentId(Long guardianId, Long dependentId);
}

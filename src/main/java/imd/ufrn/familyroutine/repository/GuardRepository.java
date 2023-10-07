package imd.ufrn.familyroutine.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import imd.ufrn.familyroutine.model.Guard;

public interface GuardRepository extends JpaRepository<Guard, Long> {

  List<Guard> findByGuardianId(Long id);

  List<Guard> findByDependentId(Long id);

  void deleteByGuardianIdAndDependentId(Long guardianId, Long dependentId);
}

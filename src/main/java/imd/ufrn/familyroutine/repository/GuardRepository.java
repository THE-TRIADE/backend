package imd.ufrn.familyroutine.repository;

import java.util.List;

import imd.ufrn.familyroutine.model.Guard;

public interface GuardRepository {

  List<Guard> findByGuardianId(Long id);

  List<Guard> findByDependentId(Long id);

  Guard save(Guard guard);

  void deleteByGuardianIdAndDependentId(Long guardianId, Long dependentId);

  void deleteAll();
}

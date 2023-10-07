package imd.ufrn.familyroutine.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import imd.ufrn.familyroutine.model.Spent;

public interface SpentRepository extends JpaRepository<Spent, Long> {
  List<Spent> findByGuardianId(Long guardianId);
  List<Spent> findByDependentId(Long dependentId);
}

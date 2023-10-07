package imd.ufrn.familyroutine.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import imd.ufrn.familyroutine.model.Dependent;

public interface DependentRepository extends JpaRepository<Dependent, Long> {
}
package imd.ufrn.familyroutine.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import imd.ufrn.familyroutine.model.Dependent;

public interface DependentRepository extends JpaRepository<Dependent, Long> {
    List<Dependent> findDependentsByFamilyGroupId(Long familyGroupId);

    Optional<Dependent> findByCpf(String cpf);
}
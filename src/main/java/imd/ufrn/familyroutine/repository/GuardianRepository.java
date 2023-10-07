package imd.ufrn.familyroutine.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import imd.ufrn.familyroutine.model.Guardian;

public interface GuardianRepository extends JpaRepository<Guardian, Long> {
    Optional<Guardian> findByEmail(String email);
}

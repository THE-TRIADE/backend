package imd.ufrn.familyroutine.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import imd.ufrn.familyroutine.model.Activity;

public interface ActivityRepository extends JpaRepository<Activity, Long>{
}

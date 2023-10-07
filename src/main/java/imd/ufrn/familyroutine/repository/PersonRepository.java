package imd.ufrn.familyroutine.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import imd.ufrn.familyroutine.model.Person;

public interface PersonRepository extends JpaRepository<Person, Long>  {
}
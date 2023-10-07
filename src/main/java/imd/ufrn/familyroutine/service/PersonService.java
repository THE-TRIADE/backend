package imd.ufrn.familyroutine.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import imd.ufrn.familyroutine.model.Dependent;
import imd.ufrn.familyroutine.model.Guardian;
import imd.ufrn.familyroutine.model.Person;
import imd.ufrn.familyroutine.repository.PersonRepository;
import imd.ufrn.familyroutine.service.exception.EntityNotFoundException;

@Service
public class PersonService {

    @Autowired
    private PersonRepository personRepository;

    public Person findPersonById(Long personId) {
        return this.personRepository
                   .findById(personId)
                   .orElseThrow(() -> new EntityNotFoundException(personId, Person.class));
    }

    protected void deletePersonById(Long personId) {
        this.personRepository.deleteById(personId);
    }
    
    protected void deleteAllDependents(List<Dependent> dependents) {
        dependents.forEach(dependent -> this.deletePersonById(dependent.getId()));
    }

    protected void deleteAllGuardians(List<Guardian> guardians) {
        guardians.forEach(guardian -> this.deletePersonById(guardian.getId()));
    }

}

package imd.ufrn.familyroutine.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import imd.ufrn.familyroutine.model.Dependent;
import imd.ufrn.familyroutine.model.FamilyGroup;
import imd.ufrn.familyroutine.repository.DependentRepository;
import imd.ufrn.familyroutine.service.exception.EntityNotFoundException;

@Service
public class DependentService{
    @Autowired
    private DependentRepository dependentRepository;
    @Autowired
    private PersonService personService;

    public List<Dependent> findAll() {
        return this.dependentRepository.findAll();
    }

    public Dependent findDependentById(Long dependentId) {
        return this.dependentRepository
            .findById(dependentId)
            .orElseThrow(() -> new EntityNotFoundException(dependentId, Dependent.class));
    }

    @Transactional
    public void deleteAllDependents() {
        List<Dependent> dependents = this.findAll();
        this.personService.deleteAllDependents(dependents);
    }

    public void deleteDependentById(Long dependentId) {
        this.personService.deletePersonById(dependentId);
    }


    public Dependent createDependent(Dependent newDependent) {
        return this.dependentRepository.save(newDependent);
    }

    public Optional<FamilyGroup> findFamilyGroupByDependentId(Long dependentId){
        return this.dependentRepository.findFamilyGroupById(dependentId);
    }

    public List<Dependent> findDependentsByFamilyGroupId(Long familyGroupId){
        return this.dependentRepository.findDependentsByFamilyGroupId(familyGroupId);
    }

}

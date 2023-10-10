package imd.ufrn.familyroutine.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import imd.ufrn.familyroutine.model.Dependent;
import imd.ufrn.familyroutine.model.FamilyGroup;
import imd.ufrn.familyroutine.model.api.DependentMapper;
import imd.ufrn.familyroutine.model.api.request.DependentRequest;
import imd.ufrn.familyroutine.model.api.response.DependentResponse;
import imd.ufrn.familyroutine.repository.DependentRepository;
import imd.ufrn.familyroutine.service.exception.EntityNotFoundException;

@Service
public class DependentService{
    @Autowired
    private DependentRepository dependentRepository;
    @Autowired
    private DependentMapper dependentMapper;

    public List<DependentResponse> findAll() {
        return this.dependentRepository.findAll()
        .stream()
        .map(dependentMapper::mapDependentToDependentResponse)
        .toList();
    }

    public DependentResponse findDependentById(Long dependentId) {
        return 
        this.dependentMapper
            .mapDependentToDependentResponse(
                this.dependentRepository
                    .findById(dependentId)
                    .orElseThrow(() -> new EntityNotFoundException(dependentId, Dependent.class)));
    }

    public Dependent findDependentModelById(Long dependentId) {
        return 
            this.dependentRepository
                .findById(dependentId)
                .orElseThrow(() -> new EntityNotFoundException(dependentId, Dependent.class));
    }

    @Transactional
    public void deleteAllDependents() {
        this.dependentRepository.deleteAll();
    }

    public void deleteDependentById(Long dependentId) {
        this.dependentRepository.deleteById(dependentId);
    }

    // FIXME: vide controller
    // public DependentResponse createDependent(DependentRequest newDependent) {
    //     Dependent dependent = this.dependentMapper.mapDependentRequestToDependent(newDependent, null)
    //     return this.dependentRepository.save();
    // }

    public DependentResponse createDependentWithFamilyGroup(DependentRequest newDependent, FamilyGroup familyGroup) {
        Dependent dependent = this.dependentMapper.mapDependentRequestToDependent(newDependent, familyGroup);
        return
            this.dependentMapper.mapDependentToDependentResponse(  
                this.dependentRepository.save(dependent));
    }

    public Optional<FamilyGroup> findFamilyGroupByDependentId(Long dependentId){
        return this.dependentRepository.findFamilyGroupById(dependentId);
    }

    public List<Dependent> findDependentsByFamilyGroupId(Long familyGroupId){
        return this.dependentRepository.findDependentsByFamilyGroupId(familyGroupId);
    }

}

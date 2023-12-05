package imd.ufrn.familyroutine.service;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import imd.ufrn.familyroutine.model.Dependent;
import imd.ufrn.familyroutine.model.FamilyGroup;
import imd.ufrn.familyroutine.model.Guard;
import imd.ufrn.familyroutine.model.Guardian;
import imd.ufrn.familyroutine.model.api.DependentMapper;
import imd.ufrn.familyroutine.model.api.FamilyGroupMapper;
import imd.ufrn.familyroutine.model.api.request.FamilyGroupRequest;
import imd.ufrn.familyroutine.model.api.response.DependentResponse;
import imd.ufrn.familyroutine.model.api.response.FamilyGroupResponse;
import imd.ufrn.familyroutine.repository.FamilyGroupRepository;
import imd.ufrn.familyroutine.service.exception.EntityNotFoundException;

@Service
public class FamilyGroupService{
    @Autowired
    private FamilyGroupRepository familyGroupRepository;
    @Autowired
    private DependentService dependentService;
    @Autowired
    private GuardService guardService;
    @Autowired
    private FamilyGroupMapper familyGroupMapper;
    @Autowired
    private DependentMapper dependentMapper;

    public List<FamilyGroupResponse> findFamilyGroupsByGuardianId(Long guardianId) {
        return
        this.familyGroupRepository
            .findFamilyGroupByGuardiansId(guardianId)
            .stream()
            .map(familyGroupMapper::mapFamilyGroupToFamilyGroupResponse)
            .toList();
    }

    public FamilyGroupResponse findFamilyGroupById(Long familyGroupId) {
        return 
        this.familyGroupMapper
            .mapFamilyGroupToFamilyGroupResponse(
                this.familyGroupRepository
                    .findById(familyGroupId)
                    .orElseThrow(
                        () -> new EntityNotFoundException(familyGroupId, FamilyGroup.class)
                    )
            );
    }

    @Transactional
    public void deleteFamilyGroupById(Long familyGroupId) {
        // FIXME: verificar se o jpa realiza automaticamente esta operacao
        // this.getFamilyGroupDependentsByFamilyGroupId(familyGroupId)
            // .forEach((familyGroup) -> {
            //     dependentService.deleteDependentById(familyGroup.getId());
            // });
        this.familyGroupRepository.deleteById(familyGroupId);
    }
    
    @Transactional
    public FamilyGroupResponse createFamilyGroupWithGuardianAndDependents(FamilyGroupRequest familyGroupRequest) {
        FamilyGroup familyGroup = this.familyGroupRepository.save(familyGroupMapper.mapFamilyGroupRequestToFamilyGroup(familyGroupRequest));
        
        Guardian guardian = familyGroup.getGuardians().iterator().next();

        familyGroupRequest.getDependents().forEach(dependentRequest -> {
            DependentResponse dependentResponse = dependentService.createDependentWithFamilyGroup(dependentRequest, familyGroup);
            Dependent dependent = dependentMapper.mapDependentRequestToDependent(dependentRequest, familyGroup);
            dependent.setId(dependentResponse.getId());

            createNewGuard(familyGroupRequest, guardian, dependent);
        });

        return this.familyGroupMapper.mapFamilyGroupToFamilyGroupResponse(familyGroup);
    }

    private void createNewGuard(FamilyGroupRequest familyGroupRequest, Guardian guardian, Dependent dependent) {
        Guard newGuard = new Guard();
        newGuard.setDependent(dependent);
        newGuard.setGuardian(guardian);
        newGuard.setGuardianRole(familyGroupRequest.getGuardianRole());
        this.guardService.createGuard(newGuard);
    }

    public List<Dependent> getFamilyGroupDependentsByFamilyGroupId(Long familyGroupId){
        return dependentService.findDependentsByFamilyGroupId(familyGroupId);
    }

    public FamilyGroupResponse findByDependentId(Long dependentId) {
        return this.familyGroupMapper.mapFamilyGroupToFamilyGroupResponse(this.findFamilyGroupByDependentId(dependentId));
    }

    public FamilyGroupResponse updateFamilyGroup(Long familyGroupId, FamilyGroupRequest putFamilyGroup) {
        if(!familyGroupRepository.existsById(familyGroupId)){
            throw new EntityNotFoundException(familyGroupId, FamilyGroup.class);
        }

        FamilyGroup familyGroup = this.familyGroupMapper.mapFamilyGroupRequestToFamilyGroup(putFamilyGroup);
        familyGroup.setId(familyGroupId);
        return this.familyGroupMapper.mapFamilyGroupToFamilyGroupResponse(this.familyGroupRepository.save(familyGroup));
    }

    protected FamilyGroup findFamilyGroupByDependentId(Long dependentId) {
        return 
        this.familyGroupRepository
            .findFamilyGroupByDependentsId(dependentId)
            .orElseThrow(
                () -> new EntityNotFoundException(dependentId, Dependent.class)
            );
    }

    protected FamilyGroup addGuardianInFamilyGroup(Guardian guardian, Long familyGroupId) {
        FamilyGroup familyGroup = this.familyGroupRepository.findById(familyGroupId).orElseThrow(
                () -> new EntityNotFoundException(familyGroupId, FamilyGroup.class)
            );
        Set<Guardian> guardians = familyGroup.getGuardians();
        guardians.add(guardian);
        familyGroup.setGuardians(guardians);
        return this.familyGroupRepository.save(familyGroup);
    }
}

package imd.ufrn.familyroutine.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import imd.ufrn.familyroutine.model.Dependent;
import imd.ufrn.familyroutine.model.FamilyGroup;
import imd.ufrn.familyroutine.model.GuardianInFamilyGroup;
import imd.ufrn.familyroutine.model.api.FamilyGroupMapper;
import imd.ufrn.familyroutine.model.api.request.FamilyGroupRequest;
import imd.ufrn.familyroutine.model.api.request.GuardRequest;
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
    private GuardianInFamilyGroupService guardianInFamilyGroupService;
    @Autowired
    private FamilyGroupMapper familyGroupMapper;

    public List<FamilyGroupResponse> findFamilyGroupsByGuardianId(Long guardianId) {
        return
        this.guardianInFamilyGroupService
            .findGuardianInFamilyGroupsByGuardianId(guardianId)
            .stream()
            .map(GuardianInFamilyGroup::getFamilyGroupId)
            .map(this::findFamilyGroupById)
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
        this.getFamilyGroupDependentsByFamilyGroupId(familyGroupId)
            .forEach((familyGroup) -> {
                dependentService.deleteDependentById(familyGroup.getId());
            });
        this.familyGroupRepository.deleteById(familyGroupId);
    }
    
    @Transactional
    public FamilyGroupResponse createFamilyGroup(FamilyGroupRequest familyGroupRequest) {
        FamilyGroup familyGroup = this.familyGroupRepository.save(familyGroupMapper.mapFamilyGroupRequestToFamilyGroup(familyGroupRequest));
        
        GuardianInFamilyGroup guardianInFamilyGroup = new GuardianInFamilyGroup(familyGroupRequest.getGuardianId(), familyGroup.getId());
        this.guardianInFamilyGroupService.createGuardianInFamilyGroup(guardianInFamilyGroup);

        for (Dependent dependent : familyGroupRequest.getDependents()) {
            dependent.setFamilyGroupId(familyGroup.getId());
            dependent.setId(dependentService.createDependent(dependent).getId());
        }

        for (Dependent dependent : familyGroupRequest.getDependents()) {
            GuardRequest newGuard = new GuardRequest();
            newGuard.setDependentId(dependent.getId());
            newGuard.setGuardianId(familyGroupRequest.getGuardianId());
            newGuard.setGuardianRole(familyGroupRequest.getGuardianRole());
            this.guardService.createGuard(newGuard);
        }

        return this.familyGroupMapper.mapFamilyGroupToFamilyGroupResponse(familyGroup);
    }

    public List<Dependent> getFamilyGroupDependentsByFamilyGroupId(Long familyGroupId){
        return familyGroupRepository.findDependentsByFamilyGroupId(familyGroupId);
    }

    public FamilyGroupResponse findByDependentId(Long dependentId) {
        return this.familyGroupMapper
            .mapFamilyGroupToFamilyGroupResponse(
                this.familyGroupRepository
                    .findByDependentId(dependentId)
                    .orElseThrow(
                        () -> new EntityNotFoundException(dependentId, Dependent.class)
                    )
            );
    }
}

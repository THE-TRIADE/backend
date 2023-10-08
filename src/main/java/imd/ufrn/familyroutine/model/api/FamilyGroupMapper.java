package imd.ufrn.familyroutine.model.api;

import java.util.ArrayList;
import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;

import imd.ufrn.familyroutine.model.Dependent;
import imd.ufrn.familyroutine.model.FamilyGroup;
import imd.ufrn.familyroutine.model.Guardian;
import imd.ufrn.familyroutine.model.api.request.FamilyGroupRequest;
import imd.ufrn.familyroutine.model.api.response.FamilyGroupResponse;
import imd.ufrn.familyroutine.service.FamilyGroupService;
import imd.ufrn.familyroutine.service.GuardianService;

@Mapper(componentModel = "spring")
public abstract class FamilyGroupMapper {
    @Lazy
    @Autowired
    private FamilyGroupService familyGroupService;

    @Lazy
    @Autowired GuardianService guardianService;

    public FamilyGroup mapFamilyGroupRequestToFamilyGroup(FamilyGroupRequest familyGroupRequest) {
        List<Guardian> guardians = new ArrayList<>();
        guardians.add(guardianService.findGuardianById(familyGroupRequest.getGuardianId()));
        return this.mapFamilyGroupRequestToFamilyGroup(familyGroupRequest, guardians);
    }

    @Mapping(target = "id", ignore = true)
    protected abstract FamilyGroup mapFamilyGroupRequestToFamilyGroup(FamilyGroupRequest familyGroupRequest, List<Guardian> guardians);

    public FamilyGroupResponse mapFamilyGroupToFamilyGroupResponse(FamilyGroup familygroup) {
        List<Dependent> dependents =  this.familyGroupService.getFamilyGroupDependentsByFamilyGroupId(familygroup.getId());
        return this.mapFamilyGroupToFamilyGroupResponse(familygroup, dependents);
    }

    protected abstract FamilyGroupResponse mapFamilyGroupToFamilyGroupResponse(FamilyGroup familygroup, List<Dependent> dependents);
}

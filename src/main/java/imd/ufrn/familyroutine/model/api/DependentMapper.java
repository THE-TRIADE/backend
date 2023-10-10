package imd.ufrn.familyroutine.model.api;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import imd.ufrn.familyroutine.model.Dependent;
import imd.ufrn.familyroutine.model.FamilyGroup;
import imd.ufrn.familyroutine.model.api.request.DependentRequest;
import imd.ufrn.familyroutine.model.api.response.DependentResponse;

@Mapper(componentModel = "spring")
public abstract class DependentMapper {

    @Mapping(target="id", ignore = true)
    @Mapping(target="name", source= "dependentRequest.name")
    public abstract Dependent mapDependentRequestToDependent(DependentRequest dependentRequest, FamilyGroup familyGroup);

    @Mapping(target = "familyGroupId", source="dependent.familyGroup.id")
    public abstract DependentResponse mapDependentToDependentResponse(Dependent dependent);
}

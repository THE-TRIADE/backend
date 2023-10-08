package imd.ufrn.familyroutine.model.api;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import imd.ufrn.familyroutine.model.api.response.GuardResponse;
import imd.ufrn.familyroutine.model.api.response.GuardToGuardianResponse;

@Mapper(componentModel = "spring", uses = { UtilsMapper.class })
public abstract class GuardToGuardianMapper {

    @Mapping(target= "daysOfWeek", source = "guardResponse.daysOfWeek")
    @Mapping(target= "guardianRole", source = "guardResponse.guardianRole")
    @Mapping(target= "dependentId", source = "guardResponse.dependentId")
    @Mapping(target= "dependentName", source = "guardResponse.dependentName")
    protected abstract GuardToGuardianResponse mapGuardResponseToGuardToGuardianResponse(GuardResponse guardResponse);

}
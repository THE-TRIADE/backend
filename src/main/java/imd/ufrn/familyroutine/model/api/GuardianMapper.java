package imd.ufrn.familyroutine.model.api;

import java.util.List;
import java.util.Set;

import org.mapstruct.Mapper;

import imd.ufrn.familyroutine.model.Guardian;
import imd.ufrn.familyroutine.model.api.response.FamilyGroupResponse;
import imd.ufrn.familyroutine.model.api.response.GuardResponse;
import imd.ufrn.familyroutine.model.api.response.GuardianResponse;
import imd.ufrn.familyroutine.model.api.response.GuardianTokenResponse;

@Mapper(componentModel = "spring", uses = { UtilsMapper.class, GuardToGuardianMapper.class })
public abstract class GuardianMapper {

    public abstract GuardianResponse mapGuardianToGuardianResponse(Guardian guardian, List<GuardResponse> guards, Set<FamilyGroupResponse> familyGroups);

    public abstract GuardianTokenResponse mapGuardianToGuardianTokenResponse(Guardian guardian, String token);
}

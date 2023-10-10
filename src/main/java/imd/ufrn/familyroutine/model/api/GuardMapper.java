package imd.ufrn.familyroutine.model.api;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;

import imd.ufrn.familyroutine.model.Dependent;
import imd.ufrn.familyroutine.model.Guard;
import imd.ufrn.familyroutine.model.Guardian;
import imd.ufrn.familyroutine.model.api.request.GuardRequest;
import imd.ufrn.familyroutine.model.api.response.GuardResponse;
import imd.ufrn.familyroutine.service.DependentService;
import imd.ufrn.familyroutine.service.GuardianService;

@Mapper(componentModel = "spring", uses = { UtilsMapper.class })
public abstract class GuardMapper {
  @Lazy
  @Autowired
  private GuardianService guardianService;
  @Autowired
  private DependentService dependentService;

  public Guard mapGuardRequestToGuard (GuardRequest guardRequest) {
    Guardian guardian = this.guardianService.findGuardianById(guardRequest.getGuardianId());
    Dependent dependent = this.dependentService.findDependentModelById(guardRequest.getDependentId());
    return this.mapGuardRequestToGuard(guardRequest, guardian, dependent);
  }

  public abstract Guard mapGuardRequestToGuard(GuardRequest guardRequest, Guardian guardian, Dependent dependent);

  @Mapping(target = "dependentId", source = "guard.dependent.id")
  @Mapping(target = "dependentName", source = "guard.dependent.name")
  @Mapping(target = "guardianId", source = "guard.guardian.id")
  @Mapping(target = "guardianName", source = "guard.guardian.name")
  @Mapping(target = "guardianEmail", source = "guard.guardian.email")
  public abstract GuardResponse mapGuardToGuardResponse(Guard guard);
}

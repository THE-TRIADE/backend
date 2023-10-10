package imd.ufrn.familyroutine.model.api;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

import imd.ufrn.familyroutine.model.Activity;
import imd.ufrn.familyroutine.model.Dependent;
import imd.ufrn.familyroutine.model.Guardian;
import imd.ufrn.familyroutine.model.Spent;
import imd.ufrn.familyroutine.model.api.request.SpentRequest;
import imd.ufrn.familyroutine.model.api.response.SpentResponse;
import imd.ufrn.familyroutine.service.ActivityService;
import imd.ufrn.familyroutine.service.DependentService;
import imd.ufrn.familyroutine.service.GuardianService;

@Mapper(componentModel = "spring")
public abstract class SpentMapper {
  @Autowired
  private GuardianService guardianService;
  @Autowired
  private DependentService dependentService;
  @Autowired
  private ActivityService activityService;
    
  public Spent mapSpentRequestToSpent(SpentRequest spentRequest) {
    Activity activity = null;
    if(spentRequest.getActivityId() != null) {
       activity = this.activityService.getActivityById(spentRequest.getActivityId());
    }
    Dependent dependent = this.dependentService.findDependentModelById(spentRequest.getDependentId());
    Guardian guardian = this.guardianService.findGuardianById(spentRequest.getGuardianId());
    return this.mapSpentRequestToSpent(spentRequest, activity, dependent, guardian);
  }

  @Mapping(target = "id", ignore = true)
  @Mapping(target = "amount", source = "spentRequest.value")
  @Mapping(target = "name", source = "spentRequest.name")
  @Mapping(target = "activity", source = "activity")
  @Mapping(target = "dependent", source = "dependent")
  protected abstract Spent mapSpentRequestToSpent(SpentRequest spentRequest, Activity activity, Dependent dependent, Guardian guardian);
  
  @Mapping(target = "dependentId", source = "spent.dependent.id")
  @Mapping(target = "dependentName", source = "spent.dependent.name")
  @Mapping(target = "activityId", source = "spent.activity.id")
  @Mapping(target = "activityName", source = "spent.activity.name")
  @Mapping(target = "guardianId", source = "spent.guardian.id")
  @Mapping(target = "guardianName", source = "spent.guardian.name")
  @Mapping(target = "value", source = "spent.amount")
  public abstract SpentResponse mapSpentToSpentResponse(Spent spent);
}

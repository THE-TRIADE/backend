package imd.ufrn.familyroutine.model.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import imd.ufrn.familyroutine.model.Dependent;
import imd.ufrn.familyroutine.model.Guardian;
import imd.ufrn.familyroutine.model.Spent;
import imd.ufrn.familyroutine.model.api.request.SpentRequest;
import imd.ufrn.familyroutine.model.api.response.SpentResponse;
import imd.ufrn.familyroutine.service.ActivityService;
import imd.ufrn.familyroutine.service.DependentService;
import imd.ufrn.familyroutine.service.GuardianService;

@Component
public class SpentMapper {
  @Autowired
  private GuardianService guardianService;
  @Autowired
  private DependentService dependentService;
  @Autowired
  private ActivityService activityService;

  public Spent mapSpentRequestToSpent(SpentRequest spentRequest) {
    Spent spent = new Spent();

    spent.setName(spentRequest.getName());
    spent.setAmount(spentRequest.getValue());
    spent.setPaidOn(spentRequest.getPaidOn());
    spent.setDependent(dependentService.findDependentById(spentRequest.getDependentId()));
    spent.setGuardian(guardianService.findGuardianById(spentRequest.getGuardianId()));

    if (spentRequest.getActivityId() != null) {
      spent.setActivity(activityService.getActivityById(spentRequest.getActivityId()));
    }
    return spent;
  }

  public SpentResponse mapSpentToSpentResponse(Spent spent) {
    SpentResponse spentResponse = new SpentResponse();

    spentResponse.setId(spent.getId());
    spentResponse.setName(spent.getName());
    spentResponse.setValue(spent.getAmount());
    spentResponse.setPaidOn(spent.getPaidOn());

    spentResponse.setDependentId(spent.getDependent().getId());
    Dependent dependent = spent.getDependent();
    spentResponse.setDependentName(dependent.getName());

    spentResponse.setGuardianId(spent.getGuardian().getId());
    Guardian guardian = spent.getGuardian();
    spentResponse.setGuardianName(guardian.getName());

    if (spent.getActivity() != null) {
      spentResponse.setActivityId(spent.getActivity().getId());
      spentResponse.setActivityName(spent.getActivity().getName());
    }

    return spentResponse;
  }
}

package imd.ufrn.familyroutine.model.api;

import java.time.DayOfWeek;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import imd.ufrn.familyroutine.model.*;
import imd.ufrn.familyroutine.model.api.request.GuardRequest;
import imd.ufrn.familyroutine.model.api.response.GuardResponse;
import imd.ufrn.familyroutine.service.DependentService;
import imd.ufrn.familyroutine.service.GuardianService;
import imd.ufrn.familyroutine.service.exception.InvalidDayException;

@Component
public class GuardMapper {
  @Lazy
  @Autowired
  private GuardianService guardianService;
  @Autowired
  private DependentService dependentService;

  public Guard mapGuardRequestToGuard(GuardRequest guardRequest) {
    Guard guard = new Guard();

    if (guardRequest.getDaysOfWeek() != null) {
      List<DayOfWeek> days = guardRequest.getDaysOfWeek()
          .stream()
          .map(intValue -> {
            try {
              return DayOfWeek.of(intValue);
            } catch (Exception e) {
              throw new InvalidDayException();
            }
          })
          .toList();

      guard.setDaysOfWeek(days);
    }

    guard.setGuardianRole(guardRequest.getGuardianRole());
    guard.setDependentId(guardRequest.getDependentId());
    guard.setGuardianId(guardRequest.getGuardianId());

    return guard;
  }

  public GuardResponse mapGuardToGuardResponse(Guard guard) {
    GuardResponse guardResponse = new GuardResponse();

    if (guard.getDaysOfWeek() != null) {
      List<Integer> daysInt = guard.getDaysOfWeek()
          .stream()
          .map(day -> day.getValue())
          .toList();

      guardResponse.setDaysOfWeek(daysInt);
    }
    guardResponse.setGuardianRole(guard.getGuardianRole());

    guardResponse.setDependentId(guard.getDependentId());
    Dependent dependent = this.dependentService.findDependentById(guard.getDependentId());
    guardResponse.setDependentName(dependent.getName());

    guardResponse.setGuardianId(guard.getGuardianId());
    Guardian guardian = this.guardianService.findGuardianById(guard.getGuardianId());
    guardResponse.setGuardianName(guardian.getName());
    guardResponse.setGuardianEmail(guardian.getEmail());

    return guardResponse;
  }
}

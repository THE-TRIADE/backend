package imd.ufrn.familyroutine.model;

import java.time.DayOfWeek;
import java.util.List;

import org.springframework.data.annotation.Id;

public class Guard {
  @Id
  private Long dependentId;
  @Id
  private Long guardianId;
  private List<DayOfWeek> daysOfWeek;
  private GuardianRole guardianRole;

  public Guard() {
  }

  public Guard(Long dependentId, Long guardianId, List<DayOfWeek> daysOfWeek, GuardianRole guardianRole) {
    this.daysOfWeek = daysOfWeek;
    this.guardianRole = guardianRole;
    this.dependentId = dependentId;
    this.guardianId = guardianId;
  }

  public List<DayOfWeek> getDaysOfWeek() {
    return daysOfWeek;
  }

  public void setDaysOfWeek(List<DayOfWeek> daysOfWeek) {
    this.daysOfWeek = daysOfWeek;
  }

  public GuardianRole getGuardianRole() {
    return guardianRole;
  }

  public void setGuardianRole(GuardianRole role) {
    this.guardianRole = role;
  }

  public Long getDependentId() {
    return dependentId;
  }

  public void setDependentId(Long dependentId) {
    this.dependentId = dependentId;
  }

  public Long getGuardianId() {
    return guardianId;
  }

  public void setGuardianId(Long guardianId) {
    this.guardianId = guardianId;
  }
}

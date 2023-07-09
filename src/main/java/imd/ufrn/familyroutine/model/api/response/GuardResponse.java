package imd.ufrn.familyroutine.model.api.response;

import java.util.List;

import imd.ufrn.familyroutine.model.GuardianRole;

public class GuardResponse {
  private List<Integer> daysOfWeek;
  private GuardianRole guardianRole;

  private Long dependentId;
  private String dependentName;

  private Long guardianId;
  private String guardianName;
  private String guardianEmail;

  public GuardResponse() {
  }

  public List<Integer> getDaysOfWeek() {
    return daysOfWeek;
  }

  public void setDaysOfWeek(List<Integer> daysOfWeek) {
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

  public String getDependentName() {
    return dependentName;
  }

  public void setDependentName(String dependentName) {
    this.dependentName = dependentName;
  }

  public String getGuardianName() {
    return guardianName;
  }

  public void setGuardianName(String guardianName) {
    this.guardianName = guardianName;
  }

  public String getGuardianEmail() {
    return guardianEmail;
  }

  public void setGuardianEmail(String guardianEmail) {
    this.guardianEmail = guardianEmail;
  }
}

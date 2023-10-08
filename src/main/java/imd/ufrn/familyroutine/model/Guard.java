package imd.ufrn.familyroutine.model;

import java.time.DayOfWeek;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import imd.ufrn.familyroutine.model.id.GuardId;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
@IdClass(GuardId.class)
public class Guard {

  @Id
  @ManyToOne(fetch = FetchType.EAGER, optional = false)
  @JoinColumn(name = "dependentId")
  @JsonIgnore
  private Dependent dependent;
  
  @Id
  @ManyToOne(fetch = FetchType.EAGER, optional = false)
  @JoinColumn(name = "guardianId")
  @JsonIgnore
  private Guardian guardian;
  
  private List<DayOfWeek> daysOfWeek;
  
  @Column(nullable = false)
  private GuardianRole guardianRole;

  public Guard(Dependent dependent, Guardian guardian, List<DayOfWeek> daysOfWeek, GuardianRole guardianRole) {
    this.dependent = dependent;
    this.guardian = guardian;
    this.daysOfWeek = daysOfWeek;
    this.guardianRole = guardianRole;
  }


  public Guard() {
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


  public Dependent getDependent() {
    return dependent;
  }


  public void setDependent(Dependent dependent) {
    this.dependent = dependent;
  }


  public Guardian getGuardian() {
    return guardian;
  }


  public void setGuardian(Guardian guardian) {
    this.guardian = guardian;
  }
}

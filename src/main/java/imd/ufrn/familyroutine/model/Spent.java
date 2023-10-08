package imd.ufrn.familyroutine.model;

import java.sql.Date;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Spent {
  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE)
  private Long id;

  @Column(nullable = false)
  private String name;

  @Column(nullable = false)
  private Integer amount;

  @Column(nullable = false)
  private Date paidOn;

  @ManyToOne(fetch = FetchType.EAGER, optional = true)
  @JoinColumn(name = "activityId")
  @JsonIgnore
  private Activity activity;

  @ManyToOne(fetch = FetchType.EAGER, optional = true)
  @JoinColumn(name = "dependentId")
  @JsonIgnore
  private Dependent dependent;

  @ManyToOne(fetch = FetchType.EAGER, optional = true)
  @JoinColumn(name = "guardianId")
  @JsonIgnore
  private Guardian guardian;

  public Spent(Long id, String name, Integer amount, Date paidOn, Activity activity, Dependent dependent,
      Guardian guardian) {
    this.id = id;
    this.name = name;
    this.amount = amount;
    this.paidOn = paidOn;
    this.activity = activity;
    this.dependent = dependent;
    this.guardian = guardian;
  }

  public Spent() {
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Integer getAmount() {
    return amount;
  }

  public void setAmount(Integer value) {
    this.amount = value;
  }

  public Date getPaidOn() {
    return paidOn;
  }

  public void setPaidOn(Date paidOn) {
    this.paidOn = paidOn;
  }
  
  public Activity getActivity() {
    return activity;
  }

  public void setActivity(Activity activity) {
    this.activity = activity;
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

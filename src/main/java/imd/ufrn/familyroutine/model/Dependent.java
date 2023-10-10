package imd.ufrn.familyroutine.model;

import java.sql.Date;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;

@Entity
@PrimaryKeyJoinColumn(name = "id")
@Table(name = "dependent")
public class Dependent extends Person {

  @ManyToOne(fetch = FetchType.EAGER, optional = false)
  @JoinColumn(name = "familyGroupId", nullable = false)
  @OnDelete(action = OnDeleteAction.CASCADE)
  @JsonIgnore
  private FamilyGroup familyGroup;

  
  public Dependent(Long id, String name, String cpf, Date birthDate, FamilyGroup familyGroup) {
    super(id, name, cpf, birthDate);
    this.familyGroup = familyGroup;
  }
  public Dependent() {

  }
  public FamilyGroup getFamilyGroup() {
    return familyGroup;
  }
  public void setFamilyGroup(FamilyGroup familyGroup) {
    this.familyGroup = familyGroup;
  }
  @Override
  public int hashCode() {
    final int prime = 31;
    int result = super.hashCode();
    result = prime * result + ((familyGroup == null) ? 0 : familyGroup.hashCode());
    return result;
  }
  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (!super.equals(obj))
      return false;
    if (getClass() != obj.getClass())
      return false;
    Dependent other = (Dependent) obj;
    if (familyGroup == null) {
      if (other.familyGroup != null)
        return false;
    } else if (!familyGroup.equals(other.familyGroup))
      return false;
    return true;
  }

}

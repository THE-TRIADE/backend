package imd.ufrn.familyroutine.model;

import java.sql.Date;
import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;

@Entity
@PrimaryKeyJoinColumn(name = "id")
public class Dependent extends Person {


  private Long familyGroupId;

  public Long getFamilyGroupId() {
    return familyGroupId;
  }

  public void setFamilyGroupId(Long familyGroupId) {
    this.familyGroupId = familyGroupId;
  }

  public Dependent() {

  }

  public Dependent(Long id, String name, String cpf, Date birthDate) {
    super(id, name, cpf, birthDate);
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = super.hashCode();
    result = prime * result + ((familyGroupId == null) ? 0 : familyGroupId.hashCode());
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
    if (familyGroupId == null) {
      if (other.familyGroupId != null)
        return false;
    } else if (!familyGroupId.equals(other.familyGroupId))
      return false;
    return true;
  }
}

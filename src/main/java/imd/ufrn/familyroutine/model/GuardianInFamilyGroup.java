package imd.ufrn.familyroutine.model;

import org.springframework.data.annotation.Id;

public class GuardianInFamilyGroup {
    @Id
    private Long guardianId;
    @Id
    private Long familyGroupId;

    public GuardianInFamilyGroup() {
    }

    public GuardianInFamilyGroup(Long guardianId, Long familyGroupId) {
        this.guardianId = guardianId;
        this.familyGroupId = familyGroupId;
    }

    public Long getGuardianId() {
        return guardianId;
    }

    public void setGuardianId(Long guardianId) {
        this.guardianId = guardianId;
    }

    public Long getFamilyGroupId() {
        return familyGroupId;
    }

    public void setFamilyGroupId(Long familyGroupId) {
        this.familyGroupId = familyGroupId;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((guardianId == null) ? 0 : guardianId.hashCode());
        result = prime * result + ((familyGroupId == null) ? 0 : familyGroupId.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        GuardianInFamilyGroup other = (GuardianInFamilyGroup) obj;
        if (guardianId == null) {
            if (other.guardianId != null)
                return false;
        } else if (!guardianId.equals(other.guardianId))
            return false;
        if (familyGroupId == null) {
            if (other.familyGroupId != null)
                return false;
        } else if (!familyGroupId.equals(other.familyGroupId))
            return false;
        return true;
    }

    

    
}

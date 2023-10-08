package imd.ufrn.familyroutine.model.id;

import imd.ufrn.familyroutine.model.Dependent;
import imd.ufrn.familyroutine.model.Guardian;

public class GuardId{
    private Dependent dependent;
    private Guardian guardian;

    public GuardId(Dependent dependent, Guardian guardian) {
        this.dependent = dependent;
        this.guardian = guardian;
    }

    public GuardId() {
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
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((dependent == null) ? 0 : dependent.hashCode());
        result = prime * result + ((guardian == null) ? 0 : guardian.hashCode());
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
        GuardId other = (GuardId) obj;
        if (dependent == null) {
            if (other.dependent != null)
                return false;
        } else if (!dependent.equals(other.dependent))
            return false;
        if (guardian == null) {
            if (other.guardian != null)
                return false;
        } else if (!guardian.equals(other.guardian))
            return false;
        return true;
    }
    
}

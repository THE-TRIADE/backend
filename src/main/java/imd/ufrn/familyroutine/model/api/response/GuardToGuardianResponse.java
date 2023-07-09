package imd.ufrn.familyroutine.model.api.response;

import java.util.List;

import imd.ufrn.familyroutine.model.GuardianRole;

public class GuardToGuardianResponse {
    private List<Integer> daysOfWeek;
    private GuardianRole guardianRole;
    private Long dependentId;
    private String dependentName;
    
    public List<Integer> getDaysOfWeek() {
        return daysOfWeek;
    }
    public void setDaysOfWeek(List<Integer> daysOfWeek) {
        this.daysOfWeek = daysOfWeek;
    }
    public GuardianRole getGuardianRole() {
        return guardianRole;
    }
    public void setGuardianRole(GuardianRole guardianRole) {
        this.guardianRole = guardianRole;
    }
    public Long getDependentId() {
        return dependentId;
    }
    public void setDependentId(Long dependentId) {
        this.dependentId = dependentId;
    }
    public String getDependentName() {
        return dependentName;
    }
    public void setDependentName(String dependentName) {
        this.dependentName = dependentName;
    }

    
}

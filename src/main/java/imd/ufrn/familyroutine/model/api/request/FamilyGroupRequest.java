package imd.ufrn.familyroutine.model.api.request;

import java.util.List;

import imd.ufrn.familyroutine.model.GuardianRole;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public class FamilyGroupRequest {
    @NotEmpty
    private String name;
    @NotNull
    private GuardianRole guardianRole;
    @NotNull
    private Long guardianId;
    @NotNull
    private List<DependentRequest> dependents;

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    
    public GuardianRole getGuardianRole() {
        return guardianRole;
    }
    public void setGuardianRole(GuardianRole guardianRole) {
        this.guardianRole = guardianRole;
    }
    public Long getGuardianId() {
        return guardianId;
    }
    public void setGuardianId(Long guardianId) {
        this.guardianId = guardianId;
    }
    public List<DependentRequest> getDependents() {
        return dependents;
    }
    public void setDependents(List<DependentRequest> dependents) {
        this.dependents = dependents;
    }

}

package imd.ufrn.familyroutine.model.api.request;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import imd.ufrn.familyroutine.model.ActivityState;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public class ActivityRequest {
    @NotEmpty
    private String name;
    @NotNull
    private LocalDate dateStart;
    @NotNull
    private LocalDate dateEnd;
    @NotNull
    private LocalTime hourStart;
    @NotNull
    private LocalTime hourEnd;
    @NotNull
    private Long dependentId;
    @NotNull
    private Long currentGuardian;
    @NotNull
    private Long actor;
    @NotNull
    private Long createdBy;
    @NotNull
    private Boolean repeat;
    
    private List<Integer> daysToRepeat = new ArrayList<>();
    private LocalDate repeatUntil;
    
    private String description;
    private ActivityState state = ActivityState.CREATED;

    public ActivityRequest() {
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
   
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    
    public LocalDate getDateStart() {
        return dateStart;
    }
    public void setDateStart(LocalDate dateStart) {
        this.dateStart = dateStart;
    }

    public LocalDate getDateEnd() {
        return dateEnd;
    }
    public void setDateEnd(LocalDate dateEnd) {
        this.dateEnd = dateEnd;
    }

    public LocalTime getHourStart() {
        return hourStart;
    }
    public void setHourStart(LocalTime hourStart) {
        this.hourStart = hourStart;
    }

    public LocalTime getHourEnd() {
        return hourEnd;
    }
    public void setHourEnd(LocalTime hourEnd) {
        this.hourEnd = hourEnd;
    }

    public Boolean getRepeat() {
        return repeat;
    }
    public Boolean isRepeat() {
        return repeat;
    }
    public void setRepeat(Boolean repeat) {
        this.repeat = repeat;
    }

    public ActivityState getState() {
        return state;
    }
    public void setState(ActivityState state) {
        this.state = state;
    }
    
    public Long getDependentId() {
        return dependentId;
    }
    public void setDependentId(Long dependentId) {
        this.dependentId = dependentId;
    }
    
    public Long getCurrentGuardian() {
        return currentGuardian;
    }
    public void setCurrentGuardian(Long currentGuardian) {
        this.currentGuardian = currentGuardian;
    }
   
    public Long getActor() {
        return actor;
    }
    public void setActor(Long actor) {
        this.actor = actor;
    }
    
    public Long getCreatedBy() {
        return createdBy;
    }
    public void setCreatedBy(Long createdBy) {
        this.createdBy = createdBy;
    }
    
    public List<Integer> getDaysToRepeat() {
        return daysToRepeat;
    }
    public void setDaysToRepeat(List<Integer> daysToRepeat) {
        this.daysToRepeat = daysToRepeat;
    }
    
    public LocalDate getRepeatUntil() {
        return repeatUntil;
    }
    public void setRepeatUntil(LocalDate repeatUntil) {
        this.repeatUntil = repeatUntil;
    }
}

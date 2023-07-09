package imd.ufrn.familyroutine.model;

import java.sql.Date;
import java.sql.Time;

import org.springframework.data.annotation.Id;

public class Activity {
    @Id
    private Long id;
    private String name;
    private String description;
    private Date dateStart;
    private Date dateEnd;
    private Time hourStart;
    private Time hourEnd;
    private ActivityState state;
    private String commentary;

    private Long dependentId;
    private Long currentGuardian;
    private Long actor;
    private Long createdBy;
    private Long finishedBy;
    private Long groupActivityId;
    
    public Activity() {
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
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public Date getDateStart() {
        return dateStart;
    }
    public void setDateStart(Date dateStart) {
        this.dateStart = dateStart;
    }
    public Date getDateEnd() {
        return dateEnd;
    }
    public void setDateEnd(Date dateEnd) {
        this.dateEnd = dateEnd;
    }
    public Time getHourStart() {
        return hourStart;
    }
    public void setHourStart(Time hourStart) {
        this.hourStart = hourStart;
    }
    public Time getHourEnd() {
        return hourEnd;
    }
    public void setHourEnd(Time hourEnd) {
        this.hourEnd = hourEnd;
    }
    public ActivityState getState() {
        return state;
    }
    public void setState(ActivityState state) {
        this.state = state;
    }
    public String getCommentary() {
        return commentary;
    }
    public void setCommentary(String commentary) {
        this.commentary = commentary;
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
    public Long getFinishedBy() {
        return finishedBy;
    }
    public void setFinishedBy(Long finishedBy) {
        this.finishedBy = finishedBy;
    }
    public Long getGroupActivityId() {
        return groupActivityId;
    }
    public void setGroupActivityId(Long groupActivityId) {
        this.groupActivityId = groupActivityId;
    }
}
package imd.ufrn.familyroutine.model.api.response;

import java.sql.Date;
import java.sql.Time;

import imd.ufrn.familyroutine.model.ActivityState; 

public class ActivityResponse {
    private Long id;
    private String name;
    private ActivityState state;
    private Date dateStart;
    private Date dateEnd;
    private Time hourStart;
    private Time hourEnd;
    
    private Long dependentId;
    private String dependentName;

    private Long currentGuardianId;
    private String currentGuardianEmail;
    private String currentGuardianName;

    private Long actorId;
    private String actorName;

    private Long createdById;
    private String createdByEmail;
    private String createdByName;

    private String commentary;
    private Long finishedById;
    private String finishedByName;

    private Long categoryId;
    private String categoryName;
    private Long groupActivityId;

    public ActivityResponse() {
    }

    public String getName() {
        return name;
    }

    public String getCommentary() {
        return commentary;
    }

    public void setCommentary(String commentary) {
        this.commentary = commentary;
    }

    public Long getFinishedById() {
        return finishedById;
    }

    public void setFinishedById(Long finishedById) {
        this.finishedById = finishedById;
    }

    public String getFinishedByName() {
        return finishedByName;
    }

    public void setFinishedByName(String finishedByName) {
        this.finishedByName = finishedByName;
    }

    public String getDependentName() {
        return dependentName;
    }

    public void setDependentName(String dependentName) {
        this.dependentName = dependentName;
    }

    public void setName(String name) {
        this.name = name;
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

    public Long getDependentId() {
        return dependentId;
    }

    public void setDependentId(Long dependentId) {
        this.dependentId = dependentId;
    }

    public Long getCurrentGuardianId() {
        return currentGuardianId;
    }

    public void setCurrentGuardianId(Long currentGuardianId) {
        this.currentGuardianId = currentGuardianId;
    }

    public String getCurrentGuardianEmail() {
        return currentGuardianEmail;
    }

    public void setCurrentGuardianEmail(String currentGuardianEmail) {
        this.currentGuardianEmail = currentGuardianEmail;
    }

    public String getCurrentGuardianName() {
        return currentGuardianName;
    }

    public void setCurrentGuardianName(String currentGuardianName) {
        this.currentGuardianName = currentGuardianName;
    }

    public Long getActorId() {
        return actorId;
    }

    public void setActorId(Long actorId) {
        this.actorId = actorId;
    }

    public String getActorName() {
        return actorName;
    }

    public void setActorName(String actorName) {
        this.actorName = actorName;
    }

    public Long getCreatedById() {
        return createdById;
    }

    public void setCreatedById(Long createdById) {
        this.createdById = createdById;
    }

    public String getCreatedByEmail() {
        return createdByEmail;
    }

    public void setCreatedByEmail(String createdByEmail) {
        this.createdByEmail = createdByEmail;
    }

    public String getCreatedByName() {
        return createdByName;
    }

    public void setCreatedByName(String createdByName) {
        this.createdByName = createdByName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ActivityState getState() {
        return state;
    }

    public void setState(ActivityState state) {
        this.state = state;
    }

    public Long getGroupActivityId() {
        return groupActivityId;
    }

    public void setGroupActivityId(Long groupActivityId) {
        this.groupActivityId = groupActivityId;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }
    
}

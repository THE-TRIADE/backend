package imd.ufrn.familyroutine.model;

import java.sql.Date;
import java.sql.Time;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "activity")
public class Activity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(nullable = false)
    private String name;

    private String description;

    @Column(nullable = false)
    private Date dateStart;

    @Column(nullable = false)
    private Date dateEnd;

    @Column(nullable = false)
    private Time hourStart;
    
    @Column(nullable = false)
    private Time hourEnd;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private ActivityState state;

    private String commentary;
    
    @ManyToOne(fetch = FetchType.EAGER, optional = true)
    @JoinColumn(name = "dependentId")
    @JsonIgnore
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Dependent dependent;

    @ManyToOne(fetch = FetchType.EAGER, optional = true)
    @JoinColumn(name = "currentGuardianId")
    @JsonIgnore
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Guardian currentGuardian;
    
    @ManyToOne(fetch = FetchType.EAGER, optional = true)
    @JoinColumn(name = "actorId")
    @JsonIgnore
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Person actor;
    
    @ManyToOne(fetch = FetchType.EAGER, optional = true)
    @JoinColumn(name = "createdById")
    @JsonIgnore
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Guardian createdBy;

    @ManyToOne(fetch = FetchType.EAGER, optional = true)
    @JoinColumn(name = "finishedById")
    @JsonIgnore
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Guardian finishedBy;

    @ManyToOne(fetch = FetchType.EAGER, optional = true)
    @JoinColumn(name = "groupActivityId")
    @JsonIgnore
    @OnDelete(action = OnDeleteAction.CASCADE)
    private GroupActivity groupActivity;
    
    public Activity(Long id, String name, String description, Date dateStart, Date dateEnd, Time hourStart,
            Time hourEnd, ActivityState state, String commentary, Dependent dependent, Guardian currentGuardian,
            Person actor, Guardian createdBy, Guardian finishedBy, GroupActivity groupActivity) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.dateStart = dateStart;
        this.dateEnd = dateEnd;
        this.hourStart = hourStart;
        this.hourEnd = hourEnd;
        this.state = state;
        this.commentary = commentary;
        this.dependent = dependent;
        this.currentGuardian = currentGuardian;
        this.actor = actor;
        this.createdBy = createdBy;
        this.finishedBy = finishedBy;
        this.groupActivity = groupActivity;
    }

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

    public Dependent getDependent() {
        return dependent;
    }

    public void setDependent(Dependent dependent) {
        this.dependent = dependent;
    }

    public GroupActivity getGroupActivity() {
        return groupActivity;
    }

    public void setGroupActivity(GroupActivity groupActivity) {
        this.groupActivity = groupActivity;
    }

    public Guardian getCurrentGuardian() {
        return currentGuardian;
    }

    public void setCurrentGuardian(Guardian currentGuardian) {
        this.currentGuardian = currentGuardian;
    }

    public Person getActor() {
        return actor;
    }

    public void setActor(Person actor) {
        this.actor = actor;
    }

    public Guardian getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Guardian createdBy) {
        this.createdBy = createdBy;
    }

    public Guardian getFinishedBy() {
        return finishedBy;
    }

    public void setFinishedBy(Guardian finishedBy) {
        this.finishedBy = finishedBy;
    }
}
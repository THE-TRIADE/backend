package imd.ufrn.familyroutine.model.api;

import java.sql.Date;
import java.sql.Time;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import imd.ufrn.familyroutine.model.Activity;
import imd.ufrn.familyroutine.model.Guardian;
import imd.ufrn.familyroutine.model.Person;
import imd.ufrn.familyroutine.model.api.request.ActivityRequest;
import imd.ufrn.familyroutine.model.api.response.ActivityResponse;
import imd.ufrn.familyroutine.service.GuardianService;
import imd.ufrn.familyroutine.service.PersonService;

@Component
public class ActivityMapper {

    @Autowired
    private GuardianService guardianService;
    @Autowired
    private PersonService personService;

    public Activity mapActivityRequestToActivity(ActivityRequest activityRequest) {
        Activity activity = new Activity();
        activity.setName(activityRequest.getName());
        activity.setDateStart(Date.valueOf(activityRequest.getDateStart()));
        activity.setDateEnd(Date.valueOf(activityRequest.getDateEnd()));
        activity.setHourStart(Time.valueOf(activityRequest.getHourStart()));
        activity.setHourEnd(Time.valueOf(activityRequest.getHourEnd()));
        activity.setDependentId(activityRequest.getDependentId());
        activity.setCurrentGuardian(activityRequest.getCurrentGuardian());
        activity.setActor(activityRequest.getActor());
        activity.setCreatedBy(activityRequest.getCreatedBy());
        activity.setState(activityRequest.getState());
        return activity;
    }

    public ActivityResponse mapActivityToActivityResponse(Activity activity) {
        ActivityResponse activityResponse = new ActivityResponse();
        activityResponse.setId(activity.getId());
        activityResponse.setName(activity.getName());
        activityResponse.setState(activity.getState());
        activityResponse.setDateStart(activity.getDateStart());
        activityResponse.setDateEnd(activity.getDateEnd());
        activityResponse.setHourStart(activity.getHourStart());
        activityResponse.setHourEnd(activity.getHourEnd());
        activityResponse.setDependentId(activity.getDependentId());
        Person dependent = this.personService.findPersonById(activity.getDependentId());
        activityResponse.setDependentName(dependent.getName());

        activityResponse.setCurrentGuardianId(activity.getCurrentGuardian());
        Guardian currentGuardian = this.guardianService.findGuardianById(activity.getCurrentGuardian());
        activityResponse.setCurrentGuardianEmail(currentGuardian.getEmail());
        activityResponse.setCurrentGuardianName(currentGuardian.getName());
        
        activityResponse.setActorId(activity.getActor());
        Person actor = this.personService.findPersonById(activity.getActor());
        activityResponse.setActorName(actor.getName());

        activityResponse.setCreatedById(activity.getCreatedBy());
        Guardian createdByGuardian = this.guardianService.findGuardianById(activity.getCreatedBy());
        activityResponse.setCreatedByEmail(createdByGuardian.getEmail());
        activityResponse.setCreatedByName(createdByGuardian.getName());
        activityResponse.setGroupActivityId(activity.getGroupActivityId());

        if(activity.getCommentary() != null) {
            activityResponse.setCommentary(activity.getCommentary());
        }
        if(activity.getFinishedBy() != null) {
            activityResponse.setFinishedById(activity.getFinishedBy());
        }

        return activityResponse;
    }
}

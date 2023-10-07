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
import imd.ufrn.familyroutine.service.DependentService;
import imd.ufrn.familyroutine.service.GuardianService;
import imd.ufrn.familyroutine.service.PersonService;

@Component
public class ActivityMapper {

    @Autowired
    private GuardianService guardianService;
    @Autowired
    private DependentService dependentService;
    @Autowired
    private PersonService personService;

    public Activity mapActivityRequestToActivity(ActivityRequest activityRequest) {
        Activity activity = new Activity();
        activity.setName(activityRequest.getName());
        activity.setDateStart(Date.valueOf(activityRequest.getDateStart()));
        activity.setDateEnd(Date.valueOf(activityRequest.getDateEnd()));
        activity.setHourStart(Time.valueOf(activityRequest.getHourStart()));
        activity.setHourEnd(Time.valueOf(activityRequest.getHourEnd()));
        activity.setDependent(dependentService.findDependentById(activityRequest.getDependentId()));
        activity.setCurrentGuardian(guardianService.findGuardianById(activityRequest.getCurrentGuardian()));
        activity.setActor(personService.findPersonById(activityRequest.getActor()));
        activity.setCreatedBy(guardianService.findGuardianById(activityRequest.getCreatedBy()));
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
        activityResponse.setDependentId(activity.getDependent().getId());
        Person dependent = this.personService.findPersonById(activity.getDependent().getId());
        activityResponse.setDependentName(dependent.getName());

        activityResponse.setCurrentGuardianId(activity.getCurrentGuardian().getId());
        Guardian currentGuardian = this.guardianService.findGuardianById(activity.getCurrentGuardian().getId());
        activityResponse.setCurrentGuardianEmail(currentGuardian.getEmail());
        activityResponse.setCurrentGuardianName(currentGuardian.getName());
        
        activityResponse.setActorId(activity.getActor().getId());
        Person actor = this.personService.findPersonById(activity.getActor().getId());
        activityResponse.setActorName(actor.getName());

        activityResponse.setCreatedById(activity.getCreatedBy().getId());
        Guardian createdByGuardian = this.guardianService.findGuardianById(activity.getCreatedBy().getId());
        activityResponse.setCreatedByEmail(createdByGuardian.getEmail());
        activityResponse.setCreatedByName(createdByGuardian.getName());
        activityResponse.setGroupActivityId(activity.getGroupActivity().getId());

        if(activity.getCommentary() != null) {
            activityResponse.setCommentary(activity.getCommentary());
        }
        if(activity.getFinishedBy() != null) {
            activityResponse.setFinishedById(activity.getFinishedBy().getId());
        }

        return activityResponse;
    }
}

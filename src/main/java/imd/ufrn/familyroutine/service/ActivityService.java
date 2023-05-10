package imd.ufrn.familyroutine.service;

import java.sql.Date;
import java.sql.Time;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import imd.ufrn.familyroutine.model.Activity;
import imd.ufrn.familyroutine.model.ActivityState;
import imd.ufrn.familyroutine.model.RecurringActivity;
import imd.ufrn.familyroutine.model.api.ActivityMapper;
import imd.ufrn.familyroutine.model.api.request.ActivityRequest;
import imd.ufrn.familyroutine.model.api.request.FinishActivityRequest;
import imd.ufrn.familyroutine.model.api.response.ActivityResponse;
import imd.ufrn.familyroutine.repository.ActivityRepository;
import imd.ufrn.familyroutine.service.exception.EntityNotFoundException;
import imd.ufrn.familyroutine.service.exception.InvalidStateException;
import imd.ufrn.familyroutine.service.exception.RecurringActivityException;
import imd.ufrn.familyroutine.service.exception.RecurringActivityType;

@Service
public class ActivityService {
	@Autowired
    private ActivityRepository activityRepository;
    @Autowired
    private RecurringActivityService recurringActivityService;
    @Autowired
    private ValidationService validationService;
    @Autowired
    private ActivityMapper activityMapper;

    public List<ActivityResponse> findAll() {
        return this.activityRepository
                .findAll()
                .stream()
                .map(this.activityMapper::mapActivityToActivityResponse)
                .toList();
    }

    public ActivityResponse findActivityById(Long activityId) {
        return this.activityMapper.mapActivityToActivityResponse(this.getActivityById(activityId));
    }
    
    public ActivityResponse handleActivityRequest(ActivityRequest activityRequest) {
        if(activityRequest.isRepeat()) {
            return this.activityMapper.mapActivityToActivityResponse(this.createRecurringActivities(activityRequest));
        }
        else {
            Activity newActivity = this.activityMapper.mapActivityRequestToActivity(activityRequest);
            return this.activityMapper.mapActivityToActivityResponse(this.createActivity(newActivity));
        }
    }

    public ActivityResponse finishActivity(Long activityId, FinishActivityRequest finishActivityRequest) {
        Activity activity = this.getActivityById(activityId);
        this.checkActivityInDoneOrNotDoneStateOrError(activity);
        activity.setState(ActivityState.DONE);
        activity.setFinishedBy(finishActivityRequest.getGuardianId());
        activity.setCommentary(finishActivityRequest.getCommentary());
        return this.activityMapper.mapActivityToActivityResponse(this.updateActivity(activity));
    }

    public void deleteAllActivities() {
        this.activityRepository.deleteAll();
	}

    public void deleteActivityById(Long activityId) {
        this.activityRepository.deleteById(activityId);
    }

    protected void checkActivityInDoneOrNotDoneStateOrError(Activity activity) {
        if (activity.getState() == ActivityState.DONE || activity.getState() == ActivityState.NOT_DONE) {
            throw new InvalidStateException(activity, List.of(ActivityState.CREATED, ActivityState.IN_PROGRESS, ActivityState.LATE));
        }
    }

    protected List<Activity> createMultipleActivities(List<Activity> newActivities) {
        return newActivities.stream().map(this::createActivity).toList();
    }

    protected Activity createActivity(Activity newActivity) {
        this.validationService.validActivityOrError(newActivity);
        return this.activityRepository.save(newActivity);
    }

    private Activity updateActivity(Activity activity) {
        return this.activityRepository.update(activity);
    }

    private Activity getActivityById(Long activityId) {
        return this.activityRepository.findById(activityId).orElseThrow(() -> new EntityNotFoundException(activityId, Activity.class));
    }

    @Transactional
	private Activity createRecurringActivities(ActivityRequest activityRequest) {
        this.checkRecurringActivitiesFieldsThroughActivityRequest(activityRequest);
        
        List<Activity> activities = new ArrayList<>();
        activities.add(this.activityMapper.mapActivityRequestToActivity(activityRequest));

        // Getting first activity time diff in hours
        LocalDateTime startDateTime = LocalDateTime.of(activityRequest.getDateStart(), activityRequest.getHourStart());
        LocalDateTime endDateTime = LocalDateTime.of(activityRequest.getDateEnd(), activityRequest.getHourEnd());
        Long diffInHours = ChronoUnit.HOURS.between(startDateTime, endDateTime);

        // Setting up loop variables
        Iterator<Integer> daysIterator = activityRequest.getDaysToRepeat().iterator();
        LocalDateTime currentDate = startDateTime;
        LocalDateTime limitDate = LocalDateTime.of(activityRequest.getRepeatUntil(), activityRequest.getHourStart());

        while (currentDate.isBefore(limitDate)) {
            currentDate = findNextDate(currentDate, DayOfWeek.of(daysIterator.next()));

            if(currentDate.isAfter(limitDate)) {
                break;
            }

            Activity newActivity = new Activity();
            newActivity.setName(activityRequest.getName());
            newActivity.setDateStart(Date.valueOf(currentDate.toLocalDate()));
            newActivity.setDateEnd(Date.valueOf(currentDate.plusHours(diffInHours).toLocalDate()));
            newActivity.setHourStart(Time.valueOf(activityRequest.getHourStart()));
            newActivity.setHourEnd(Time.valueOf(activityRequest.getHourEnd()));
            newActivity.setDependentId(activityRequest.getDependentId());
            newActivity.setCurrentGuardian(activityRequest.getCurrentGuardian());
            newActivity.setActor(activityRequest.getActor());
            newActivity.setCreatedBy(activityRequest.getCreatedBy());
            newActivity.setState(activityRequest.getState());

            activities.add(newActivity);

            if(!daysIterator.hasNext()) {
                daysIterator = activityRequest.getDaysToRepeat().iterator();
            }
        }

        RecurringActivity recurringActivity = this.recurringActivityService.createRecurringActivity(new RecurringActivity());
        activities.forEach(activity -> activity.setRecurringActivityId(recurringActivity.getId()));

        return this.createMultipleActivities(activities).get(0);
	}

    /* UTILS */
    private LocalDateTime findNextDate(LocalDateTime currentDate, DayOfWeek dayOfWeek) {
        LocalDateTime nextDate = currentDate;

        if (currentDate.getDayOfWeek() == dayOfWeek) {
            nextDate = currentDate.plusDays(1);
        }
        while (nextDate.getDayOfWeek() != dayOfWeek) {
            nextDate = nextDate.plusDays(1);
        }

        return nextDate;
    }

    private void checkRecurringActivitiesFieldsThroughActivityRequest(ActivityRequest activityRequest) {
        if (activityRequest.getDaysToRepeat().isEmpty() || activityRequest.getRepeatUntil() == null) {
            throw new RecurringActivityException(RecurringActivityType.FIELD);
        }

        LocalDate startDate = activityRequest.getDateStart();
        LocalDate endDate = activityRequest.getRepeatUntil();

        if (startDate.isAfter(endDate)) {
            throw new RecurringActivityException(RecurringActivityType.INTERVAL);
        }

        // Check if there is no days lesser than 1 and greater than 7. Valid interval: [1,7]. 1 is Monday and 7 is Sunday.
        Long daysLesserThan1 = activityRequest.getDaysToRepeat()
            .stream()
            .filter(day -> day < 1)
            .count();

        Long daysGreaterThan7 = activityRequest.getDaysToRepeat()
            .stream()
            .filter(day -> day > 7)
            .count();

        if (daysLesserThan1 > 0 || daysGreaterThan7 > 0) {
            throw new RecurringActivityException(RecurringActivityType.DAY_INDEX);
        }
    }
}

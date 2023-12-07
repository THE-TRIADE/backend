package imd.ufrn.familyroutine.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import imd.ufrn.familyroutine.model.api.request.ActivityRequest;
import imd.ufrn.familyroutine.model.api.request.FinishActivityRequest;
import imd.ufrn.familyroutine.model.api.response.ActivityResponse;
import imd.ufrn.familyroutine.service.ActivityService;
import jakarta.validation.Valid;

@RestController
@CrossOrigin
@RequestMapping("/activity")
public class ActivityController {
    @Autowired
    private ActivityService activityService;
    
    @GetMapping
    public List<ActivityResponse> getAllActivities(@RequestParam Optional<Long> dependentId, @RequestParam Optional<Long> categoryId) {
        if(dependentId.isPresent()) {
            return this.activityService.findByDependentId(dependentId.get());
        }
        else if(categoryId.isPresent()) {
            return this.activityService.findByCategoryId(categoryId.get());
        }
        return this.activityService.findAll();
    }

    @GetMapping("{activityId}")
    public ActivityResponse findActivityById(@PathVariable Long activityId) {
        return this.activityService.findActivityById(activityId);
    }

    @PostMapping
    public ActivityResponse createActivity(@RequestBody @Valid ActivityRequest activity) {
        return this.activityService.handleActivityRequest(activity);
    }

    @PatchMapping("{activityId}/finish")
    public ActivityResponse createActivity(@PathVariable Long activityId, @RequestBody @Valid FinishActivityRequest finishActivityRequest) {
        return this.activityService.finishActivity(activityId, finishActivityRequest);
    }

    @DeleteMapping
    public void deleteActivity() {
        this.activityService.deleteAllActivities();
    }

    @DeleteMapping("{activityId}")
    public void deleteActivityById(@PathVariable Long activityId) {
        this.activityService.deleteActivityById(activityId);
    } 
    
    @PutMapping("{activityId}")
    public ActivityResponse updateActivity(@PathVariable Long activityId, @RequestBody ActivityRequest updateActivity) {
        return activityService.updateActivity(activityId, updateActivity);
    }
}

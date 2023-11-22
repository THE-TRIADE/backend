package imd.ufrn.familyroutine.model.api;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

import imd.ufrn.familyroutine.model.Activity;
import imd.ufrn.familyroutine.model.Category;
import imd.ufrn.familyroutine.model.Dependent;
import imd.ufrn.familyroutine.model.Guardian;
import imd.ufrn.familyroutine.model.Person;
import imd.ufrn.familyroutine.model.api.request.ActivityRequest;
import imd.ufrn.familyroutine.model.api.response.ActivityResponse;
import imd.ufrn.familyroutine.service.CategoryService;
import imd.ufrn.familyroutine.service.DependentService;
import imd.ufrn.familyroutine.service.GuardianService;
import imd.ufrn.familyroutine.service.PersonService;

@Mapper(componentModel = "spring", uses = { UtilsMapper.class })
public abstract class ActivityMapper {
    @Autowired
    private GuardianService guardianService;
    @Autowired
    private DependentService dependentService;
    @Autowired
    private PersonService personService;
    @Autowired
    private CategoryService categoryService;

    public Activity mapActivityRequestToActivity(ActivityRequest activityRequest) {
        Guardian createdBy = this.guardianService.findGuardianById(activityRequest.getCreatedBy());
        Guardian currentGuardian = this.guardianService.findGuardianById(activityRequest.getCurrentGuardian());
        Person actor = this.personService.findPersonById(activityRequest.getActor());
        Dependent dependent = this.dependentService.findDependentModelById(activityRequest.getDependentId());
        Category category = null;
        if(activityRequest.getCategory() != null) {
            category = this.categoryService.findCategoryById(activityRequest.getCategory());
        }
        return this.mapActivityRequestToActivity(activityRequest, createdBy, currentGuardian, actor, dependent, category);
    }

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "commentary", ignore = true)
    @Mapping(target = "finishedBy", ignore = true)
    @Mapping(target = "groupActivity", ignore = true)
    @Mapping(target = "name", source = "activityRequest.name")
    @Mapping(target = "createdBy", source = "createdBy")
    @Mapping(target = "currentGuardian", source = "currentGuardian")
    @Mapping(target = "actor", source = "actor")
    @Mapping(target = "category", source = "category")
    protected abstract Activity mapActivityRequestToActivity(ActivityRequest activityRequest, Guardian createdBy, Guardian currentGuardian, Person actor, Dependent dependent, Category category);

    @Mapping(target = "id", source = "activity.id")
    @Mapping(target = "groupActivityId", source = "activity.groupActivity.id")
    @Mapping(target = "name", source = "activity.name")
    @Mapping(target = "actorId", source = "activity.actor.id")
    @Mapping(target = "actorName", source = "activity.actor.name")
    @Mapping(target = "dependentId", source = "activity.dependent.id")
    @Mapping(target = "dependentName", source = "activity.dependent.name")
    @Mapping(target = "finishedById", source = "activity.finishedBy.id")
    @Mapping(target = "finishedByName", source = "activity.finishedBy.name")
    @Mapping(target = "createdById", source = "activity.createdBy.id")
    @Mapping(target = "createdByName", source = "activity.createdBy.name")
    @Mapping(target = "createdByEmail", source = "activity.createdBy.email")
    @Mapping(target = "currentGuardianId", source = "activity.currentGuardian.id")
    @Mapping(target = "currentGuardianName", source = "activity.currentGuardian.name")
    @Mapping(target = "currentGuardianEmail", source = "activity.currentGuardian.email")
    @Mapping(target = "categoryId", source = "activity.category.id")
    @Mapping(target = "categoryName", source = "activity.category.name")
    public abstract ActivityResponse mapActivityToActivityResponse(Activity activity);
}

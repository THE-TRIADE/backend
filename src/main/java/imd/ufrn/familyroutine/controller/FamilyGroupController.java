package imd.ufrn.familyroutine.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import imd.ufrn.familyroutine.model.api.request.FamilyGroupRequest;
import imd.ufrn.familyroutine.model.api.response.FamilyGroupResponse;
import imd.ufrn.familyroutine.service.FamilyGroupService;

@CrossOrigin
@RestController
@RequestMapping("familyGroup")
public class FamilyGroupController {
    @Autowired
    private FamilyGroupService familyGroupService;
    
    @GetMapping
    public List<FamilyGroupResponse> getFamilyGroupsByGuardianId(@RequestParam Long guardianId) {
        return this.familyGroupService.findFamilyGroupsByGuardianId(guardianId);
    }

    @GetMapping("{familyGroupId}")
    public FamilyGroupResponse findFamilyGroupById(@PathVariable Long familyGroupId) {
        return this.familyGroupService.findFamilyGroupById(familyGroupId);
    }

    @PostMapping
    public FamilyGroupResponse createFamilyGroup(@RequestBody FamilyGroupRequest newFamilyGroup) {
        return this.familyGroupService.createFamilyGroupWithGuardianAndDependents(newFamilyGroup);
    }

    @DeleteMapping("{familyGroupId}")
    public void deleteFamilyGroupById(@PathVariable Long familyGroupId) {
        this.familyGroupService.deleteFamilyGroupById(familyGroupId);
    } 
    
    @PutMapping("/{familyGroupId}")
    public FamilyGroupResponse updatefamilyGroup(@PathVariable("familyGroupId") Long familyGroupId, @RequestBody FamilyGroupRequest updatefamilyGroup) {
        return familyGroupService.updateFamilyGroup(familyGroupId, updatefamilyGroup);
    }
}

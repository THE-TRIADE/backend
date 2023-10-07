package imd.ufrn.familyroutine.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import imd.ufrn.familyroutine.model.Dependent;
import imd.ufrn.familyroutine.service.DependentService;

@CrossOrigin
@RestController
@RequestMapping("/dependent")
public class DependentController {
    @Autowired
    private DependentService dependentService;

    @GetMapping
    public List<Dependent> getAllDependents() {
        return this.dependentService.findAll();
    }

    @GetMapping("{dependentId}")
    public Dependent findDependentById(@PathVariable Long dependentId) {
        return this.dependentService.findDependentById(dependentId);
    }

    @PostMapping
    public Dependent createDependent(@RequestBody Dependent newDependent) {
        return this.dependentService.createDependent(newDependent);
    }

    @DeleteMapping
    public void deleteAllDependents() {
        this.dependentService.deleteAllDependents();
    }

    @DeleteMapping("{dependentId}")
    public void deleteDependentById(@PathVariable Long dependentId) {
        this.dependentService.deleteDependentById(dependentId);
    } 
}

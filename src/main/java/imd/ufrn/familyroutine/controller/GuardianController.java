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

import imd.ufrn.familyroutine.model.Guardian;
import imd.ufrn.familyroutine.service.GuardianService;

@CrossOrigin
@RestController
@RequestMapping("/guardian")
public class GuardianController {
    @Autowired
    private GuardianService guardianService;

    @GetMapping
    public List<Guardian> getAllGuardians() {
        return this.guardianService.findAll();
    }

    @GetMapping("{guardianId}")
    public Guardian findGuardianById(@PathVariable Long guardianId) {
        return this.guardianService.findGuardianById(guardianId);
    }

    @PostMapping
    public Guardian createGuardian(@RequestBody Guardian newGuardian) {
        return this.guardianService.createGuardian(newGuardian);
    }

    @DeleteMapping
    public void deleteAllGuardians() {
        this.guardianService.deleteAllGuardians();
    }

    @DeleteMapping("{guardianId}")
    public void deleteGuardianById(@PathVariable Long guardianId) {
        this.guardianService.deleteGuardianById(guardianId);
    } 
}

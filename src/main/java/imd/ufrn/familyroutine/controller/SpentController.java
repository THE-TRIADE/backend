package imd.ufrn.familyroutine.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import imd.ufrn.familyroutine.model.api.request.SpentRequest;
import imd.ufrn.familyroutine.model.api.response.SpentResponse;
import imd.ufrn.familyroutine.service.SpentService;

@CrossOrigin
@RestController
@RequestMapping("/spent")
public class SpentController {
  @Autowired
  private SpentService spentService;

  @GetMapping
  public List<SpentResponse> findAllSpents() {
    return this.spentService.findAllSpents();
  }

  @GetMapping("/by-id/{spentId}")
  public SpentResponse findSpentById(@PathVariable Long spentId) {
    return this.spentService.findSpentById(spentId);
  }

  @GetMapping("/by-dependent-id/{dependentId}")
  public List<SpentResponse> findSpentsByDependentId(@PathVariable Long dependentId) {
    return this.spentService.findSpentsByDependentId(dependentId);
  }

  @GetMapping("/by-guardian-id/{guardianId}")
  public List<SpentResponse> findSpentsByGuardianId(@PathVariable Long guardianId) {
    return this.spentService.findSpentsByGuardianId(guardianId);
  }

  @PostMapping
  public SpentResponse createSpent(@RequestBody SpentRequest newSpent) {
    return this.spentService.createSpent(newSpent);
  }

  @DeleteMapping
  public void deleteAllSpents() {
    this.spentService.deleteAllSpents();
  }

  @DeleteMapping("{spentId}")
  public void deleteSpentById(@PathVariable Long spentId) {
    this.spentService.deleteSpentById(spentId);
  }

  @PutMapping("/{spentId}")
  public SpentResponse updateSpent(@PathVariable("spentId") Long spentId, @RequestBody SpentRequest updateSpent) {
    return spentService.updateSpent(spentId, updateSpent);
  }
}

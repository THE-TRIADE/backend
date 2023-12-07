package imd.ufrn.familyroutine.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import imd.ufrn.familyroutine.model.api.request.GuardRequest;
import imd.ufrn.familyroutine.model.api.response.GuardResponse;
import imd.ufrn.familyroutine.service.GuardService;

@CrossOrigin
@RestController
@RequestMapping("/guard")
public class GuardController {
  @Autowired
  private GuardService guardService;

  @GetMapping("/by-dependent-id/{dependentId}")
  public List<GuardResponse> findGuardsByDependentId(@PathVariable Long dependentId) {
    return this.guardService.findGuardsByDependentId(dependentId);
  }

  @GetMapping("/by-guardian-id/{guardianId}")
  public List<GuardResponse> findGuardsByGuardianId(@PathVariable Long guardianId) {
    return this.guardService.findGuardsByGuardianId(guardianId);
  }

  @GetMapping("/by-guardian-id-and-dependent-id/{guardianId}/{dependentId}")
  public GuardResponse findGuardByGuardianIdAndDependentId(@PathVariable Long guardianId, @PathVariable Long dependentId) {
    return this.guardService.findGuardByGuardianIdAndDependentId(guardianId, dependentId);
  }

  @PostMapping
  public GuardResponse createGuard(@RequestBody GuardRequest newGuard) {
    return this.guardService.createGuard(newGuard);
  }

  @DeleteMapping
  public void deleteAllGuards(@RequestParam Optional<Long> guardianId, @RequestParam Optional<Long> dependentId) {
    if(guardianId.isPresent() && dependentId.isPresent()) {
        this.guardService.deleteGuardsByGuardianIdAndDependentId(guardianId.get(), dependentId.get());
    }
    else if(guardianId.isPresent() && dependentId.isEmpty()) {
        throw new RuntimeException("If 'guardianId' is present, 'dependentId' must be present too.");
    }
    else if(dependentId.isPresent() && guardianId.isEmpty()) {
        throw new RuntimeException("If 'dependentId' is present, 'guardianId' must be present too.");
    }
    else {
        this.guardService.deleteAllGuards();
    }
  }
}

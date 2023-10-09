package imd.ufrn.familyroutine.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import imd.ufrn.familyroutine.model.FamilyGroup;
import imd.ufrn.familyroutine.model.Guard;
import imd.ufrn.familyroutine.model.Guardian;
import imd.ufrn.familyroutine.model.api.GuardMapper;
import imd.ufrn.familyroutine.model.api.UtilsMapper;
import imd.ufrn.familyroutine.model.api.request.GuardRequest;
import imd.ufrn.familyroutine.model.api.response.FamilyGroupResponse;
import imd.ufrn.familyroutine.model.api.response.GuardResponse;
import imd.ufrn.familyroutine.repository.GuardRepository;
import jakarta.transaction.Transactional;

@Service
public class GuardService {
  @Autowired
  private GuardRepository guardRepository;
  @Autowired
  private GuardMapper guardMapper;
  @Autowired
  private ValidationService validationService;
  @Autowired
  private UtilsMapper utilsMapper;
  @Autowired
  private FamilyGroupService familyGroupService;
  @Autowired
  private GuardianService guardianService;

  public List<GuardResponse> findGuardsByGuardianId(Long guardianId) {
    return this.guardRepository.findByGuardianId(guardianId).stream().map(guardMapper::mapGuardToGuardResponse)
        .toList();
  }

  public List<GuardResponse> findGuardsByDependentId(Long dependentId) {
    return this.guardRepository.findByDependentId(dependentId).stream().map(guardMapper::mapGuardToGuardResponse)
        .toList();
  }

  @Transactional
  public GuardResponse createGuard(GuardRequest newGuard) {
    this.validationService.validDaysOfWeekOrError(newGuard.getDaysOfWeek());
    FamilyGroup familyGroup = this.familyGroupService.findFamilyGroupByDependentId(newGuard.getDependentId());
    Guardian guardGuardian = familyGroup.getGuardians()
        .stream().filter(guardian -> guardian.getId().equals(newGuard.getGuardianId()))
        .findFirst().orElse(null);
    if(guardGuardian == null) {
        guardGuardian = this.guardianService.findGuardianById(newGuard.getGuardianId());
        this.familyGroupService.addGuardianInFamilyGroup(guardGuardian, familyGroup.getId());
    }
    Guard guard = this.guardMapper.mapGuardRequestToGuard(newGuard);
    return this.guardMapper.mapGuardToGuardResponse(this.guardRepository.save(guard));
  }

  public GuardResponse createGuard(Guard newGuard) {
    this.validationService.validDaysOfWeekOrError(utilsMapper.listDayOfWeekToListInteger(newGuard.getDaysOfWeek()));
    
    return this.guardMapper.mapGuardToGuardResponse(this.guardRepository.save(newGuard));
  }

  public void deleteGuardsByGuardianIdAndDependentId(Long guardianId, Long dependentId) {
    this.guardRepository.deleteByGuardianIdAndDependentId(guardianId, dependentId);
  }

  public void deleteAllGuards() {
    this.guardRepository.deleteAll();
  }
}

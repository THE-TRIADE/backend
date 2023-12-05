package imd.ufrn.familyroutine.service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.hibernate.validator.internal.util.stereotypes.Lazy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import imd.ufrn.familyroutine.model.Guardian;
import imd.ufrn.familyroutine.model.Spent;
import imd.ufrn.familyroutine.model.api.GuardianMapper;
import imd.ufrn.familyroutine.model.api.request.LoginRequest;
import imd.ufrn.familyroutine.model.api.request.SpentRequest;
import imd.ufrn.familyroutine.model.api.response.FamilyGroupResponse;
import imd.ufrn.familyroutine.model.api.response.GuardResponse;
import imd.ufrn.familyroutine.model.api.response.GuardianResponse;
import imd.ufrn.familyroutine.model.api.response.GuardianTokenResponse;
import imd.ufrn.familyroutine.model.api.response.SpentResponse;
import imd.ufrn.familyroutine.repository.GuardianRepository;
import imd.ufrn.familyroutine.security.JwtUtil;
import imd.ufrn.familyroutine.service.exception.EmailAlreadyInUseException;
import imd.ufrn.familyroutine.service.exception.EntityNotFoundException;

@Service
public class GuardianService {
    @Autowired
    private GuardianRepository guardianRepository;
    @Autowired
    private GuardService guardService;
    @Autowired
    private FamilyGroupService familyGroupService;

    @Lazy
    @Autowired
    private GuardianMapper guardianMapper;
    
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private PersonService personService;

    @Autowired
    private JwtUtil jwtUtil;

    public List<Guardian> findAll() {
        return this.guardianRepository.findAll();
    }

    public Guardian findGuardianById(Long guardianId) {
        return this.guardianRepository
                    .findById(guardianId)
                    .orElseThrow(() -> new EntityNotFoundException(guardianId, Guardian.class));
    }

    public GuardianResponse findGuardianByGuardianId(Long guardianId) {
        Guardian guardian = this.guardianRepository
                                .findById(guardianId)
                                .orElseThrow(() -> new EntityNotFoundException(guardianId, Guardian.class));

        // FIXME Fazer apenas uma consulta: findFamilyGroupsByGuardianId
        List<GuardResponse> guards = this.guardService.findGuardsByGuardianId(guardianId);
        Set<FamilyGroupResponse> familyGroups = new HashSet<>();
        guards.stream()
            .forEach(guard -> {
                FamilyGroupResponse fg = this.familyGroupService.findByDependentId(guard.getDependentId());
                familyGroups.add(fg);
            });
        
        return guardianMapper.mapGuardianToGuardianResponse(guardian, guards, familyGroups);
    }

    @Transactional
    public void deleteAllGuardians() {
        List<Guardian> guardians = this.findAll();
        this.personService.deleteAllGuardians(guardians);
    }

    public void deleteGuardianById(Long guardianId) {
        this.personService.deletePersonById(guardianId);
    }

    public GuardianTokenResponse authenticateGuardian(LoginRequest loginRequest) {
        authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword())
        );
        // FIXME: Expand EntityNotFoundException to accept string
        Guardian guardianAuthenticated = this.guardianRepository
            .findByEmail(loginRequest.getUsername())
            .orElseThrow(() -> new EntityNotFoundException(0L, Guardian.class));
        
        return this.guardianMapper.mapGuardianToGuardianTokenResponse(guardianAuthenticated, jwtUtil.createToken(guardianAuthenticated));
    }

    @Transactional
    public Guardian createGuardian(Guardian newGuardian) {
        this.guardianValidOrError(newGuardian);
        newGuardian.setPassword(passwordEncoder.encode(newGuardian.getPassword()));
        return this.guardianRepository.save(newGuardian);
    }

    public GuardianResponse updateguardian(Long guardianId, Guardian putGuardian) {
        if(!guardianRepository.existsById(guardianId)){
            throw new EntityNotFoundException(guardianId, Guardian.class);
        }

        putGuardian.setId(guardianId);
        List<GuardResponse> guards = this.guardService.findGuardsByGuardianId(guardianId);
        Set<FamilyGroupResponse> familyGroups = new HashSet<>();
        guards.stream()
            .forEach(guard -> {
                FamilyGroupResponse fg = this.familyGroupService.findByDependentId(guard.getDependentId());
                familyGroups.add(fg);
            });
        
        return this.guardianMapper.mapGuardianToGuardianResponse(this.guardianRepository.save(putGuardian), guards, familyGroups);
    }

    private void guardianValidOrError(Guardian guardian) {
        Optional<Guardian> guardianOptional = this.guardianRepository.findByEmail(guardian.getEmail());
        if(guardianOptional.isPresent()) {
            throw new EmailAlreadyInUseException();
        }
    }
}

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
import imd.ufrn.familyroutine.model.Person;
import imd.ufrn.familyroutine.model.api.GuardianMapper;
import imd.ufrn.familyroutine.model.api.request.LoginRequest;
import imd.ufrn.familyroutine.model.api.response.FamilyGroupResponse;
import imd.ufrn.familyroutine.model.api.response.GuardResponse;
import imd.ufrn.familyroutine.model.api.response.GuardianResponse;
import imd.ufrn.familyroutine.repository.GuardianRepository;
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
        
        return guardianMapper.mapGuardianToGuardianReponse(guardian, guards, familyGroups);
    }
    
    @Transactional
    public Guardian createGuardianInCascade(Guardian newGuardian) {
        Person personCreated = this.personService.createPerson(newGuardian);
        newGuardian.setId(personCreated.getId());
        return this.createGuardian(newGuardian);
    }

    @Transactional
    public void deleteAllGuardians() {
        List<Guardian> guardians = this.findAll();
        this.personService.deleteAllGuardians(guardians);
    }

    public void deleteGuardianById(Long guardianId) {
        this.personService.deletePersonById(guardianId);
    }

    public Guardian authenticateGuardian(LoginRequest loginRequest) {
        authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword())
        );
        
        return this.guardianRepository.findByEmail(loginRequest.getUsername()).get();
    }

    private Guardian createGuardian(Guardian newGuardian) {
        this.guardianValidOrError(newGuardian);
        newGuardian.setPassword(passwordEncoder.encode(newGuardian.getPassword()));
        return this.guardianRepository.save(newGuardian);
    }

    private void guardianValidOrError(Guardian guardian) {
        Optional<Guardian> guardianOptional = this.guardianRepository.findByEmail(guardian.getEmail());
        if(guardianOptional.isPresent()) {
            throw new EmailAlreadyInUseException();
        }
    }
}

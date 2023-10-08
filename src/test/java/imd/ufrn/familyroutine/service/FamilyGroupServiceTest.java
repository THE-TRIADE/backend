// package imd.ufrn.familyroutine.service;

// import static org.junit.jupiter.api.Assertions.assertEquals;
// import static org.junit.jupiter.api.Assertions.assertThrows;
// import static org.mockito.Mockito.times;
// import static org.mockito.Mockito.verify;

// import java.sql.Date;
// import java.time.LocalDateTime;
// import java.util.ArrayList;
// import java.util.List;
// import java.util.Optional;

// import org.junit.jupiter.api.Nested;
// import org.junit.jupiter.api.Test;
// import org.junit.jupiter.api.extension.ExtendWith;
// import org.mockito.InjectMocks;
// import org.mockito.Mock;
// import org.mockito.Mockito;
// import org.springframework.test.context.junit.jupiter.SpringExtension;

// import imd.ufrn.familyroutine.model.Dependent;
// import imd.ufrn.familyroutine.model.FamilyGroup;
// import imd.ufrn.familyroutine.model.api.FamilyGroupMapper;
// import imd.ufrn.familyroutine.model.api.request.FamilyGroupRequest;
// import imd.ufrn.familyroutine.model.api.response.FamilyGroupResponse;
// import imd.ufrn.familyroutine.model.api.response.GuardResponse;
// import imd.ufrn.familyroutine.repository.FamilyGroupRepository;
// import imd.ufrn.familyroutine.service.exception.EntityNotFoundException;

// @ExtendWith(SpringExtension.class)
// public class FamilyGroupServiceTest {

//     @InjectMocks
//     private FamilyGroupService familyGroupService;
    
//     @Mock
//     private FamilyGroupRepository familyGroupRepository;

//     @Mock
//     private FamilyGroupMapper familyGroupMapper;
    
//     @Mock
//     private DependentService dependentService;
    
//     @Mock
//     private GuardService guardService;

//     @Nested
//     public class FindFamilyGroupById {
//         /*
//          * Positivo simples - ID que existe e é um Long
//          * Negativo com entradas válidas  - ID não existe, mas é um Long
//          */

//         @Test
//         void shouldFindTheFamilyGroupById() {
//             Long familyGroupId = 1L;
//             FamilyGroup testRepository = new FamilyGroup("Test");
//             testRepository.setId(familyGroupId);
//             FamilyGroupResponse testResponse = new FamilyGroupResponse();
//             testResponse.setId(testRepository.getId());
//             testResponse.setName(testRepository.getName());

//             Mockito.when(familyGroupRepository.findById(familyGroupId)).thenReturn( Optional.of(testRepository)); 
//             Mockito.when(familyGroupMapper.mapFamilyGroupToFamilyGroupResponse(testRepository)).thenReturn(testResponse);
            
//             FamilyGroupResponse familyGroup = familyGroupService.findFamilyGroupById(familyGroupId);

//             assertEquals(1L, familyGroup.getId());
//             assertEquals("Test", familyGroup.getName());
//         }

//         @Test
//         void shouldNotFindTheFamilyGroupById() {
//             Long familyGroupId = 1L;
//             FamilyGroup testRepository = new FamilyGroup("Test");
//             testRepository.setId(familyGroupId);

//             Mockito.when(familyGroupRepository.findById(familyGroupId)).thenReturn(Optional.empty());
            
//             assertThrows( EntityNotFoundException.class , () -> {
//                 familyGroupService.findFamilyGroupById(familyGroupId);
//             });
//         }
//     }

//     @Nested
//     public class CreateFamilyGroup {
//         /*
//          * Positivo simples - id e nome
//          * Positivo expandido - id, nome e dependentes
//          */
//         @Test
//         void shouldCreateSimpleFamilyGroup() {
//             Long familyGroupId = 1L;
//             FamilyGroup testRepository = new FamilyGroup("Test");
//             testRepository.setId(familyGroupId);

//             FamilyGroupRequest testRequest = new FamilyGroupRequest();
//             testRequest.setName(testRepository.getName());

//             FamilyGroupResponse testResponse = new FamilyGroupResponse();
//             testResponse.setName(testRepository.getName());
//             testResponse.setId(testRepository.getId());

//             Mockito.when(familyGroupRepository.save(testRepository)).thenReturn(testRepository);
//             Mockito.when(familyGroupMapper.mapFamilyGroupToFamilyGroupResponse(testRepository)).thenReturn(testResponse);
//             Mockito.when(familyGroupMapper.mapFamilyGroupRequestToFamilyGroup(testRequest)).thenReturn(testRepository);
            
//             FamilyGroupResponse familyGroup = familyGroupService.createFamilyGroup(testRequest);
//             assertEquals(testResponse, familyGroup);
//         }

//         @Test
//         void shouldCreateFamilyGroupWithDependents() {
//             List<Dependent> dependents = new ArrayList<>();
//             dependents.add(new Dependent(1L, "Teste 1", "12345678910L", Date.valueOf(LocalDateTime.now().toLocalDate())));
//             dependents.add(new Dependent(2L, "Teste 2", "01987654321L", Date.valueOf(LocalDateTime.now().toLocalDate())));

//             Long familyGroupId = 1L;
//             FamilyGroup testRepository = new FamilyGroup("Test");
//             testRepository.setId(familyGroupId);

//             FamilyGroupRequest testRequest = new FamilyGroupRequest();
//             testRequest.setName(testRepository.getName());
//             testRequest.setDependents(dependents);

//             FamilyGroupResponse testResponse = new FamilyGroupResponse();
//             testResponse.setName(testRepository.getName());
//             testResponse.setId(testRepository.getId());
//             testResponse.setDependents(dependents);

//             GuardResponse guardResponse = new GuardResponse();

//             Mockito.when(familyGroupRepository.save(testRepository)).thenReturn(testRepository);
//             Mockito.when(familyGroupMapper.mapFamilyGroupRequestToFamilyGroup(testRequest)).thenReturn(testRepository);
//             Mockito.when(familyGroupMapper.mapFamilyGroupToFamilyGroupResponse(testRepository)).thenReturn(testResponse);
//             Mockito.when(dependentService.createDependentInCascade(dependents.get(0))).thenReturn(dependents.get(0));
//             Mockito.when(dependentService.createDependentInCascade(dependents.get(1))).thenReturn(dependents.get(1));
//             Mockito.when(guardService.createGuard(Mockito.any())).thenReturn(guardResponse);
            
//             FamilyGroupResponse familyGroup = familyGroupService.createFamilyGroup(testRequest);
//             assertEquals(testResponse, familyGroup);
//         }
//     }

//     @Nested
//     public class DeleteFamilyGroupById {

//         @Test
//         public void shouldDeleteFamilyGroupById() {
//             Long familyGroupId = 1L;

//             Mockito.when(familyGroupRepository.findDependentsByFamilyGroupId(familyGroupId)).thenReturn(new ArrayList<Dependent>());
            
//             familyGroupService.deleteFamilyGroupById(familyGroupId); 
            
//             verify(familyGroupRepository, times(1)).deleteById(familyGroupId);
            
//         }

//     }

//     @Nested
//     public class GetFamilyGroupDependentsByFamilyGroupId{
        
//         @Test
//         public void shouldTryFindFDependents(){
//             Long familyGroupId = 1L;

//             Mockito.when(familyGroupRepository.findDependentsByFamilyGroupId(familyGroupId)).thenReturn(new ArrayList<Dependent>());

//             familyGroupService.getFamilyGroupDependentsByFamilyGroupId(familyGroupId);
            
//             verify(familyGroupRepository, times(1)).findDependentsByFamilyGroupId(familyGroupId);

//         }
        
//         @Test
//         public void shouldFindDependents(){
//             Long familyGroupId = 1L;
//             List<Dependent> dependents = new ArrayList<>();
//             dependents.add(new Dependent(1L, "Teste 1", "12345678910L", Date.valueOf(LocalDateTime.now().toLocalDate())));
//             dependents.add(new Dependent(2L, "Teste 2", "01987654321L", Date.valueOf(LocalDateTime.now().toLocalDate())));

//             Mockito.when(familyGroupRepository.findDependentsByFamilyGroupId(familyGroupId)).thenReturn(dependents);

//             List<Dependent> dependentsResult = familyGroupService.getFamilyGroupDependentsByFamilyGroupId(familyGroupId);
            
//             verify(familyGroupRepository, times(1)).findDependentsByFamilyGroupId(familyGroupId);
//             assertEquals( dependents, dependentsResult);

//         }
//     }

//     @Nested
//     public class FindByDependentId{

//         @Test
//         public void shoulFindFamilyGroupByDependentId(){
//             Long familyGroupId = 1L;
//             Long dependentId = 1L;
//             FamilyGroup testRepository = new FamilyGroup("Test");
//             testRepository.setId(familyGroupId);
//             FamilyGroupResponse testResponse = new FamilyGroupResponse();
//             testResponse.setId(testRepository.getId());
//             testResponse.setName(testRepository.getName());

//             Mockito.when(familyGroupRepository.findByDependentId(dependentId)).thenReturn( Optional.of(testRepository)); 
//             Mockito.when(familyGroupMapper.mapFamilyGroupToFamilyGroupResponse(testRepository)).thenReturn(testResponse);

//             FamilyGroupResponse familyGroup = familyGroupService.findByDependentId(dependentId);

//             assertEquals(1L, familyGroup.getId());
//             assertEquals("Test", familyGroup.getName());
            
//         }

//         @Test
//         void shouldNotFindTheFamilyGroupByDependentId() {
//             Long dependentId = 1L;
            
//             Mockito.when(familyGroupRepository.findByDependentId(dependentId)).thenReturn(Optional.empty());
            
//             assertThrows( EntityNotFoundException.class, () -> {
//                 familyGroupService.findByDependentId(dependentId);
//             });
//         }
//     }
// }

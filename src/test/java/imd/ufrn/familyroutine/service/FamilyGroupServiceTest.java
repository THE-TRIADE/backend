package imd.ufrn.familyroutine.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.sql.Date;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.springframework.test.context.junit.jupiter.SpringExtension;

import imd.ufrn.familyroutine.model.Dependent;
import imd.ufrn.familyroutine.model.FamilyGroup;
import imd.ufrn.familyroutine.model.api.FamilyGroupMapper;
import imd.ufrn.familyroutine.model.api.request.FamilyGroupRequest;
import imd.ufrn.familyroutine.model.api.response.FamilyGroupResponse;
import imd.ufrn.familyroutine.model.api.response.GuardResponse;
import imd.ufrn.familyroutine.repository.FamilyGroupRepository;
import imd.ufrn.familyroutine.service.exception.EntityNotFoundException;

@ExtendWith(SpringExtension.class)
public class FamilyGroupServiceTest {

    @InjectMocks
    private FamilyGroupService familyGroupService;
    
    @Mock
    private FamilyGroupRepository familyGroupRepository;

    @Mock
    private FamilyGroupMapper familyGroupMapper;
    
    @Mock
    private DependentService dependentService;
    
    @Mock
    private GuardService guardService;

    @Nested
    public class FindAll {

        @Nested
        public class WhenIsEmpty {
            @Test
            void shouldReturnAnEmptyList() {
                Mockito.when(familyGroupRepository.findAll()).thenReturn(List.of());
                Mockito.when(familyGroupMapper.mapFamilyGroupToFamilyGroupResponse(null)).thenReturn(null);

                List<FamilyGroupResponse> familyGroups = familyGroupService.findAll();
                
                assertTrue(familyGroups.isEmpty());
            }
        }

        @Nested
        public class WhenHasOneFamilyGroup {
            @Test
            void shouldReturnAnListWithOneFamilyGroupResponse() {
                FamilyGroup testRepository = new FamilyGroup("Test");
                FamilyGroupResponse testResponse = new FamilyGroupResponse();
                testResponse.setId(1L);
                testResponse.setName(testRepository.getName());
                List<FamilyGroup> findAllResponse = new ArrayList<>();
                findAllResponse.add(testRepository);

                Mockito.when(familyGroupRepository.findAll()).thenReturn(findAllResponse);
                Mockito.when(familyGroupMapper.mapFamilyGroupToFamilyGroupResponse(testRepository)).thenReturn(testResponse);
                
                List<FamilyGroupResponse> familyGroups = familyGroupService.findAll();

                assertTrue(familyGroups.size() == 1);
                familyGroups.forEach(familyGroup -> {
                    assertEquals(1, familyGroup.getId());
                    assertEquals("Test", familyGroup.getName());
                });
            }
        }

        @Nested
        public class WhenHasMoreThanOneFamilyGroup {
            @Test
            void shouldReturnAnListWithMoreThanOneFamilyGroupResponse() {
                List<FamilyGroup> testRepositories = new ArrayList<>();
                List<FamilyGroupResponse> testResponses = new ArrayList<>();
                
                for (Long i = 0L; i < 3L; ++i){
                    FamilyGroup familyGroup = new FamilyGroup("Test " + i);
                    familyGroup.setId(i);
                    testRepositories.add(familyGroup);

                    FamilyGroupResponse familyGroupResponse = new FamilyGroupResponse();
                    familyGroupResponse.setId(familyGroup.getId());
                    familyGroupResponse.setName(familyGroup.getName());
                    testResponses.add(familyGroupResponse);
                };

                Mockito.when(familyGroupRepository.findAll()).thenReturn(testRepositories);
                Mockito.when(familyGroupMapper.mapFamilyGroupToFamilyGroupResponse(testRepositories.get(0)))
                    .thenReturn(testResponses.get(0));
                Mockito.when(familyGroupMapper.mapFamilyGroupToFamilyGroupResponse(testRepositories.get(1)))
                    .thenReturn(testResponses.get(1));
                Mockito.when(familyGroupMapper.mapFamilyGroupToFamilyGroupResponse(testRepositories.get(2)))
                    .thenReturn(testResponses.get(2));
                
                List<FamilyGroupResponse> familyGroups = familyGroupService.findAll();

                assertTrue(familyGroups.size() == 3);
                assertEquals(0L, familyGroups.get(0).getId());
                assertEquals("Test " + 0, familyGroups.get(0).getName());
                assertEquals(1L, familyGroups.get(1).getId());
                assertEquals("Test " + 1, familyGroups.get(1).getName());
                assertEquals(2L, familyGroups.get(2).getId());
                assertEquals("Test " + 2, familyGroups.get(2).getName());
                
            }
        }

    }

    @Nested
    public class FindFamilyGroupById {
        /*
         * Positivo simples - ID que existe e é um Long
         * Negativo com entradas válidas  - ID não existe, mas é um Long
         */

        @Test
        void shouldFindTheFamilyGroupById() {
            Long familyGroupId = 1L;
            FamilyGroup testRepository = new FamilyGroup("Test");
            testRepository.setId(familyGroupId);
            FamilyGroupResponse testResponse = new FamilyGroupResponse();
            testResponse.setId(testRepository.getId());
            testResponse.setName(testRepository.getName());

            Mockito.when(familyGroupRepository.findById(familyGroupId)).thenReturn( Optional.of(testRepository)); 
            Mockito.when(familyGroupMapper.mapFamilyGroupToFamilyGroupResponse(testRepository)).thenReturn(testResponse);
            
            FamilyGroupResponse familyGroup = familyGroupService.findFamilyGroupById(familyGroupId);

            assertEquals(1L, familyGroup.getId());
            assertEquals("Test", familyGroup.getName());
        }

        @Test
        void shouldNotFindTheFamilyGroupById() {
            Long familyGroupId = 1L;
            FamilyGroup testRepository = new FamilyGroup("Test");
            testRepository.setId(familyGroupId);

            Mockito.when(familyGroupRepository.findById(familyGroupId)).thenReturn(Optional.empty());
            
            assertThrows( EntityNotFoundException.class , () -> {
                familyGroupService.findFamilyGroupById(familyGroupId);
            });
        }
    }

    @Nested
    public class CreateFamilyGroup {
        /*
         * Positivo simples - id e nome
         * Positivo expandido - id, nome e dependentes
         */
        @Test
        void shouldCreateSimpleFamilyGroup() {
            Long familyGroupId = 1L;
            FamilyGroup testRepository = new FamilyGroup("Test");
            testRepository.setId(familyGroupId);

            FamilyGroupRequest testRequest = new FamilyGroupRequest();
            testRequest.setName(testRepository.getName());

            FamilyGroupResponse testResponse = new FamilyGroupResponse();
            testResponse.setName(testRepository.getName());
            testResponse.setId(testRepository.getId());

            Mockito.when(familyGroupRepository.save(testRepository)).thenReturn(testRepository);
            Mockito.when(familyGroupMapper.mapFamilyGroupToFamilyGroupResponse(testRepository)).thenReturn(testResponse);
            Mockito.when(familyGroupMapper.mapFamilyGroupRequestToFamilyGroup(testRequest)).thenReturn(testRepository);
            
            FamilyGroupResponse familyGroup = familyGroupService.createFamilyGroup(testRequest);
            assertEquals(testResponse, familyGroup);
        }

        @Test
        void shouldCreateFamilyGroupWithDependents() {
            List<Dependent> dependents = new ArrayList<>();
            dependents.add(new Dependent(1L, "Teste 1", "12345678910L", Date.valueOf(LocalDateTime.now().toLocalDate())));
            dependents.add(new Dependent(2L, "Teste 2", "01987654321L", Date.valueOf(LocalDateTime.now().toLocalDate())));

            Long familyGroupId = 1L;
            FamilyGroup testRepository = new FamilyGroup("Test");
            testRepository.setId(familyGroupId);

            FamilyGroupRequest testRequest = new FamilyGroupRequest();
            testRequest.setName(testRepository.getName());
            testRequest.setDependents(dependents);

            FamilyGroupResponse testResponse = new FamilyGroupResponse();
            testResponse.setName(testRepository.getName());
            testResponse.setId(testRepository.getId());
            testResponse.setDependents(dependents);

            GuardResponse guardResponse = new GuardResponse();

            Mockito.when(familyGroupRepository.save(testRepository)).thenReturn(testRepository);
            Mockito.when(familyGroupMapper.mapFamilyGroupRequestToFamilyGroup(testRequest)).thenReturn(testRepository);
            Mockito.when(familyGroupMapper.mapFamilyGroupToFamilyGroupResponse(testRepository)).thenReturn(testResponse);
            Mockito.when(dependentService.createDependentInCascade(dependents.get(0))).thenReturn(dependents.get(0));
            Mockito.when(dependentService.createDependentInCascade(dependents.get(1))).thenReturn(dependents.get(1));
            Mockito.when(guardService.createGuard(Mockito.any())).thenReturn(guardResponse);
            
            FamilyGroupResponse familyGroup = familyGroupService.createFamilyGroup(testRequest);
            assertEquals(testResponse, familyGroup);
        }
    }

    @Nested
    public class DeleteFamilyGroupById {

        @Test
        public void shouldDeleteFamilyGroupById() {
            Long familyGroupId = 1L;

            Mockito.when(familyGroupRepository.findDependentsByFamilyGroupId(familyGroupId)).thenReturn(Optional.of(new ArrayList<Dependent>()));
            
            familyGroupService.deleteFamilyGroupById(familyGroupId); 
            
            verify(familyGroupRepository, times(1)).deleteById(familyGroupId);
            
        }

    }

    @Nested
    public class DeleteAllFamilyGroups{

        @Test
        public void shouldDeleteFamilyGroupById() {
            Mockito.doNothing().when(familyGroupRepository).deleteAll();
            Mockito.doNothing().when(dependentService).deleteAllDependents();
            
            familyGroupService.deleteAllFamilyGroups(); 
            
            verify(dependentService, times(1)).deleteAllDependents();
            verify(familyGroupRepository, times(1)).deleteAll();
        }
    }

    @Nested
    public class GetFamilyGroupDependentsByFamilyGroupId{
        
        @Test
        public void shouldTryFindFDependents(){
            Long familyGroupId = 1L;

            Mockito.when(familyGroupRepository.findDependentsByFamilyGroupId(familyGroupId)).thenReturn(Optional.of(new ArrayList<Dependent>()));

            familyGroupService.getFamilyGroupDependentsByFamilyGroupId(familyGroupId);
            
            verify(familyGroupRepository, times(1)).findDependentsByFamilyGroupId(familyGroupId);

        }
        
        @Test
        public void shouldFindDependents(){
            Long familyGroupId = 1L;
            List<Dependent> dependents = new ArrayList<>();
            dependents.add(new Dependent(1L, "Teste 1", "12345678910L", Date.valueOf(LocalDateTime.now().toLocalDate())));
            dependents.add(new Dependent(2L, "Teste 2", "01987654321L", Date.valueOf(LocalDateTime.now().toLocalDate())));

            Mockito.when(familyGroupRepository.findDependentsByFamilyGroupId(familyGroupId)).thenReturn(Optional.of(dependents));

            List<Dependent> dependentsResult = familyGroupService.getFamilyGroupDependentsByFamilyGroupId(familyGroupId);
            
            verify(familyGroupRepository, times(1)).findDependentsByFamilyGroupId(familyGroupId);
            assertEquals( dependents, dependentsResult);

        }
    }

    @Nested
    public class FindByDependentId{

        @Test
        public void shoulFindFamilyGroupByDependentId(){
            Long familyGroupId = 1L;
            Long dependentId = 1L;
            FamilyGroup testRepository = new FamilyGroup("Test");
            testRepository.setId(familyGroupId);
            FamilyGroupResponse testResponse = new FamilyGroupResponse();
            testResponse.setId(testRepository.getId());
            testResponse.setName(testRepository.getName());

            Mockito.when(familyGroupRepository.findByDependentId(dependentId)).thenReturn( Optional.of(testRepository)); 
            Mockito.when(familyGroupMapper.mapFamilyGroupToFamilyGroupResponse(testRepository)).thenReturn(testResponse);

            FamilyGroupResponse familyGroup = familyGroupService.findByDependentId(dependentId);

            assertEquals(1L, familyGroup.getId());
            assertEquals("Test", familyGroup.getName());
            
        }

        @Test
        void shouldNotFindTheFamilyGroupByDependentId() {
            Long dependentId = 1L;
            
            Mockito.when(familyGroupRepository.findByDependentId(dependentId)).thenReturn(Optional.empty());
            
            assertThrows( EntityNotFoundException.class, () -> {
                familyGroupService.findByDependentId(dependentId);
            });
        }
    }
}

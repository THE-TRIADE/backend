package imd.ufrn.familyroutine.service;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.time.DayOfWeek;
import java.util.*;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

import imd.ufrn.familyroutine.model.Guard;
import imd.ufrn.familyroutine.model.GuardianRole;
import imd.ufrn.familyroutine.model.api.GuardMapper;
import imd.ufrn.familyroutine.model.api.request.GuardRequest;
import imd.ufrn.familyroutine.model.api.response.GuardResponse;
import imd.ufrn.familyroutine.repository.GuardRepository;
import imd.ufrn.familyroutine.service.exception.EntityNotFoundException;
import imd.ufrn.familyroutine.service.exception.InvalidImmutableArgumentsException;

@ExtendWith(SpringExtension.class)
public class GuardServiceTest {
    @InjectMocks
    private GuardService guardService;

    @Mock
    private GuardRepository guardRepository;

    @Mock
    private GuardRepository guardResponse;

    @Mock
    private GuardMapper guardMapper;

    @Mock
    private ValidationService validationService;

    @Nested
    public class UpdateGuard {
        @Test
        public void shouldUpdateGuardAndReturnUpdatedGuard() {
            Long guardId = 1L;

            Guard guard = new Guard();
            guard.setId(guardId);
            guard.setDependentId(1L);
            guard.setGuardianId(1L);
            guard.setGuardianRole(GuardianRole.FATHER);

            GuardRequest guardRequest = new GuardRequest();
            guardRequest.setGuardianRole(GuardianRole.MOTHER);
            guardRequest.setDependentId(1L);
            guardRequest.setGuardianId(1L);

            Guard guardUpdated = new Guard();
            guardUpdated.setId(guardId);
            guardUpdated.setDependentId(guardRequest.getDependentId());
            guardUpdated.setGuardianId(guardRequest.getGuardianId());
            guardUpdated.setGuardianRole(guardRequest.getGuardianRole());

            Mockito.when(guardMapper.mapGuardRequestToGuard(guardRequest)).thenReturn(guardUpdated);

            Mockito.when(guardRepository.findById(guardId)).thenReturn(Optional.of(guard));

            Mockito.when(guardRepository.update(guard)).thenReturn(guard);

            Mockito.when(validationService.validDaysOfWeekOrError(guardRequest.getDaysOfWeek()))
                    .thenReturn(guardRequest.getDaysOfWeek());

            Guard updatedGuard = guardService.updateGuard(guardId, guardRequest);

            assertEquals(guard, updatedGuard);
        }

        @Test
        public void shouldUpdateGuardDaysOfWeekAndGuardianRole() {
            Long guardId = 1L;

            Guard guard = new Guard();
            guard.setId(guardId);
            guard.setDependentId(1L);
            guard.setGuardianId(1L);
            guard.setGuardianRole(GuardianRole.FATHER);

            GuardRequest guardRequest = new GuardRequest();
            guardRequest.setGuardianRole(GuardianRole.MOTHER);
            guardRequest.setDependentId(1L);
            guardRequest.setGuardianId(1L);
            guardRequest.setDaysOfWeek(Arrays.asList(DayOfWeek.MONDAY, DayOfWeek.TUESDAY)
                    .stream()
                    .map(DayOfWeek::getValue)
                    .toList());         
            Guard guardUpdated = new Guard();    
            guardUpdated.setId(guardId);
            guardUpdated.setDependentId(guardRequest.getDependentId());
            guardUpdated.setGuardianId(guardRequest.getGuardianId());
            guardUpdated.setGuardianRole(guardRequest.getGuardianRole());
            guardUpdated.setDaysOfWeek(guardRequest.getDaysOfWeek()
                    .stream()
                    .map(day -> DayOfWeek.of(day))
                    .toList());

            Mockito.when(guardMapper.mapGuardRequestToGuard(guardRequest)).thenReturn(guardUpdated);

            Mockito.when(guardRepository.findById(guardId)).thenReturn(Optional.of(guard));

            Mockito.when(guardRepository.update(guard)).thenReturn(guard);

            Mockito.when(validationService.validDaysOfWeekOrError(guardRequest.getDaysOfWeek()))
                    .thenReturn(guardRequest.getDaysOfWeek());

            Guard updatedGuard = guardService.updateGuard(guardId, guardRequest);

            assertEquals(guardUpdated.getDaysOfWeek(), updatedGuard.getDaysOfWeek());
            assertEquals(guardUpdated.getGuardianRole(), updatedGuard.getGuardianRole());
        }
        
        @Test
        public void shouldThrowInvalidImmutableArgumentsExceptionWithDependentIdModified() {
            Long guardId = 1L;

            Guard guard = new Guard();
            guard.setId(guardId);
            guard.setDependentId(1L);
            guard.setGuardianId(1L);
            guard.setGuardianRole(GuardianRole.FATHER);

            GuardRequest guardRequest = new GuardRequest();
            guardRequest.setGuardianRole(GuardianRole.MOTHER);
            guardRequest.setDependentId(3L);
            guardRequest.setGuardianId(1L);

            Guard guardUpdated = new Guard();
            guardUpdated.setId(guardId);
            guardUpdated.setDependentId(guardRequest.getDependentId());
            guardUpdated.setGuardianId(guardRequest.getGuardianId());
            guardUpdated.setGuardianRole(guardRequest.getGuardianRole());

            Mockito.when(guardMapper.mapGuardRequestToGuard(guardRequest)).thenReturn(guardUpdated);

            Mockito.when(guardRepository.findById(guardId)).thenReturn(Optional.of(guard));

            Mockito.when(guardRepository.update(guard)).thenReturn(guard);

            Mockito.when(validationService.validDaysOfWeekOrError(guardRequest.getDaysOfWeek()))
                    .thenReturn(guardRequest.getDaysOfWeek());


            assertThrows(InvalidImmutableArgumentsException.class, () -> {
                guardService.updateGuard(guardId, guardRequest);
            });
        }

        @Test
        public void shouldThrowInvalidImmutableArgumentsExceptionWithGuardianIdModified() {
            Long guardId = 1L;

            Guard guard = new Guard();
            guard.setId(guardId);
            guard.setDependentId(1L);
            guard.setGuardianId(1L);
            guard.setGuardianRole(GuardianRole.FATHER);

            GuardRequest guardRequest = new GuardRequest();
            guardRequest.setGuardianRole(GuardianRole.MOTHER);
            guardRequest.setDependentId(1L);
            guardRequest.setGuardianId(3L);

            Guard guardUpdated = new Guard();
            guardUpdated.setId(guardId);
            guardUpdated.setDependentId(guardRequest.getDependentId());
            guardUpdated.setGuardianId(guardRequest.getGuardianId());
            guardUpdated.setGuardianRole(guardRequest.getGuardianRole());

            Mockito.when(guardMapper.mapGuardRequestToGuard(guardRequest)).thenReturn(guardUpdated);

            Mockito.when(guardRepository.findById(guardId)).thenReturn(Optional.of(guard));

            Mockito.when(guardRepository.update(guard)).thenReturn(guard);

            Mockito.when(validationService.validDaysOfWeekOrError(guardRequest.getDaysOfWeek()))
                    .thenReturn(guardRequest.getDaysOfWeek());


            assertThrows(InvalidImmutableArgumentsException.class, () -> {
                guardService.updateGuard(guardId, guardRequest);
            });
        }
    }

    @Nested
    public class DeleteGuardById {
        @Test
        public void shouldDeleteGuardById() {
            Long guardId = 1L;

            guardService.deleteGuardById(guardId);

            verify(guardRepository, times(1)).deleteById(guardId);
        }
    }

    @Nested
    public class DeleteAllGuards {
        @Test
        public void shouldDeleteAllGuards() {
            guardService.deleteAllGuards();

            verify(guardRepository, times(1)).deleteAll();
        }
    }

    @Nested
    public class FindGuardById {
        @Test
        public void testFindGuardById() {
            Long guardId = 1L;

            Guard guard = new Guard();
            guard.setId(guardId);
            guard.setDependentId(1L);
            guard.setGuardianId(1L);
            guard.setGuardianRole(GuardianRole.FATHER);

            GuardResponse guardResponse = new GuardResponse();
            guardResponse.setId(guard.getId());
            guardResponse.setDependentId(guard.getDependentId());
            guardResponse.setGuardianId(guard.getGuardianId());
            guardResponse.setGuardianRole(guard.getGuardianRole());

            Mockito.when(guardRepository.findById(guardId)).thenReturn(Optional.of(guard));

            Mockito.when(guardMapper.mapGuardToGuardResponse(guard))
                    .thenReturn(guardResponse);

            GuardResponse guardResponseReturn = guardService.findGuardById(guardId);

            assertEquals(guardResponse.getId(), guardResponseReturn.getId());
            assertEquals(guardResponse.getDependentId(), guardResponseReturn.getDependentId());
            assertEquals(guardResponse.getGuardianId(), guardResponseReturn.getGuardianId());
            assertEquals(guardResponse.getGuardianRole(), guardResponseReturn.getGuardianRole());
        }

        @Test
        public void testFindGuardByIdNotFound() {
            Long guardId = 1L;

            Mockito.when(guardRepository.findById(guardId)).thenReturn(Optional.empty());

            assertThrows(EntityNotFoundException.class, () -> {
                guardService.findGuardById(guardId);
            });

            verify(guardRepository, times(1)).findById(guardId);
        }
    }

    @Nested
    public class FindAll {

        @Nested
        public class WhenIsEmpty {
            @Test
            void shouldReturnAnEmptyList() {
                Mockito.when(guardRepository.findAll()).thenReturn(List.of());
                Mockito.when(guardMapper.mapGuardToGuardResponse(null)).thenReturn(null);

                List<GuardResponse> guards = guardService.findAllGuards();

                assertTrue(guards.isEmpty());
            }
        }

        @Nested
        public class WhenHasOneGuard {
            @Test
            void shouldReturnAListWithOneGuardResponse() {
                Guard guard = new Guard();
                guard.setId(1L);
                guard.setDependentId(1L);
                guard.setGuardianId(1L);
                guard.setGuardianRole(GuardianRole.FATHER);

                GuardResponse guardResponse = new GuardResponse();
                guardResponse.setId(guard.getId());
                guardResponse.setDependentId(guard.getDependentId());
                guardResponse.setGuardianId(guard.getGuardianId());
                guardResponse.setGuardianRole(guard.getGuardianRole());

                List<Guard> findAllResponse = new ArrayList<>();
                findAllResponse.add(guard);

                Mockito.when(guardRepository.findAll()).thenReturn(findAllResponse);
                Mockito.when(guardMapper.mapGuardToGuardResponse(guard)).thenReturn(guardResponse);

                List<GuardResponse> guards = guardService.findAllGuards();

                assertEquals(1, guards.size());
                guards.forEach(guarda -> {
                    assertEquals(guardResponse.getId(), guarda.getId());
                    assertEquals(guardResponse.getDependentId(), guarda.getDependentId());
                    assertEquals(guardResponse.getGuardianId(), guarda.getGuardianId());
                    assertEquals(guardResponse.getGuardianRole(), guarda.getGuardianRole());
                });
            }
        }

        @Nested
        public class WhenHasMoreThanOneGuard {
            @Test
            void shouldReturnAListWithMultipleGuardResponses() {
                List<Guard> testGuards = new ArrayList<>();
                List<GuardResponse> testResponses = new ArrayList<>();

                for (Long i = 0L; i < 3L; ++i) {
                    Guard guard = new Guard();
                    guard.setId(i);
                    guard.setDependentId(i);
                    guard.setGuardianId(i);
                    guard.setGuardianRole(GuardianRole.FATHER);
                    testGuards.add(guard);

                    GuardResponse guardResponse = new GuardResponse();
                    guardResponse.setId(guard.getId());
                    guardResponse.setDependentId(guard.getDependentId());
                    guardResponse.setGuardianId(guard.getGuardianId());
                    guardResponse.setGuardianRole(guard.getGuardianRole());
                    testResponses.add(guardResponse);
                }

                Mockito.when(guardRepository.findAll()).thenReturn(testGuards);
                Mockito.when(guardMapper.mapGuardToGuardResponse(testGuards.get(0))).thenReturn(testResponses.get(0));
                Mockito.when(guardMapper.mapGuardToGuardResponse(testGuards.get(1))).thenReturn(testResponses.get(1));
                Mockito.when(guardMapper.mapGuardToGuardResponse(testGuards.get(2))).thenReturn(testResponses.get(2));

                List<GuardResponse> guards = guardService.findAllGuards();

                assertEquals(3, guards.size());
                for (int i = 0; i < 3; i++) {
                    GuardResponse guardResponse = guards.get(i);
                    assertEquals(i, guardResponse.getId());
                    assertEquals(i, guardResponse.getDependentId());
                    assertEquals(i, guardResponse.getGuardianId());
                    assertEquals(GuardianRole.FATHER, guardResponse.getGuardianRole());
                }
            }
        }
    }

}

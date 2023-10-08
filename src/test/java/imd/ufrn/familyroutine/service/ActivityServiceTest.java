// package imd.ufrn.familyroutine.service;

// import static org.junit.jupiter.api.Assertions.assertEquals;
// import static org.junit.jupiter.api.Assertions.assertThrows;
// import static org.mockito.Mockito.times;
// import static org.mockito.Mockito.verify;

// import java.sql.Date;
// import java.sql.Time;
// import java.time.LocalDateTime;
// import java.util.List;

// import org.junit.jupiter.api.BeforeEach;
// import org.junit.jupiter.api.Nested;
// import org.junit.jupiter.api.Test;
// import org.junit.jupiter.api.extension.ExtendWith;
// import org.mockito.InjectMocks;
// import org.mockito.Mock;
// import org.mockito.Mockito;
// import org.springframework.test.context.junit.jupiter.SpringExtension;

// import imd.ufrn.familyroutine.model.Activity;
// import imd.ufrn.familyroutine.model.ActivityState;
// import imd.ufrn.familyroutine.model.GroupActivity;
// import imd.ufrn.familyroutine.model.api.ActivityMapper;
// import imd.ufrn.familyroutine.model.api.request.ActivityRequest;
// import imd.ufrn.familyroutine.model.api.response.ActivityResponse;
// import imd.ufrn.familyroutine.repository.ActivityRepository;
// import imd.ufrn.familyroutine.service.exception.ActivityIntervalException;
// import imd.ufrn.familyroutine.service.exception.ActivityIntervalType;
// import imd.ufrn.familyroutine.service.exception.GroupActivityException;
// import imd.ufrn.familyroutine.service.exception.GroupActivityType;

// @ExtendWith(SpringExtension.class)
// public class ActivityServiceTest {

//     @InjectMocks
//     private ActivityService activityService;

//     @Mock
//     private ActivityRepository activityRepository;

//     @Mock
//     private ActivityMapper activityMapper;

//     @Mock
//     private ValidationService validationService;

//     @Mock
//     private GroupActivityService groupActivityService;

//     @Nested
//     public class CreateActivity {

//         private ActivityRequest activityRequest;
//         private Activity activity;
//         private ActivityResponse activityResponse;
//         private LocalDateTime dateTimeStart;
//         private LocalDateTime dateTimeEnd;

//         // Remember to setUp dateStart, dateEnd, hourStart and hourEnd
//         @BeforeEach
//         public void setUp() {
//             activityRequest = new ActivityRequest();
//             activityRequest.setName("Activity Test");
//             activityRequest.setDependentId(2L);
//             activityRequest.setCurrentGuardian(1L);
//             activityRequest.setActor(2L);
//             activityRequest.setCreatedBy(1L);
//             activityRequest.setRepeat(false);
            
//             activity = new Activity();
//             activity.setId(1L);
//             activity.setName(activityRequest.getName());
//             activity.setDependentId(activityRequest.getDependentId());
//             activity.setCurrentGuardian(activityRequest.getCurrentGuardian());
//             activity.setActor(activityRequest.getActor());
//             activity.setCreatedBy(activityRequest.getCreatedBy());
//             activity.setState(ActivityState.CREATED);

//             activityResponse = new ActivityResponse();
//             activityResponse.setId(activity.getId());
//             activityResponse.setName(activity.getName());
//             activityResponse.setDependentId(activity.getDependentId());
//             activityResponse.setCurrentGuardianId(activity.getCurrentGuardian());
//             activityResponse.setActorId(activity.getActor());
//             activityResponse.setCreatedById(activity.getCreatedBy());
//         }

//         @Nested
//         public class WhenTheRequestParamsAreOk {

//             @Test
//             public void shouldReturnAnActivity() {
//                 dateTimeStart = LocalDateTime.now();
//                 dateTimeEnd = LocalDateTime.now().plusHours(2);
//                 activityRequest.setDateStart(dateTimeStart.toLocalDate());
//                 activityRequest.setDateEnd(dateTimeEnd.toLocalDate());
//                 activityRequest.setHourStart(dateTimeStart.toLocalTime());
//                 activityRequest.setHourEnd(dateTimeEnd.toLocalTime());
               
//                 activity.setDateStart(Date.valueOf(activityRequest.getDateStart()));
//                 activity.setDateEnd(Date.valueOf(activityRequest.getDateEnd()));
//                 activity.setHourStart(Time.valueOf(activityRequest.getHourStart()));
//                 activity.setHourEnd(Time.valueOf(activityRequest.getHourStart()));

//                 activityResponse.setDateStart(activity.getDateStart());
//                 activityResponse.setDateEnd(activity.getDateEnd());
//                 activityResponse.setHourStart(activity.getHourStart());
//                 activityResponse.setHourEnd(activity.getHourEnd());

//                 Mockito.when(activityMapper.mapActivityRequestToActivity(activityRequest)).thenReturn(activity);
//                 Mockito.when(validationService.validActivityOrError(activity)).thenReturn(activity);
//                 Mockito.when(activityService.createActivity(activity)).thenReturn(activity);
//                 Mockito.when(activityMapper.mapActivityToActivityResponse(activity)).thenReturn(activityResponse);


//                 ActivityResponse response = activityService.handleActivityRequest(activityRequest);
//                 assertEquals(1L, response.getId());
//                 assertEquals("Activity Test", response.getName());
//                 assertEquals(2L, response.getDependentId());
//                 assertEquals(1L, response.getCurrentGuardianId());
//                 assertEquals(2L, response.getActorId());
//                 assertEquals(1L, response.getCreatedById());
//             }
//         }

//         @Nested
//         public class WhenTheParamDateEndIsBeforeDateStart {
//             @Test
//             public void shouldThrowActivityIntervalDateException() {
//                 dateTimeStart = LocalDateTime.now();
//                 dateTimeEnd = LocalDateTime.now().minusDays(2);
//                 activityRequest.setDateStart(dateTimeStart.toLocalDate());
//                 activityRequest.setDateEnd(dateTimeEnd.toLocalDate());
//                 activityRequest.setHourStart(dateTimeStart.toLocalTime());
//                 activityRequest.setHourEnd(dateTimeEnd.toLocalTime());
               
//                 activity.setDateStart(Date.valueOf(activityRequest.getDateStart()));
//                 activity.setDateEnd(Date.valueOf(activityRequest.getDateEnd()));
//                 activity.setHourStart(Time.valueOf(activityRequest.getHourStart()));
//                 activity.setHourEnd(Time.valueOf(activityRequest.getHourStart()));

//                 activityResponse.setDateStart(activity.getDateStart());
//                 activityResponse.setDateEnd(activity.getDateEnd());
//                 activityResponse.setHourStart(activity.getHourStart());
//                 activityResponse.setHourEnd(activity.getHourEnd());

//                 Mockito.when(activityMapper.mapActivityRequestToActivity(activityRequest)).thenReturn(activity);
//                 Mockito.when(validationService.validActivityOrError(activity)).thenThrow(new ActivityIntervalException(dateTimeStart.toLocalDate(), dateTimeEnd.toLocalDate(), ActivityIntervalType.DATE));

//                 assertThrows(ActivityIntervalException.class, () -> {
//                     activityService.handleActivityRequest(activityRequest);
//                 });
//             }
//         }

//         @Nested
//         public class WhenTheParamDateStartIsEqualDateEndButHourEndIsBeforeHourStart {
//             @Test
//             public void shouldThrowActivityIntervalTimeException() {
//                 dateTimeStart = LocalDateTime.now();
//                 dateTimeEnd = dateTimeStart.minusSeconds(10);
//                 activityRequest.setDateStart(dateTimeStart.toLocalDate());
//                 activityRequest.setDateEnd(dateTimeEnd.toLocalDate());
//                 activityRequest.setHourStart(dateTimeStart.toLocalTime());
//                 activityRequest.setHourEnd(dateTimeEnd.toLocalTime());
               
//                 activity.setDateStart(Date.valueOf(activityRequest.getDateStart()));
//                 activity.setDateEnd(Date.valueOf(activityRequest.getDateEnd()));
//                 activity.setHourStart(Time.valueOf(activityRequest.getHourStart()));
//                 activity.setHourEnd(Time.valueOf(activityRequest.getHourStart()));

//                 activityResponse.setDateStart(activity.getDateStart());
//                 activityResponse.setDateEnd(activity.getDateEnd());
//                 activityResponse.setHourStart(activity.getHourStart());
//                 activityResponse.setHourEnd(activity.getHourEnd());

//                 Mockito.when(activityMapper.mapActivityRequestToActivity(activityRequest)).thenReturn(activity);
//                 Mockito.when(validationService.validActivityOrError(activity))
//                     .thenThrow(new ActivityIntervalException(dateTimeStart.toLocalTime(), dateTimeEnd.toLocalTime(), ActivityIntervalType.TIME));

//                 assertThrows(ActivityIntervalException.class, () -> {
//                     activityService.handleActivityRequest(activityRequest);
//                 });
//             }
//         }

//         @Nested
//         public class WhenTheParamRepeatIsTrueButRepeatUntilIsNull {
//             @Test
//             public void shouldThrowGroupActivityFieldException() {
//                 dateTimeStart = LocalDateTime.now();
//                 dateTimeEnd = LocalDateTime.now().plusHours(2);
//                 activityRequest.setDateStart(dateTimeStart.toLocalDate());
//                 activityRequest.setDateEnd(dateTimeEnd.toLocalDate());
//                 activityRequest.setHourStart(dateTimeStart.toLocalTime());
//                 activityRequest.setHourEnd(dateTimeEnd.toLocalTime());
//                 activityRequest.setRepeat(true);
//                 activityRequest.setDaysToRepeat(List.of(1,3,5));
               
//                 activity.setDateStart(Date.valueOf(activityRequest.getDateStart()));
//                 activity.setDateEnd(Date.valueOf(activityRequest.getDateEnd()));
//                 activity.setHourStart(Time.valueOf(activityRequest.getHourStart()));
//                 activity.setHourEnd(Time.valueOf(activityRequest.getHourStart()));

//                 Mockito.when(activityMapper.mapActivityToActivityResponse(activity)).thenThrow(new GroupActivityException(GroupActivityType.FIELD));
//                 Throwable exception = assertThrows(GroupActivityException.class, () -> {
//                     activityService.handleActivityRequest(activityRequest);
//                 });

//                 assertEquals("'repeat' field is true. Check if both 'repeatUntil' and 'daysToRepeat' fields are filled correctly.", exception.getMessage());
//             }
//         }

//         @Nested
//         public class WhenTheParamRepeatIsTrueButDaysToRepeatIsEmpty {
//             @Test
//             public void shouldThrowGroupActivityFieldException() {
//                 dateTimeStart = LocalDateTime.now();
//                 dateTimeEnd = LocalDateTime.now().plusHours(2);
//                 activityRequest.setDateStart(dateTimeStart.toLocalDate());
//                 activityRequest.setDateEnd(dateTimeEnd.toLocalDate());
//                 activityRequest.setHourStart(dateTimeStart.toLocalTime());
//                 activityRequest.setHourEnd(dateTimeEnd.toLocalTime());
//                 activityRequest.setRepeat(true);
//                 activityRequest.setRepeatUntil(dateTimeStart.plusDays(10).toLocalDate());
//                 activityRequest.setDaysToRepeat(List.of());
               
//                 activity.setDateStart(Date.valueOf(activityRequest.getDateStart()));
//                 activity.setDateEnd(Date.valueOf(activityRequest.getDateEnd()));
//                 activity.setHourStart(Time.valueOf(activityRequest.getHourStart()));
//                 activity.setHourEnd(Time.valueOf(activityRequest.getHourStart()));

//                 Mockito.when(activityMapper.mapActivityToActivityResponse(activity)).thenThrow(new GroupActivityException(GroupActivityType.FIELD));
//                 Throwable exception = assertThrows(GroupActivityException.class, () -> {
//                     activityService.handleActivityRequest(activityRequest);
//                 });

//                 assertEquals("'repeat' field is true. Check if both 'repeatUntil' and 'daysToRepeat' fields are filled correctly.", exception.getMessage());
//             }
//         }

//         @Nested
//         public class WhenTheParamRepeatIsTrueButRepeatUntilIsBeforeFirstActivityDateStart {
//             @Test
//             public void shouldThrowGroupActivityIntervalException() {
//                 dateTimeStart = LocalDateTime.now();
//                 dateTimeEnd = LocalDateTime.now().plusHours(2);
//                 activityRequest.setDateStart(dateTimeStart.toLocalDate());
//                 activityRequest.setDateEnd(dateTimeEnd.toLocalDate());
//                 activityRequest.setHourStart(dateTimeStart.toLocalTime());
//                 activityRequest.setHourEnd(dateTimeEnd.toLocalTime());
//                 activityRequest.setRepeat(true);
//                 activityRequest.setRepeatUntil(dateTimeStart.minusDays(2).toLocalDate());
//                 activityRequest.setDaysToRepeat(List.of(1,3,5));
               
//                 activity.setDateStart(Date.valueOf(activityRequest.getDateStart()));
//                 activity.setDateEnd(Date.valueOf(activityRequest.getDateEnd()));
//                 activity.setHourStart(Time.valueOf(activityRequest.getHourStart()));
//                 activity.setHourEnd(Time.valueOf(activityRequest.getHourStart()));

//                 Mockito.when(activityMapper.mapActivityToActivityResponse(activity)).thenThrow(new GroupActivityException(GroupActivityType.INTERVAL));
//                 Throwable exception = assertThrows(GroupActivityException.class, () -> {
//                     activityService.handleActivityRequest(activityRequest);
//                 });

//                 assertEquals("'repeatUntil' date occurs before the first activity date. Please try again.", exception.getMessage());
//             }
//         }

//         @Nested
//         public class WhenTheParamRepeatIsTrueButDaysToRepeatHasValueLesserThanOne {
//             @Test
//             public void shouldThrowGroupActivityDayIndexException() {
//                 dateTimeStart = LocalDateTime.now();
//                 dateTimeEnd = LocalDateTime.now().plusHours(2);
//                 activityRequest.setDateStart(dateTimeStart.toLocalDate());
//                 activityRequest.setDateEnd(dateTimeEnd.toLocalDate());
//                 activityRequest.setHourStart(dateTimeStart.toLocalTime());
//                 activityRequest.setHourEnd(dateTimeEnd.toLocalTime());
//                 activityRequest.setRepeat(true);
//                 activityRequest.setRepeatUntil(dateTimeEnd.plusDays(5).toLocalDate());
//                 activityRequest.setDaysToRepeat(List.of(-2,3,5));
               
//                 activity.setDateStart(Date.valueOf(activityRequest.getDateStart()));
//                 activity.setDateEnd(Date.valueOf(activityRequest.getDateEnd()));
//                 activity.setHourStart(Time.valueOf(activityRequest.getHourStart()));
//                 activity.setHourEnd(Time.valueOf(activityRequest.getHourStart()));

//                 Mockito.when(activityMapper.mapActivityToActivityResponse(activity)).thenThrow(new GroupActivityException(GroupActivityType.DAY_INDEX));
//                 Throwable exception = assertThrows(GroupActivityException.class, () -> {
//                     activityService.handleActivityRequest(activityRequest);
//                 });

//                 assertEquals("'daysToRepeat' has a number either lesser than one or greater than seven. Please try again.", exception.getMessage());
//             }
//         }

//         @Nested
//         public class WhenTheParamRepeatIsTrueButDaysToRepeatHasValueGreaterThanSeven {
//             @Test
//             public void shouldThrowGroupActivityDayIndexException() {
//                 dateTimeStart = LocalDateTime.now();
//                 dateTimeEnd = LocalDateTime.now().plusHours(2);
//                 activityRequest.setDateStart(dateTimeStart.toLocalDate());
//                 activityRequest.setDateEnd(dateTimeEnd.toLocalDate());
//                 activityRequest.setHourStart(dateTimeStart.toLocalTime());
//                 activityRequest.setHourEnd(dateTimeEnd.toLocalTime());
//                 activityRequest.setRepeat(true);
//                 activityRequest.setRepeatUntil(dateTimeEnd.plusDays(5).toLocalDate());
//                 activityRequest.setDaysToRepeat(List.of(9,3,5));
               
//                 activity.setDateStart(Date.valueOf(activityRequest.getDateStart()));
//                 activity.setDateEnd(Date.valueOf(activityRequest.getDateEnd()));
//                 activity.setHourStart(Time.valueOf(activityRequest.getHourStart()));
//                 activity.setHourEnd(Time.valueOf(activityRequest.getHourStart()));

//                 Mockito.when(activityMapper.mapActivityToActivityResponse(activity)).thenThrow(new GroupActivityException(GroupActivityType.DAY_INDEX));
//                 Throwable exception = assertThrows(GroupActivityException.class, () -> {
//                     activityService.handleActivityRequest(activityRequest);
//                 });

//                 assertEquals("'daysToRepeat' has a number either lesser than one or greater than seven. Please try again.", exception.getMessage());
//             }
//         }
   
//         @Nested
//         public class CreatingGroupActivityWhenParamsAreOk {
//             @Test
//             public void shouldReturnActivityAndBeCalledThreeTimes() {
//                 dateTimeStart = LocalDateTime.of(2023, 6, 10, 20, 5);
//                 dateTimeEnd = dateTimeStart.plusHours(2);
//                 activityRequest.setDateStart(dateTimeStart.toLocalDate());
//                 activityRequest.setDateEnd(dateTimeEnd.toLocalDate());
//                 activityRequest.setHourStart(dateTimeStart.toLocalTime());
//                 activityRequest.setHourEnd(dateTimeEnd.toLocalTime());
//                 activityRequest.setRepeat(true);
//                 activityRequest.setRepeatUntil(dateTimeEnd.plusDays(3).toLocalDate());
//                 activityRequest.setDaysToRepeat(List.of(7,1));
               
//                 activity.setDateStart(Date.valueOf(activityRequest.getDateStart()));
//                 activity.setDateEnd(Date.valueOf(activityRequest.getDateEnd()));
//                 activity.setHourStart(Time.valueOf(activityRequest.getHourStart()));
//                 activity.setHourEnd(Time.valueOf(activityRequest.getHourStart()));

//                 activityResponse.setDateStart(activity.getDateStart());
//                 activityResponse.setDateEnd(activity.getDateEnd());
//                 activityResponse.setHourStart(activity.getHourStart());
//                 activityResponse.setHourEnd(activity.getHourEnd());

//                 GroupActivity groupActivity = new GroupActivity(1L);

//                 Mockito.when(activityMapper.mapActivityRequestToActivity(activityRequest)).thenReturn(activity);
//                 Mockito.when(groupActivityService.createGroupActivity(Mockito.any(GroupActivity.class))).thenReturn(groupActivity);
//                 Mockito.when(validationService.validActivityOrError(Mockito.any())).thenReturn(Mockito.any());
//                 Mockito.when(activityMapper.mapActivityToActivityResponse(activity)).thenReturn(activityResponse);
                
//                 ActivityResponse response = activityService.handleActivityRequest(activityRequest);
//                 assertEquals(1L, response.getId());
//                 assertEquals("Activity Test", response.getName());
//                 assertEquals(2L, response.getDependentId());
//                 assertEquals(1L, response.getCurrentGuardianId());
//                 assertEquals(2L, response.getActorId());
//                 assertEquals(1L, response.getCreatedById());
//                 verify(activityRepository, times(3)).save(Mockito.any());
//             }
//         }
//     }
// }

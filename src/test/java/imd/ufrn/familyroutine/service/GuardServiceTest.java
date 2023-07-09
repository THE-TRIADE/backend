package imd.ufrn.familyroutine.service;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import imd.ufrn.familyroutine.model.api.GuardMapper;
import imd.ufrn.familyroutine.repository.GuardRepository;

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
    public class DeleteAllGuards {
        @Test
        public void shouldDeleteAllGuards() {
            guardService.deleteAllGuards();

            verify(guardRepository, times(1)).deleteAll();
        }
    }
}

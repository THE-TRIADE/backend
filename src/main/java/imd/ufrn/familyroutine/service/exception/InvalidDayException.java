package imd.ufrn.familyroutine.service.exception;

import imd.ufrn.familyroutine.FamilyRoutineException;

public class InvalidDayException extends FamilyRoutineException {
    public InvalidDayException() {
    }

    public InvalidDayException(Throwable cause) {
        super(cause);
    }

    @Override
    public String getMessage() {
        return "Invalid DayOfWeek. Valid interval: [1,7]. 1 is Monday and 7 is Sunday.";
    }
  
}

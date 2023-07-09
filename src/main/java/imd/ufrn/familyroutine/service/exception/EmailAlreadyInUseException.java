package imd.ufrn.familyroutine.service.exception;

import imd.ufrn.familyroutine.FamilyRoutineException;

public class EmailAlreadyInUseException extends FamilyRoutineException {

    public EmailAlreadyInUseException() {
        
    }

    public EmailAlreadyInUseException (Throwable cause) {
        super(cause);
    }

    @Override
    public String getMessage() {
        return "Email already in use. Try again.";
    }
}
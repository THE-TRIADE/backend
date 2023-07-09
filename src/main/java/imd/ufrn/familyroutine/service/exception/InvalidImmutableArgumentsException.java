package imd.ufrn.familyroutine.service.exception;

import imd.ufrn.familyroutine.FamilyRoutineException;

public class InvalidImmutableArgumentsException extends FamilyRoutineException {
    private String nonModifiableAtributeOne;
    private String nonModifiableAtributeTwo;

    public InvalidImmutableArgumentsException (String nonModifiableAtributeOne, String nonModifiableAtributeTwo) {
        this.nonModifiableAtributeOne = nonModifiableAtributeOne;
        this.nonModifiableAtributeTwo = nonModifiableAtributeTwo;
    }

    @Override
    public String getMessage() {
        return "Atributes: " + nonModifiableAtributeOne + ", and " + nonModifiableAtributeTwo + " are invalid";
    }

}

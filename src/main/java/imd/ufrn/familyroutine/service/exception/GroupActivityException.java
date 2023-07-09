package imd.ufrn.familyroutine.service.exception;

import imd.ufrn.familyroutine.FamilyRoutineException;

public class GroupActivityException extends FamilyRoutineException {
    private GroupActivityType type;

    public GroupActivityException (GroupActivityType type) {
        this.type = type;
    }

    @Override
    public String getMessage() {
        String message = "";

        if(type == GroupActivityType.FIELD) {
            message += "'repeat' field is true. Check if both 'repeatUntil' and 'daysToRepeat' fields are filled correctly.";
        }
        else if (type == GroupActivityType.DAY_INDEX) {
            message += "'daysToRepeat' has a number either lesser than one or greater than seven. Please try again.";
        }
        else if (type == GroupActivityType.INTERVAL) {
            message += "'repeatUntil' date occurs before the first activity date. Please try again.";
        }
        
        return message;
    }
    
}

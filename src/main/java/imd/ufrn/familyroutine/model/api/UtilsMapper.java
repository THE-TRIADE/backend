package imd.ufrn.familyroutine.model.api;

import java.sql.Date;
import java.sql.Time;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public abstract class UtilsMapper {
    
    public Time localTimeToTime(LocalTime localTime) {
        return Time.valueOf(localTime);
    }

    public LocalDate dateToLocalDate(Date date) {
        return date.toLocalDate();
    }

    public List<DayOfWeek> listIntegerToListDayOfWeek(List<Integer> daysOfWeek) {
        if(daysOfWeek == null){
            return null;
        }
        return daysOfWeek.stream().map(DayOfWeek::of).toList();
    }

    public List<Integer> listDayOfWeekToListInteger(List<DayOfWeek> daysOfWeek) {
        if(daysOfWeek == null){
            return null;
        }
        return daysOfWeek.stream().map(DayOfWeek::getValue).toList();
    }
}

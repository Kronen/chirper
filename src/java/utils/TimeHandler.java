package utils;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

/**
 *
 * @author Alberto G. Lagos
 */
@ManagedBean
@RequestScoped
public class TimeHandler {

    public TimeHandler() {
    }
    
    public String elapsed(Date date) {
        
        LocalDateTime fromDateTime = LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault());
        LocalDateTime toDateTime = LocalDateTime.ofInstant(new Date().toInstant(), ZoneId.systemDefault());
        
        String diff = "";
        long days = fromDateTime.until(toDateTime, ChronoUnit.DAYS);
        long hours = fromDateTime.until(toDateTime, ChronoUnit.HOURS);
        long minutes = fromDateTime.until(toDateTime, ChronoUnit.MINUTES);

        if(days > 0) diff = days + " d"; 
        else if(hours > 0) diff = hours + " h";
        else diff = minutes + " m";
            
        return diff;
    }
    
}

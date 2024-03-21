package Data;

import java.time.Duration;
import java.time.LocalDateTime;

/**
 * The type Recurring alert.
 */
public class RecurringAlert extends Alert{

    /**
     * Instantiates a new Recurring alert.
     *
     * @param eventID   the event id
     * @param eventName the event name
     * @param eventTime the event time
     * @param frequency the frequency
     */
    public RecurringAlert(Integer eventID, String eventName, LocalDateTime eventTime, Duration frequency) {
        super(eventID, eventName, eventTime, RecurringAlert.getTrueFrequency(eventTime, frequency));
        this.setGoOffTime();
        timer.scheduleAtFixedRate(this,java.sql.Timestamp.valueOf(getGoOffTime()),10000);
    }


    public static Duration getTrueFrequency(LocalDateTime eventTime, Duration frequency){
        Duration d = Duration.between(eventTime, LocalDateTime.now());
        if (d.compareTo(frequency) < 0){
            return frequency;
        }
        Duration trueFrequency = Duration.ZERO;
        while (d.compareTo(frequency) >= 0){
            d = d.minus(frequency);
            trueFrequency = trueFrequency.plus(frequency);
        }
        return trueFrequency;
    }

    public void setGoOffTime(){
        Duration d = getTrueFrequency(eventTime, frequency);
        goOffTime = eventTime.minus(d);
        if (goOffTime.compareTo(LocalDateTime.now())<=0){
            on = false;
        }
    }

    @Override
    public String goOff() {
        setGoOffTime();
        return toString();
    }

}

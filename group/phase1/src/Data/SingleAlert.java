package Data;

import java.time.Duration;
import java.time.LocalDateTime;

/**
 * The type Single alert.
 */
public class SingleAlert extends Alert{

//    private String eventId;
//
//    private LocalDateTime goOffTime;
//
//    private boolean on = true;
//
//    private Duration frequency;

//    private String eventName;

//    private LocalDateTime eventTime;


    /**
     * Instantiates a new Single alert.
     *
     * @param eventId   the event id
     * @param eventName the event name
     * @param eventTime the event time
     * @param frequency the go off time
     */
    public SingleAlert(Integer eventId, String eventName, LocalDateTime eventTime, Duration frequency) {
        super(eventId, eventName, eventTime, frequency);
        this.setGoOffTime();
        timer.scheduleAtFixedRate(this,java.sql.Timestamp.valueOf(getGoOffTime()),10000);
    }

    @Override
    public void setGoOffTime() {
        this.goOffTime = eventTime.minus(frequency);
    }


    @Override
    public String goOff() {
        this.on = false;
        return toString();
    }

}

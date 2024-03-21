package Data;

import java.time.LocalDateTime;
import java.time.Duration;
import java.time.Period;
import java.util.Objects;
import java.util.TimerTask;
import java.util.Timer;


/**
 * The type Alert.
 */
public abstract class Alert extends TimerTask implements Comparable<Alert>{

    /**
     * The Timer.
     */
    Timer timer = new Timer();
    /**
     * The Event id.
     */
    Integer eventId;

    /**
     * The Go off time.
     */
    LocalDateTime goOffTime;

    /**
     * The On.
     */
    boolean on = true;


    /**
     * The Frequency.
     */
    Duration frequency;

    /**
     * The Event name.
     */
    String eventName;

    /**
     * The Event time.
     */
    LocalDateTime eventTime;

    /**
     * The Minutes per hour.
     */
    static final int MINUTES_PER_HOUR = 60;
    /**
     * The Seconds per minute.
     */
    static final int SECONDS_PER_MINUTE = 60;
    /**
     * The Seconds per hour.
     */
    static final int SECONDS_PER_HOUR = SECONDS_PER_MINUTE * MINUTES_PER_HOUR;


    /**
     * Instantiates a new Alert.
     *
     * @param eventId   the event id
     * @param eventName the event name
     * @param eventTime the event time
     * @param frequency the frequency
     */
    public Alert(Integer eventId, String eventName, LocalDateTime eventTime, Duration frequency){
        this.eventId = eventId;
        this.eventName = eventName;
        this.eventTime = eventTime;
        this.frequency = frequency;
    }

    /**
     * Sets event name.
     *
     * @param eventName the event name
     */
    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    /**
     * Sets event time.
     *
     * @param eventTime the event time
     */
    public void setEventTime(LocalDateTime eventTime) {
        this.eventTime = eventTime;
        setGoOffTime();
    }


    /**
     * Gets go off time.
     *
     * @return the go off time
     */
    public LocalDateTime getGoOffTime() {
        return goOffTime;
    }


    /**
     * Disable alert.
     */
    public void disableAlert() {
        this.on = false;
    }

    /**
     * Check status boolean.
     *
     * @return the boolean
     */
    public boolean checkStatus(){
        return this.on;
    }

    /**
     * Get event id string.
     *
     * @return the string
     */
    public Integer getEventId(){return this.eventId;}

    /**
     * Get duration duration.
     *
     * @return the duration
     */
    public Duration getDuration(){return this.frequency;}


    /**
     * Sets go off time.
     */
    public abstract void setGoOffTime();

    /**
     * Gets time left.
     *
     * @return the time left
     */
    public String getTimeLeft() {
        LocalDateTime currentTime = LocalDateTime.now();
        Period period = Period.between(currentTime.toLocalDate(), goOffTime.toLocalDate());
        long[] time = getTime(currentTime, goOffTime);

        return (period.getYears() + " years " +
                period.getMonths() + " months " +
                period.getDays() + " days " +
                time[0] + " hours " +
                time[1] + " minutes " +
                time[2] + " seconds. ");
    }

    /**
     * Gets event name.
     *
     * @return the event name
     */
    public String getEventName() {
        return eventName;
    }

    private static long[] getTime(LocalDateTime dob, LocalDateTime now) {
        LocalDateTime today = LocalDateTime.of(now.getYear(),
                now.getMonthValue(), now.getDayOfMonth(), dob.getHour(), dob.getMinute(), dob.getSecond());
        Duration duration = Duration.between(today, now);

        long seconds = duration.getSeconds();

        long hours = seconds / SECONDS_PER_HOUR;
        long minutes = ((seconds % SECONDS_PER_HOUR) / SECONDS_PER_MINUTE);
        long secs = (seconds % SECONDS_PER_MINUTE);

        return new long[]{hours, minutes, secs};
    }


    public int compareTo(Alert alert){
        return this.goOffTime.compareTo(alert.getGoOffTime());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Alert)) return false;
        Alert alert = (Alert) o;
        return on == alert.on &&
                Objects.equals(eventId, alert.eventId) &&
                Objects.equals(getGoOffTime(), alert.getGoOffTime()) &&
                Objects.equals(frequency, alert.frequency);
    }

    @Override
    public String toString() {
        return " Event " + eventName + ' ' +
                "at " + eventTime.toString() + " will trigger an alert in " + getTimeLeft(); }

    /**
     * Go off string.
     *
     * @return the string
     */
    abstract public String goOff();

    public void run(){
        goOff();
    }

}
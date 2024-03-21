package Data;

import com.sun.xml.internal.txw2.output.DumpSerializer;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Observable;

/**
 * The type Data.Event.
 */
public class Event implements Comparable<Event>{

    private String name;

    private java.time.LocalDateTime startTime;

    private java.time.LocalDateTime endTime;

    private Integer eid;

    private ArrayList<String> calendars;

    private ArrayList<String> users;

    protected AlertManager alertManager;

    /**
     * Instantiates a new Data.Event.
     *
     * @param name      the name
     * @param startTime the start time
     * @param endTime   the end time
     * @param eid       the eid
     */
    public Event(String name, LocalDateTime startTime, LocalDateTime endTime, Integer eid) {
        this.name = name;
        this.startTime = startTime;
        this.endTime = endTime;
        this.eid = eid;
        this.alertManager = new AlertManager(eid, name, startTime);
        this.calendars = new ArrayList<>();
        this.users = new ArrayList<>();
    }

//    public Event(String name, Integer eid) {
//        this.name = name;
//        this.eid = eid;
//        this.alertManager = new AlertManager(eid, name);
//        this.startTime = LocalDateTime.MAX;
//        this.endTime = LocalDateTime.MAX;
//    }

    public void addUser(String user){
        this.users.add(user);
    }

    public ArrayList<String> getUsers(){
        return this.users;
    }

    /**
     * add the calendars to this event.
     *
     * @param calendar the name of calendar.
     */
    public void addCalendar(String calendar){
        this.calendars.add(calendar);
    }

    /**
     * return the calendars of this event.
     *
     * @return calendars
     */
    public ArrayList<String> getCalendars(){
        return this.calendars;
    }

    /**
     * Sets name.
     *
     * @param name the name
     */
    public void setName(String name) {
        this.name = name;

    }

    /**
     * Sets start time.
     *
     * @param startTime the start time
     */
    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    /**
     * Sets end time.
     *
     * @param endTime the end time
     */
    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    /**
     * Gets name.
     *
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Gets start time.
     *
     * @return the start time
     */
    public LocalDateTime getStartTime() {
        return startTime;
    }

    /**
     * Gets end time.
     *
     * @return the end time
     */
    public LocalDateTime getEndTime() {
        return endTime;
    }

    /**
     * Gets eid.
     *
     * @return the eid
     */
    public Integer getEid() {
        return eid;
    }

    public int compareTo(Event event){
        return startTime.compareTo(event.startTime);
    }

    public String calendarsToString() {
        StringBuilder sb = new StringBuilder();
        for (String c : calendars) {
            sb.append(c);
            sb.append(",");
        }
        if(sb.length() >= 1) {
            sb.deleteCharAt(sb.length() - 1);
        }
        return sb.toString();
    }


    public String toString() {
        return name + "\n" + startTime + "\n" + endTime + "\n" + "In calendars: " + calendarsToString();
    }
}
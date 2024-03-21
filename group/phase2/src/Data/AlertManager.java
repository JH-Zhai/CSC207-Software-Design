package Data;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAmount;
import java.util.List;
import java.util.PriorityQueue;

/**
 * The type Alert manager.
 */
public class AlertManager {

    private Integer eventId;
    private String eventName;
    private LocalDateTime eventTime;


    public AlertManager(Integer eventId, String eventName, LocalDateTime eventTime){
        this.eventId = eventId;
        this.eventName = eventName;
        this.eventTime = eventTime;
    }

    public AlertManager(Integer eventId, String eventName){
        this.eventId = eventId;
        this.eventName = eventName;
    }

    /**
     * Create alert and add to the alertQueue;
     *
     * @param repeat the repeat
     */
    public boolean createAlert(DataBase dataBase, boolean repeat, Duration duration){

        Alert alert;
        if (repeat){
            alert = new RecurringAlert(eventId, eventName, eventTime, duration);
        } else{
            alert = new SingleAlert(eventId, eventName, eventTime, duration); }

        for (Alert a : dataBase.alerts){
            if (a.equals(alert)){
                return false;
            }
        }
        dataBase.alerts.add(alert);
        return true;

    };


    /**
     * Call setGoOffTime in class Alert to update time when Event time changes.
     *
     *
     */
    public void updateEventName(DataBase dataBase, Integer eventId, String name){
        for (Alert alert: dataBase.alerts){
            if (alert.eventId.equals(eventId)) {
            alert.setEventName(name);}
        }
    }

    public void updateEventTime(DataBase dataBase, Integer eventId, LocalDateTime time){
        for (Alert alert: dataBase.alerts){
            if (alert.eventId.equals(eventId)) {
                alert.setEventTime(time);}
        }
    }


//    public void GoOffTAlerts(DataBase dataBase){
//        while (!dataBase.alerts.isEmpty()){
//            Alert temp = dataBase.alerts.remove();
//            while (temp != null){
//                if (temp.getGoOffTime().equals(LocalDateTime.now())){
//                    System.out.println(temp.goOff());
//                    temp = null;
//                }
//            }
//        }
//    }
}
package Data;
import java.lang.reflect.Array;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.PriorityQueue;

/**
 * The type Event manager.
 */
public class EventManager {
    private static Integer counter = 0;


    /**
     * Create event boolean.
     *
     * @param dataBase  the data base
     * @param title     the title
     * @param startTime the start time
     * @param endTime   the end time
     * @return the boolean
     */
    public boolean createEvent(DataBase dataBase, String title, java.time.LocalDateTime startTime, java.time.LocalDateTime endTime){
        for(Event event: dataBase.events){
            if (event.getName().equals(title)){
                System.out.println("Event existed, please continue with the old event.");
                return false;
            }
        }
        Integer newId = counter;
        counter ++;
        Event newEvent = new Event(title, startTime, endTime, newId);
        dataBase.events.add(newEvent);
        return true;
    }

    public boolean readEvent(DataBase dataBase, String title, java.time.LocalDateTime startTime, java.time.LocalDateTime endTime, Integer eid){
        for(Event event: dataBase.events){
            if (event.getName().equals(title)){
                System.out.println("Event existed, please continue with the old event.");
                return false;
            }
        }
        Event newEvent = new Event(title, startTime, endTime, eid);
        dataBase.events.add(newEvent);
        if(counter <= eid){
            counter = eid + 1;
        }
        return true;
    }

    public Event createEventReturnEvent(DataBase dataBase, String title, java.time.LocalDateTime startTime, java.time.LocalDateTime endTime){
        for(Event event: dataBase.events){
            if (event.getName().equals(title)){
                System.out.println("Event existed, please continue with the old event.");
                return event;
            }
        }
        Integer newId = counter++;
        Event newEvent = new Event(title, startTime, endTime, newId);
        dataBase.events.add(newEvent);
        return newEvent;
    }

    public boolean createAlert(DataBase dataBase, boolean repeat, Duration duration, Event event){
        return event.alertManager.createAlert(dataBase, repeat, duration);
    }

//    public boolean createAlert(DataBase dataBase, boolean repeat, Duration duration, Integer eventId){
//        for(Event event: dataBase.events){
//            if (event.getEid().equals(eventId)){
//                return event.alertManager.createAlert(dataBase, repeat, duration);
//            }
//        }
//        return false;
//    }

    public void goOffAlerts(DataBase dataBase){
        while (!dataBase.alerts.isEmpty()){
            Alert temp = dataBase.alerts.remove();
            while (temp != null){
                if (temp.getGoOffTime().equals(LocalDateTime.now())){
                    System.out.println(temp.goOff());
                    temp = null; }
            }
        }
    }

    public void displayAllAlerts(DataBase dataBase){
        for(Alert alert: dataBase.alerts){
            System.out.println(alert + "\n");
        }
    }


    public PriorityQueue<Alert> getAlertsByEvent(DataBase dataBase, String name){
        PriorityQueue<Alert> aQueue = new PriorityQueue<Alert>();
        for(Alert alert: dataBase.alerts){
            if (alert.getEventName().equals(name)){
                aQueue.add(alert);
            }
        }
        return aQueue;
    }

    public boolean updateEventName(DataBase dataBase, Integer eventId, String name){
        for(Event event: dataBase.events){
            if (event.getEid().equals(eventId)){
                event.setName(name);
                event.alertManager.updateEventName(dataBase, eventId, name);
                return true;
            }
        }
        return false;
    }

    public boolean updateEventTime(DataBase dataBase, Integer eventId, LocalDateTime time){
        for(Event event: dataBase.events){
            if (event.getEid().equals(eventId)){
                event.setStartTime(time);
                event.alertManager.updateEventTime(dataBase, eventId, time);
                return true;
            }
        }
        return false;
    }

    /**
     * Create event boolean.
     *
     * @param dataBase  the data base
     * @param title     the title
     * @param startTime the start time
     * @param endTime   the end time
     * @param eid       the eid
     * @return the boolean
     */
    public Event createEvent(DataBase dataBase, String title, java.time.LocalDateTime startTime, java.time.LocalDateTime endTime, Integer eid){
        for(Event event: dataBase.events){
            if (event.getName().equals(title)){
                System.out.println("Event existed, please continue with the old event.");
                return event;
            }
        }
        Event newEvent = new Event(title, startTime, endTime, eid);
        dataBase.events.add(newEvent);
        return newEvent;
    }

    /**
     * Create multiple events array list.
     *
     * @param dataBase  the data base
     * @param name      the name
     * @param startTime the start time
     * @param endTime   the end time
     * @param frequency the frequency
     * @param numbers   the numbers
     * @return the array list
     */
    public ArrayList<Event> createMultipleEvents(DataBase dataBase, String name, java.time.LocalDateTime startTime,
                                                        java.time.LocalDateTime endTime, long frequency, int numbers){
        int i = 1;
        ArrayList<Event> newEvents = new ArrayList<>();
        while (numbers > 0){
            Event newEvent = createEventReturnEvent(dataBase, name + String.valueOf(i), startTime, endTime);
            newEvents.add(newEvent);
            startTime = startTime.plusDays(frequency);
            endTime = endTime.plusDays(frequency);
            numbers -= 1;
            i += 1;
        }
        return newEvents;
    }


    /**
     * Search event by date array list.
     *
     * @param date the date
     * @return the array list
     */
    public ArrayList<Event> searchEventByDate(DataBase dataBase, java.time.LocalDate date) {
        ArrayList<Event> events = new ArrayList<>();
        for (Event e: dataBase.events) {
            java.time.LocalDate startDate = e.getStartTime().toLocalDate();
            java.time.LocalDate endDate = e.getEndTime().toLocalDate();
            if (startDate.isEqual(date) || endDate.isEqual(date) || (startDate.isBefore(date) && endDate.isAfter(date))) {
                events.add(e);
            }
        }
        return events;
    }

    /**
     * Search event by tag array list.
     *
     * @param tagName the tag name
     * @return the array list
     */
    public ArrayList<Event> searchEventByTag(DataBase dataBase, String tagName) {
        ArrayList<Event> res = new ArrayList<>();
        for (EventAndTag eventAndTag: dataBase.eventAndTag){
            if (eventAndTag.getTag().getTitle().equals(tagName)){
                res.add(eventAndTag.getEvent());
            }
        }
        return res;
    }

    /**
     * Search event by series name array list.
     *
     * @param seriesName the series name
     * @return the array list
     */
    public ArrayList<Event> searchEventBySeriesName(DataBase dataBase, String seriesName) {
        ArrayList<Event> res = new ArrayList<>();
        for (EventAndSeries eventAndSeries: dataBase.eventAndSeries){
            if (eventAndSeries.getSeries().getName().equals(seriesName)){
                res.add(eventAndSeries.getEvent());
            }
        }
        return res;
    }

    /**
     * Search event by event name array list.
     *
     * @param name the name
     * @return the array list
     */
    public Event searchEventByEventName(DataBase dataBase, String name) {
        for (Event event: dataBase.events){
            if (event.getName().equals(name)){
                return event;
            }
        }
        return null;
    }
    
    public Event getEventByEid(DataBase dataBase, Integer eid){
        for (Event event: dataBase.events){
            if (event.getEid().equals(eid)){
                return event;
            }
        }
        return null;
    }

//    public Event getEventByNameTime(DataBase dataBase, String name, LocalDateTime time){
//        for (Event event: dataBase.events){
//            if (event.getName().equals(name)&&event.getStartTime().equals(time)){
//                return event;
//            }
//        }
//        return null;
//    }


}
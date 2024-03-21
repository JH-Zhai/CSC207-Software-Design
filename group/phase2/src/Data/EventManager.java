package Data;
import com.sun.xml.internal.bind.v2.TODO;

import java.lang.reflect.Array;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.Year;
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


    public PriorityQueue<Alert> getAlertsByEvent(DataBase dataBase, Integer eid){
        PriorityQueue<Alert> aQueue = new PriorityQueue<Alert>();
        for(Alert alert: dataBase.alerts){
            if (alert.getEventId().equals(eid)){
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
    public PriorityQueue<Event> createMultipleEvents(DataBase dataBase, String name, java.time.LocalDateTime startTime,
                                                        java.time.LocalDateTime endTime, long frequency, int numbers){
        int i = 1;
        PriorityQueue<Event> newEvents = new PriorityQueue<>();
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

    public PriorityQueue<Event> searchEventByYear(DataBase dataBase, java.time.Year year){
        PriorityQueue<Event> events = new PriorityQueue<Event>();
        for(Event e: dataBase.events) {
            int endYear = e.getEndTime().getYear();
            int startYear = e.getStartTime().getYear();
            if (year.getValue() == endYear || year.getValue() == startYear) {
                events.add(e);
            }
        }
        return events;
    }

    public PriorityQueue<Event> searchEventByMonth(DataBase dataBase, java.time.Month month){
        PriorityQueue<Event> events = new PriorityQueue<Event>();
        for(Event e: dataBase.events) {
            Month endMonth = e.getEndTime().getMonth();
            Month startMonth = e.getStartTime().getMonth();
            if (month.equals(endMonth) || month.equals(startMonth)) {
                events.add(e);
            }
        }
        return events;
    }

    public PriorityQueue<Event> searchEventByTime(DataBase dataBase, LocalDateTime startTime){
        PriorityQueue<Event> events = new PriorityQueue<Event>();
        for (Event e: dataBase.events) {
            LocalDateTime time = e.getStartTime();
            if (startTime.isEqual(time)) {
                events.add(e);
            }
        }
        return events;
    }


    /**
     * Search event by date array list.
     *
     * @param date the date
     * @return the array list
     */
    public PriorityQueue<Event> searchEventsByDate(DataBase dataBase, java.time.LocalDate date) {
        PriorityQueue<Event> events = new PriorityQueue<Event>();
        for (Event e: dataBase.events) {
            java.time.LocalDate startDate = e.getStartTime().toLocalDate();
            java.time.LocalDate endDate = e.getEndTime().toLocalDate();
            if (startDate.isEqual(date) || endDate.isEqual(date) || (startDate.isBefore(date) && endDate.isAfter(date))) {
                events.add(e);
            }
        }
        return events;
    }

    public PriorityQueue<Event> searchEventFromDateToDate(DataBase dataBase, java.time.LocalDate date1, java.time.LocalDate date2){
        PriorityQueue<Event> events = new PriorityQueue<Event>();
        for (Event e: dataBase.events){
            java.time.LocalDate startDate = e.getStartTime().toLocalDate();
            java.time.LocalDate endDate = e.getEndTime().toLocalDate();
            if (startDate.isEqual(date1) || startDate.isEqual(date2) ||
                    (startDate.isAfter(date1) && startDate.isBefore(date2)) ||
                    (endDate.isAfter(date1) && endDate.isBefore(date2))){
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
    public PriorityQueue<Event> searchEventByTag(DataBase dataBase, String tagName) {
        PriorityQueue<Event> res = new PriorityQueue<Event>();
        for (EventAndTag eventAndTag: dataBase.eventAndTag){
            if (eventAndTag.getTag().getTitle().equals(tagName)){
                res.add(eventAndTag.getEvent());
            }
        }
        return res;
    }

    public PriorityQueue<Event> searchEventByCalendar(DataBase dataBase, String calendar){
        PriorityQueue<Event> events = new PriorityQueue<Event>();
        for (Event event : dataBase.events){
            if (event.getCalendars().contains(calendar)){
                events.add(event);
            }
        }
        return events;
    }

    public void addUser(Event event, String user){
        event.addUser(user);
    }

    public ArrayList<String> getUsers(Event event){
        return event.getUsers();
    }

    public void addCalendar(Event event, String calendar){
        if (!event.getCalendars().contains(calendar)){
            event.addCalendar(calendar);
        }
    }

    /**
     * Search event by series name array list.
     *
     * @param seriesName the series name
     * @return the array list
     */
    public PriorityQueue<Event> searchEventBySeriesName(DataBase dataBase, String seriesName) {
        PriorityQueue<Event> res = new PriorityQueue<Event>();
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
    public PriorityQueue<Event> searchEventsByName(DataBase dataBase, String name) {
        PriorityQueue<Event> events = new PriorityQueue<Event>();
        for (Event event: dataBase.events){
            if (event.getName().equals(name)){
                 events.add(event);
            }
        }
        return events;
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

    public void deleteEvent(DataBase dataBase, Event event){
        dataBase.events.removeIf(target -> event.getName().equals(target.getName()));
        dataBase.eventAndMemos.removeIf(target -> target.getEventId().equals(event.getEid()));
        dataBase.eventAndTag.removeIf(target -> target.getEventId().equals(event.getEid()));
        dataBase.eventAndSeries.removeIf(target -> target.getEventId().equals(event.getEid()));
        dataBase.alerts.removeIf(target -> event.getName().equals(target.eventName));
    }


    public void deleteMemoByEvent(DataBase dataBase, Event event){
        int eventId = event.getEid();
        dataBase.eventAndMemos.removeIf(eventAndMemo -> eventAndMemo.getEventId() == eventId);
    }

    public void deleteTags(DataBase dataBase, Event event){
        int eventId = event.getEid();
        dataBase.eventAndTag.removeIf(eventAndTag -> eventAndTag.getEventId() == eventId);
    }


    public void deleteAlerts(DataBase dataBase, Event event){
        int eventId = event.getEid();
        dataBase.alerts.removeIf(alert -> alert.getEventId() == eventId);
    }

    public void deleteSeries(DataBase dataBase, Event event, Series series){
        int eventId = event.getEid();
        int seriesId = series.getId();
        dataBase.eventAndSeries.removeIf(eventAndSeries -> eventAndSeries.getEventId() == eventId
                && eventAndSeries.getSeriesId() == seriesId);
    }



}
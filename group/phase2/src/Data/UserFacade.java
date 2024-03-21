package Data;



import java.time.*;
import java.util.ArrayList;
import java.util.PriorityQueue;

/**
 * The type User facade.
 */
public class UserFacade {
        private DataBase dataBase;
        private String userAccount;

    public UserFacade(String userAccount){
        this.userAccount = userAccount;
        this.dataBase = new DataBase();
    }

//    private String userPassword;
    private EventManager eventManager = new EventManager();
    private SeriesManager seriesManager = new SeriesManager();
    private TagManager tagManager = new TagManager();
    private MemoManager memoManager = new MemoManager();
    private TodoManager todoManager = new TodoManager();


    public String getUserAccount(){
        return userAccount;
    }


    /**
     * Add tag boolean.
     *
     * @param tag   the tag
     * @param event the event
     * @return the boolean
     */
    public boolean addTag(Tag tag, Event event){
        return tagManager.addTag(dataBase, event, tag);
    }

    /**
     * Add memo boolean.
     *
     * @param memo  the memo
     * @param event the event
     * @return the boolean
     */
    public boolean addMemo(Memo memo, Event event){
        return memoManager.addMemo(dataBase, event, memo);
    }

    /**
     * Create event boolean.
     *
     * @param title     the title
     * @param startTime the start time
     * @param endTime   the end time
     * @return the boolean
     */
    public boolean createEvent(String title, java.time.LocalDateTime startTime, java.time.LocalDateTime endTime){
        return eventManager.createEvent(dataBase, title, startTime, endTime);
    }

    public boolean readEvent(String title, java.time.LocalDateTime startTime, java.time.LocalDateTime endTime, Integer eid){
        return eventManager.readEvent(dataBase, title, startTime, endTime, eid);
    }

    /**
     * Create event return event event.
     *
     * @param title     the title
     * @param startTime the start time
     * @param endTime   the end time
     * @return the event
     */
    public Event createEventReturnEvent(String title, java.time.LocalDateTime startTime, java.time.LocalDateTime endTime){
        return eventManager.createEventReturnEvent(dataBase, title, startTime, endTime);
    }

    /**
     * Create tag boolean.
     *
     * @param title the title
     * @return the boolean
     */
    public boolean createTag(String title){
        return tagManager.createTag(dataBase, title);
    }

    public boolean readTag(String title, Integer id){
        return tagManager.readTag(dataBase, title, id);
    }

    /**
     * Create memo boolean.
     *
     * @param title the title
     * @return the boolean
     */
    public boolean createMemo(String title){
        return memoManager.createMemo(dataBase, title);
    }

    public boolean readMemo(String title, Integer id){
        return memoManager.readMemo(dataBase, title, id);
    }

    /**
     * Add content.
     *
     * @param memo    the memo
     * @param content the content
     */
    public void addContent(Memo memo, String content){
        memo.addContent(content);
    }

    /**
     * Create series boolean.
     *
     * @param name the name
     * @return the boolean
     */
    public Series createSeries(String name){
        return seriesManager.createSeries(dataBase, name);
    }

    public Boolean readSeries(String name, Integer id){
        return seriesManager.readSeries(dataBase, name, id);
    }

    public Series getSeriesByName(String seriesName){
        return seriesManager.getSeriesByName(dataBase, seriesName);
    }

    /**
     * Create alert boolean.
     * @param event  the event
     * @param repeat   the event
     * @param duration the duration
     * @return the boolean
     */
    public boolean createAlert(Event event, boolean repeat, Duration duration){
        return !eventManager.createAlert(dataBase, repeat, duration, event);
}

//    public Event getEventByNameTime(String name, LocalDateTime time){
//        return eventManager.getEventByNameTime(dataBase, name, time);
//    }

    /**
     * Update event name boolean.
     *
     * @param eventId the event id
     * @param name    the name
     * @return the boolean
     */
    public boolean updateEventName(Integer eventId, String name){
        return eventManager.updateEventName(dataBase, eventId, name);
    }

    /**
     * Update event time boolean.
     *
     * @param eventId      the event id
     * @param newStartTime the new start time
     * @return the boolean
     */
    public boolean updateEventTime(Integer eventId, LocalDateTime newStartTime){
        return eventManager.updateEventTime(dataBase, eventId, newStartTime);
    }

    /**
     * Get alerts by event priority queue.
     *
     * @param eid the event id
     * @return the priority queue
     */
    public PriorityQueue<Alert>  getAlertsByEvent(Integer eid){
        return eventManager.getAlertsByEvent(dataBase, eid);
    }



    public void goOffAlerts(){
        eventManager.goOffAlerts(dataBase);
    }

//    /**
//     * Display all alerts.
//     */
//    public void displayAllAlerts(){
//        eventManager.displayAllAlerts(dataBase);
//    }
    public PriorityQueue<Event> searchEventsByYear(Year year) {
        return eventManager.searchEventByYear(dataBase, year);
    }

    public PriorityQueue<Event> searchEventsByMonth(Month month) {
        return eventManager.searchEventByMonth(dataBase, month);
    }
    /**
     * Search by date array list.
     *
     * @param date the date
     * @return the array list
     */
    public PriorityQueue<Event> searchEventsByDate(LocalDate date){
        return eventManager.searchEventsByDate(dataBase, date);
    }

    public PriorityQueue<Event> searchEventsFromDateToDate(LocalDate date1, LocalDate date2){
        return eventManager.searchEventFromDateToDate(dataBase, date1, date2);
    }

    /**
     * Search by tag array list.
     *
     * @param tagTitle the tag title
     * @return the array list
     */
    public PriorityQueue<Event> searchEventsByTag(String tagTitle){
        return eventManager.searchEventByTag(dataBase, tagTitle);
    }

    /**
     * Search by series array list.
     *
     * @param seriresName the serires name
     * @return the array list
     */
    public PriorityQueue<Event> searchEventsBySeries(String seriresName){
        return eventManager.searchEventBySeriesName(dataBase, seriresName);
    }

    public PriorityQueue<Event> searchEventsByCalendar(String calendarName){
        return eventManager.searchEventByCalendar(dataBase, calendarName);
    }

    public void addCalendar(Event event, String calendar){
        eventManager.addCalendar(event, calendar);
    }

//    TODO haozhe, kuai
//    public void addUser(Event event, String userName){
//        return;
//    }
//
//    public ArrayList<String > getUsers(Event event){
//
//        return;
//    }

    /**
     * Search event by event name event.
     *
     * @param name the name
     * @return the event
     */
    public PriorityQueue<Event> searchEventsByName(String name){
        return eventManager.searchEventsByName(dataBase, name);
    }

    public PriorityQueue<Event> searchEventsByTime(LocalDateTime startTime){
        return eventManager.searchEventByTime(dataBase, startTime);
    }

    public Event searchEventsByNameTime(String name, LocalDateTime startTime){
        Event event = null;
        PriorityQueue<Event> eventsOfName = searchEventsByName(name);
        PriorityQueue<Event> eventsOfTime = searchEventsByTime(startTime);
        while (eventsOfName.size()!= 0){
            Event temp = eventsOfName.remove();
            if (eventsOfTime.contains(temp)){
                event = temp;
            }
        }
        return event;
    }

    public void displayALlSeries(){
        seriesManager.displayAllSeries(dataBase);
    }

    public ArrayList<Series> getAllSeries(){
        return dataBase.series;
    }

    /**
     * Link single event to series event and series.
     *
     * @param event  the event
     * @param series the series
     * @return the event and series
     */
    public boolean linkSingleEventToSeries(Event event, Series series){
        return seriesManager.linkSingleEventToSeries(dataBase, event, series);
    }

    /**
     * Link multiple events array list.
     *
     * @param events the events
     * @param series the series
     * @return the array list
     */
    public boolean linkMultipleEvents(PriorityQueue<Event> events, Series series){
        return seriesManager.linkMultipleEventsToSeries(dataBase, events, series);
    }

    public String seriesAssociatedEvents(Series series){
        return seriesManager.seriesAssociatedEvents(dataBase, series);
    }

    /**
     * Create multiple events array list.
     *
     * @param name      the name
     * @param startTime the startTime
     * @param endTime   the endTime
     * @param frequency the frequency
     * @param numbers   the numbers
     * @return the array list
     */
    public PriorityQueue<Event> createMultipleEvents(String name, LocalDateTime startTime, LocalDateTime endTime,
                                                 long frequency, int numbers){
         return eventManager.createMultipleEvents(dataBase, name, startTime, endTime, frequency, numbers);
    }



    /**
     * Get past events array list.
     *
     * @return the array list
     */
    public ArrayList<Event> getPastEvents(){
        ArrayList<Event> events = new ArrayList<>();

        for (Event event : dataBase.events){
            if (event.getEndTime().isBefore(java.time.LocalDateTime.now())){
                events.add(event);
            }
        }
        return events;
    }

    /**
     * Get current events array list.
     *
     * @return the array list
     */
    public PriorityQueue<Event> getCurrentEvents(){
        PriorityQueue<Event> events = getAllEvents();
        events.removeAll(getPastEvents());
        events.removeAll(getFutureEvents());
        return events;
    }

    /**
     * Get future events array list.
     *
     * @return the array list
     */
    public ArrayList<Event> getFutureEvents(){
        ArrayList<Event> events = new ArrayList<>();
        for (Event event : dataBase.events){
            if (event.getStartTime().isAfter(java.time.LocalDateTime.now())){
                events.add(event);
            }
        }
        return events;
    }

    /**
     * Get all events array list.
     *
     * @return the array list
     */
    public PriorityQueue<Event> getAllEvents(){
        return dataBase.events;
    }

    /**
     * Print events.
     *
     * @param events the events
     */
    public void printEvents(PriorityQueue<Event> events){
        for (Event event : events){
            System.out.println(event);
        }
    }

    public Event getEventByid(Integer id){
        return eventManager.getEventByEid(dataBase, id);

    }

    public Tag getTagByid(Integer id){
        return tagManager.getTagById(dataBase, id);

    }

    public Memo getMemoByid(Integer id){
        return memoManager.getMemoById(dataBase, id);

    }

    public Series getSeriesByid(Integer id){
        return seriesManager.getSeriesById(dataBase, id);

    }


    /**
     * Get events by memo array list.
     *
     * @param memo the memo
     * @return the array list
     */
    public ArrayList<Event> getEventsByMemo(Memo memo) {
        return memoManager.getEventsByMemo(dataBase, memo);
    }

    /**
     * Get memo by event memo.
     *
     * @param event the event
     * @return the memo
     */
    public Memo getMemoByEvent(Event event){
        return memoManager.getMemoByEvent(dataBase, event);
    }

    /**
     * Get memo by id memo.
     *
     * @param mid the mid
     * @return the memo
     */
    public Memo getMemoById(Integer mid){
        return memoManager.getMemoById(dataBase, mid);
    }

    /**
     * Get tag by id tag.
     *
     * @param tid the tid
     * @return the tag
     */
    public Tag getTagById(Integer tid){
        return tagManager.getTagById(dataBase, tid);
    }

    /**
     * Get series by id series.
     *
     * @param sid the sid
     * @return the series
     */
    public Series getSeriesById(Integer sid){
        return seriesManager.getSeriesById(dataBase, sid);
    }

    /**
     * Get all tag array list.
     *
     * @return the array list
     */
    public ArrayList<Tag> getAllTag(){
        return tagManager.getAllTag(dataBase);
    }

    /**
     * Display all memos.
     */
    public void displayAllMemos(){
        memoManager.displayAllMemos(dataBase);
    }

    /**
     * Get memo by title memo.
     *
     * @param memoTitle the memo title
     * @return the memo
     */
    public Memo getMemoByTitle(String memoTitle){
        return memoManager.getMemoByTitle(dataBase, memoTitle);
    }

    /**
     * Get tag by title tag.
     *
     * @param title the title
     * @return the tag
     */
    public Tag getTagByTitle(String title){
        return tagManager.getTagByTitle(dataBase, title);
    }

    public ArrayList<Tag> getTagsByEvent(Integer eid){
        return tagManager.getTagsByEvent(dataBase, eid);
    }

    public ArrayList<Memo> getAllMemo() {return dataBase.memos;}

    public ArrayList<Alert> getAllAlert() {return new ArrayList<Alert>(dataBase.alerts);}

    public ArrayList<EventAndSeries> getAllEventAndSeries(){
        return dataBase.eventAndSeries;
    }

    public ArrayList<EventAndTag> getAllEventAndTag() {
        return dataBase.eventAndTag;
    }

    public ArrayList<EventAndMemo> getAllEventAndMemo(){
        return dataBase.eventAndMemos;
    }

    public void deleteMemo(Event event){ eventManager.deleteMemoByEvent(dataBase, event); }

    public void deleteTags(Event event){ eventManager.deleteTags(dataBase, event);}

    public void deleteTag(Event event, Tag tag){ tagManager.deleteTag(dataBase, event, tag);}

    public void deleteAlerts(Event event){ eventManager.deleteAlerts(dataBase, event);}

    public void deleteEvent(Event event){ eventManager.deleteEvent(dataBase, event);}

    public void deleteSeriesEvent(Event event, Series series){ seriesManager.deleteEvent(dataBase, event, series);}

    public void deleteSeriesEvent(Series series){ seriesManager.deleteEvents(dataBase, series);}

    public void deleteEventSeries(Event event, Series series){ eventManager.deleteSeries(dataBase, event, series);}

    public boolean createTodoList(String header){
        return todoManager.createTodoList(dataBase, header);
    }

    public boolean createTodoItem(String content, Integer listId){
        return todoManager.createTodoItem(dataBase, content, listId);
    }

    public ArrayList<TodoList> getAllTodoList(){
        return dataBase.todoLists;
    }

    public ArrayList<TodoItem> getAllTodoItem(){
        return dataBase.todoItems;
    }

    public TodoList searchTodoListById(Integer listId){
        return todoManager.searchTodoListById(dataBase, listId);
    }

    public void showAllTodoItemInList(Integer listId){
        todoManager.showAllTodoItemInList(dataBase, listId);
    }


    public boolean readTodoItem(Integer tItemId, Integer todoListId, Integer eventId, String content, boolean checked){
        return todoManager.readTodoItem(dataBase, tItemId, todoListId, eventId, content, checked );
    }

    public boolean readTodoList(Integer todoListId, String header, ArrayList<Integer> ids){
        return todoManager.readTodoList(dataBase, todoListId, header, ids);
    }

    public boolean readEmptyTodoList(Integer todoListId, String header){
        return todoManager.readEmptyTodoList(dataBase, todoListId, header);
    }

}

package Presenters;

import Data.*;

import javax.swing.text.DateFormatter;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.PriorityQueue;
import java.util.Scanner;

/**
 * The type Event presenter.
 */
public class EventPresenter {

    private Scanner reader = new Scanner(System.in);
    private ArrayList<String> units = new ArrayList<String>(
            Arrays.asList("days",
                    "hours",
                    "minutes"));

    /**
     * Show all event.
     *
     * @param userFacade the user facade
     */
    public void showAllEvent(UserFacade userFacade) {
        for (Event event : userFacade.getAllEvents()) {
            System.out.println(event);
        }
    }

    /**
     * Show past event.
     *
     * @param userFacade the user facade
     */
    public void showPastEvent(UserFacade userFacade) {
        for (Event event : userFacade.getPastEvents()) {
            System.out.println(event);
        }
    }

    /**
     *
     * Show current event.
     *
     * @param userFacade the user facade
     */
    public void showCurrentEvent(UserFacade userFacade) {
        for (Event event : userFacade.getCurrentEvents()) {
            System.out.println(event);
        }
    }

    /**
     * Show future event.
     *
     * @param userFacade the user facade
     */
    public void showFutureEvent(UserFacade userFacade) {
        for (Event event : userFacade.getFutureEvents()) {
            System.out.println(event);
        }
    }

    /**
     * Add alert to event.
     *
     * @param userFacade the user facade
     * @param event      the event
     */
    public void addAlertToEvent(UserFacade userFacade, Event event){
        LocalDateTime eventTime;
        eventTime = event.getStartTime();

        System.out.println("You are creating an alert for event named "+event.getName() + " held at " + eventTime.toString()
                + "\n" + "Please enter alert information as specified."
                + "or type 'end' to finish execution. \n \n");
        createAlert(userFacade, event);
    }

    /**
     * Create alert.
     *
     * @param userFacade the user facade
     * @param event      the event
     */
    public void createAlert(UserFacade userFacade, Event event) {

        System.out.println("What kind of alert do you want to create? \n" +
                "Type 1 to create non-repeating alert,\n" +
                "Type 2 to create a repeating alert.\n" +
                "or type 'end' to finish execution.");

        String option = reader.nextLine();
        if (option.equals("end")) {
            return;
        }
        while (!option.equals("1") && !option.equals("2")) {
            System.out.println("Your input is invalid. Please re-enter:" +
                    "or type 'end' to finish execution.");
            option = reader.nextLine();
            if (option.equals("end")) {
                break; }
        }
            if (option.equals("1")) {
                createSingleAlert(userFacade, event);
            } else {
                createRecurringAlert(userFacade, event);
            }
        }

    private void disableAlert(UserFacade userFacade, Event event){

    }


    private void createSingleAlert(UserFacade userFacade, Event event){
            String timeStr, amount, unit;

            System.out.println("How long before the event would you like this alert to go off? \n Selecting unit from:" +
                    "days, hours, minutes \n Please enter in the format of '(#)-(unit)' \n E.g. '3-hours'");
            timeStr = reader.nextLine();
            String[] temp = timeStr.split("-");

            while (!isValidDuration(temp)) {
                System.out.println("Invalid Input \n  Please reenter in the format: '(#)-(unit)' \n E.g. '3-hours'");
                timeStr = reader.nextLine();
                temp = timeStr.split("-");
            }

            amount = temp[0];
            unit = temp[1];
            Duration d = convertStringToDuration(amount, unit);
            while (userFacade.createAlert(event, false, d)) {
                System.out.println("**Warning** Identical alert exists. Fail to add alert. Please type anything to retry " +
                        "or type 'end' to finish execution.");
                timeStr = reader.nextLine();
                if (timeStr.equals("end")) {
                    return;
                } else {
                    createSingleAlert(userFacade, event);
                }
            }System.out.println("A non-repeating alert is successfully created for this event.");
    }


    private void createRecurringAlert(UserFacade userFacade, Event event) {
        String timeStr, amount, unit;

        System.out.println("Please type in how frequent the alerts should repeat in the format: \n Selecting unit from:" +
                "days, hours, minutes \n Please enter in the format of '(#)-(unit)' \n E.g. '3-hours'");

        timeStr = reader.nextLine();
        String[] temp = timeStr.split("-");

        while (!isValidDuration(temp)) {
            System.out.println("Invalid Input \n  Please reenter in the format: '(#)-(unit)' \n E.g. '3-hours'");
            timeStr = reader.nextLine();
            temp = timeStr.split("-");
        }

        amount = temp[0];
        unit = temp[1];
        Duration d = convertStringToDuration(amount, unit);
        while (userFacade.createAlert(event, true, d)) {
            System.out.println("**Warning** Identical alert exists. Fail to add alert. Please type anything to retry " +
                    "or type 'end' to finish execution.");
            timeStr = reader.nextLine();
            if (timeStr.equals("end")) {
                return;
            } else {
                createRecurringAlert(userFacade, event);
            }
        }System.out.println("Repeating alerts are successfully created for this event.");
    }


    /**
     * Convert string to duration duration.
     *
     * @param amount the amount
     * @param unit   the unit
     * @return the duration
     */
    public Duration convertStringToDuration(String amount, String unit){
    if (unit.equals("days")) {
        return Duration.ofDays(Long.parseLong(amount));
    } else if(unit.equals("hours")) {
        return Duration.ofHours(Long.parseLong(amount));
    } else {
        return Duration.ofMinutes(Long.parseLong(amount)); }
    }


    /**
     * Is valid duration boolean.
     *
     * @param intStr the int str
     * @return the boolean
     */
    public boolean isValidDuration(String[] intStr){
        if (intStr.length!=2){
            return false;
        }
        try{
            Integer.parseInt(intStr[0]);
        }catch(NumberFormatException e){
            return false;
        }
        return units.contains(intStr[1]);
    }


    /**
     * View alerts by event.
     *
     * @param userFacade the user facade
     * @param eventName  the event name
     */


    /**
     * Add tag to event.
     *
     * @param userFacade the user facade
     * @param event      the event
     */
    public void addTagToEvent(UserFacade userFacade, Event event) {
        System.out.println("Please type in the name of the tag you would like to add into for");
        String tagName = reader.nextLine();
        Tag tag = userFacade.getTagByTitle(tagName);
        while (tag == null) {
            System.out.println("The tag title does not exist, please type a valid title, \n" +
                    "or type 'end' to finish execution.");
            tagName = reader.nextLine();
            if (tagName.equals("end")) {
                return;
            }
            tag = userFacade.getTagByTitle(tagName);
        }
        boolean success = userFacade.addTag(tag, event);
        if (success){
            System.out.println("Tag has been added successfully to the event!");}
        else{
            System.out.println("The tag had been added, please try again with other tags.");
        }
    }

    /**
     * Add memo to event.
     *
     * @param userFacade the user facade
     * @param event      the event
     */
    public void addMemoToEvent(UserFacade userFacade, Event event) {
        System.out.println("Please type the title of the memo that you want to add:");
        String memoTitle = reader.nextLine();
        Memo memo = userFacade.getMemoByTitle(memoTitle);
        while (memo == null) {
            System.out.println("The memo title does not exist, please type a valid title, \n" +
                    "or type 'end' to finish execution.");
            memoTitle = reader.nextLine();
            if (memoTitle.equals("end")) {
                return;
            }
            memo = userFacade.getMemoByTitle(memoTitle);
        }
        userFacade.addMemo(memo, event);
        System.out.println("The memo is added successfully!");
    }


    /**
     * Search event by event name.
     *
     * @param userFacade the user facade
     */
    public void searchEventsByName(UserFacade userFacade){
        System.out.println("Please enter the name of the event you want to search for");
        String eventName = reader.nextLine();
        PriorityQueue<Event> events = userFacade.searchEventsByName(eventName);
        while (events == null) {
            System.out.println("No event exists under this name, please type in a valid title, \n" +
                    "or type 'end' to finish execution.");
            eventName = reader.nextLine();
            if (eventName.equals("end")) {
                return;
            } else {
                events = userFacade.searchEventsByName(eventName);
            }
        }
        while (events.size() != 0) {
            System.out.println(events.remove());
        }
    }

//    private PriorityQueue<Event> getEventsByName(UserFacade userFacade, String eventName) {
//        return userFacade.searchEventsByName(eventName);
//    }
//
//    private PriorityQueue<Event> getEventsByDate(UserFacade userFacade, LocalDate startTime) {
//        return userFacade.searchEventsByDate(startTime);
//    }
//
////    private Event searchEventsByNameTime(UserFacade userFacade){
//////
//////    }

    /**
     * Search event by time.
     *
     * @param userFacade the user facade
     */
    public void searchEventByTime(UserFacade userFacade){
        LocalDate eventDate;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        System.out.println("Please enter the start time of the event you want to search for in 'yyyy-MM-dd HH:mm' format： ");
        String timeString = reader.nextLine();
        while (!isValidDateTimeInput(timeString)){
            System.out.println("Enter your event start time in 'yyyy-MM-dd HH:mm' format");
            timeString = reader.nextLine();
        }
        eventDate = LocalDate.parse(timeString, formatter);
        PriorityQueue<Event> events = userFacade.searchEventsByDate(eventDate);
        for (Event e: events){
            System.out.println(e);
        }
    }


    /**
     * Search event by year.
     *
     * @param userFacade the user facade
     */
    public void searchEventByYear(UserFacade userFacade){
        Year year;
        System.out.println("Please enter the year of the event you want to search for in 'yyyy'. ");
        String timeString = reader.nextLine();
        while (!isIntegerGreaterThanZero(timeString)){
            System.out.println("Enter your event start time in 'yyyy'. ");
            timeString = reader.nextLine();
        }
        year = Year.of(Integer.parseInt(timeString));
        PriorityQueue<Event> events = userFacade.searchEventsByYear(year);
        for (Event e: events){
            System.out.println(e);
        }
    }

    /**
     * Search event by month.
     *
     * @param userFacade the user facade
     */
    public void searchEventByMonth(UserFacade userFacade){
        Month month;
        System.out.println("Please enter the month of the event you want to search for in 'MM'： ");
        String timeString = reader.nextLine();
        while (!isIntegerAValidMonth(timeString)){
            System.out.println("Enter your event start time in 'MM");
            timeString = reader.nextLine();
        }
        month = Month.of(Integer.parseInt(timeString));
        PriorityQueue<Event> events = userFacade.searchEventsByMonth(month);
        for (Event e: events){
            System.out.println(e);
        }
    }

    /**
     * Search event by date.
     *
     * @param userFacade the user facade
     */
    public void searchEventByDate(UserFacade userFacade){
        LocalDate eventDate;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        System.out.println("Please enter the date of the event you want to search for in 'yyyy-MM-dd' format： ");
        String timeString = reader.nextLine();
        while (!isValidDateInput(timeString)){
            System.out.println("Enter your event start time in 'yyyy-MM-dd' format");
            timeString = reader.nextLine();
        }
        eventDate = LocalDate.parse(timeString, formatter);
        PriorityQueue<Event> events = userFacade.searchEventsByDate(eventDate);
        for (Event e: events){
            System.out.println(e);
        }
    }

    /**
     * Search event from date to date.
     *
     * @param userFacade the user facade
     */
    public void searchEventFromDateToDate(UserFacade userFacade){
        LocalDate date1;
        LocalDate date2;
        PriorityQueue<Event> events;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        System.out.println("Please enter the start date of time period you want to search for in 'yyyy-MM-dd' format： ");
        String dateString1 = reader.nextLine();
        while (!isValidDateInput(dateString1)) {
            System.out.println("Incorrect input. Please enter your start date in 'yyyy-MM-dd' format");
            dateString1 = reader.nextLine();
        }

        System.out.println("Please enter the end date of time period you want to search for in 'yyyy-MM-dd' format： ");
        String dateString2 = reader.nextLine();
        while (!isValidDateInput(dateString2)) {
            System.out.println("Incorrect input. Please enter your end date in 'yyyy-MM-dd' format");
            dateString2 = reader.nextLine();
        }

        date1 = LocalDate.parse(dateString1, formatter);
        date2 = LocalDate.parse(dateString2, formatter);

        if (date1.isBefore(date2)){
            events = userFacade.searchEventsFromDateToDate(date1, date2);
        } else {
            events = userFacade.searchEventsFromDateToDate(date2, date1);
        }

        for (Event e : events){
            System.out.println(e);
        }
    }

    /**
     * Search events by tag.
     *
     * @param userFacade the user facade
     */
    public void searchEventsByTag(UserFacade userFacade) {
        Scanner reader = new Scanner(System.in);
        System.out.println("Please enter the title of the tag");
        String tagTitle = reader.nextLine();
        Tag tag = userFacade.getTagByTitle(tagTitle);
        while (tag == null) {
            System.out.println("The tag does not exist, please type a valid title, \n" +
                    "or type 'end' to finish execution.");
            tagTitle = reader.nextLine();
            if (tagTitle.equals("end")) {
                return;
            }
            tag = userFacade.getTagByTitle(tagTitle);
        }
        PriorityQueue<Event> events = userFacade.searchEventsByTag(tag.getTitle());
        if (events.size() == 0) {
            System.out.println("No event has been added to this tag.");
        } else {
            for (Event event : events) {
                System.out.println(event);
            }
        }
    }

    /**
     * Search events by series.
     *
     * @param userFacade the user facade
     */
    public void searchEventsBySeries(UserFacade userFacade) {
        Scanner reader = new Scanner(System.in);
        System.out.println("Please enter the name of the series");
        String seriesName = reader.nextLine();
        Series series = userFacade.getSeriesByName(seriesName);
        while (series == null) {
            System.out.println("The series does not exist, please type a valid name, \n" +
                    "or type 'end' to finish execution.");
            seriesName = reader.nextLine();
            if (seriesName.equals("end")) {
                return;
            }
            series = userFacade.getSeriesByName(seriesName);
        }
        PriorityQueue<Event> events = userFacade.searchEventsBySeries(seriesName);
        if (events.size() == 0) {
            System.out.println("No event has been added to this series.");
        } else {
            for (Event event : events) {
                System.out.println(event);
            }
        }
    }

    public void searchEventsByCalendar(UserFacade userFacade){
        Scanner reader = new Scanner(System.in);
        System.out.println("Please enter the calendar you want to search");
        String calendarName = reader.nextLine();
        if (calendarName == "end"){
            return;
        }
        PriorityQueue<Event> events = userFacade.searchEventsByCalendar(calendarName);
        if (events.size() == 0) {
            System.out.println("No event is under this calendar.");
        } else {
            for (Event event : events) {
                System.out.println(event);
            }
        }
    }

    public void addCalendarToEvent(UserFacade userFacade, Event event){
        Scanner reader = new Scanner(System.in);
        System.out.println("Please enter the calendar you want to add to the event");
        String calendarName = reader.nextLine();
        if (calendarName == "end"){
            return;
        }
        userFacade.addCalendar(event, calendarName);
        System.out.println("You have successfully put this event under " + calendarName);
    }

    /**
     * View tags by event.
     *
     * @param userFacade the user facade
     * @param event      the event
     */
    public void viewTagsByEvent(UserFacade userFacade, Event event) {
        ArrayList<Tag> tags = userFacade.getTagsByEvent(event.getEid());
        if (tags.size() == 0) {
            System.out.println("No tag has been tagged to this event.");
        } else {
            for (Tag tag : tags) {
                System.out.println(tag);
            }
        }
    }

    /**
     * View memo by event.
     *
     * @param userFacade the user facade
     * @param event      the event
     */
    public void viewMemoByEvent(UserFacade userFacade, Event event) {
        Memo memo = userFacade.getMemoByEvent(event);
        if (memo == null) {
            System.out.println("No memo is associated with this event.");
        } else {
            System.out.println(memo);
        }
    }


    /**
     * View alerts by event.
     *
     * @param userFacade the user facade
     * @param event      the event
     */
    public void viewAlertsByEvent(UserFacade userFacade, Event event) {
        PriorityQueue<Alert> aQueue = userFacade.getAlertsByEvent(event.getEid());
        if (aQueue.isEmpty()){
            System.out.println("No alert has been created for this event.");
        } else {
            for (Alert a: aQueue){
                System.out.println(a);}
        }
    }

    /**
     * Create event.
     *
     * @param userFacade the user facade
     */
    public void createEvent(UserFacade userFacade){
        Scanner reader = new Scanner(System.in);

        System.out.println("Please type the name of the event");
        String eventName = reader.nextLine();
        LocalDateTime startTime = null;
        LocalDateTime endTime = null;

        ArrayList<LocalDateTime> times = eventTimesMaker();
        startTime = times.get(0);
        endTime = times.get(1);
        while (existingEventChecker(userFacade, eventName, startTime, endTime)) {
            System.out.println("Identical event exists, please attempt again \n " +
                    "Type '1' to re-enter event name \n" + "Type '2' to re-enter event time \n");
            String choice = reader.nextLine();
            ArrayList<String> items = new ArrayList<>(Arrays.asList("1", "2"));
            while(!items.contains(choice)) {
                System.out.println("Invalid input. Please re-enter or type 'end' to finish execution.");
                choice = reader.nextLine();
                if (choice.equals("end")) {
                    return;
                }
            }
            if (choice.equals("1")) {
                System.out.println("Please type the name of the event");
                eventName = reader.nextLine();
            }else {
                times = eventTimesMaker();
                startTime = times.get(0);
                endTime = times.get(1);
            }
        }
        Event newEvent = userFacade.createEventReturnEvent(eventName, startTime, endTime);
        System.out.println("The event has been created successfully!");
        System.out.println(newEvent);
    }

    public ArrayList<LocalDateTime> eventTimesMaker () {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        LocalDateTime startTime;
        LocalDateTime endTime;
        String startString = null;
        String endString = null;
        boolean isValidStartTime = false;
        boolean isValidEndTime = false;
        while (!isValidStartTime){
            System.out.println("Enter your start time in yyyy-MM-dd HH:mm format");
            startString = reader.nextLine();
            isValidStartTime = isValidDateTimeInput(startString);
        }

        startTime = LocalDateTime.parse(startString, formatter);
        while (!isValidEndTime){
            System.out.println("Enter your end time in yyyy-MM-dd HH:mm format");
            endString = reader.nextLine();
            isValidEndTime = isValidDateTimeInput(endString);
        }
        endTime = LocalDateTime.parse(endString, formatter);
        return new ArrayList<>(Arrays.asList(startTime, endTime));
    }

    private boolean existingEventChecker(UserFacade userFacade, String eventName, LocalDateTime startTime,
                                         LocalDateTime endTime) {
        PriorityQueue<Event> events = userFacade.searchEventsByName(eventName);
        while(events.size() != 0) {
            Event current = events.remove();
            if (current.getStartTime().isEqual(startTime) && current.getEndTime().isEqual(endTime)){
                return true;
            }
        }
        return false;
    }


    public void createDuplicateEvent(UserFacade userFacade, Event event){
        String eventName = event.getName();
        LocalDateTime eventStartTime = event.getStartTime();
        LocalDateTime eventEndTime = event.getEndTime();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        LocalDateTime startTime;
        LocalDateTime endTime;
        Duration duration = Duration.between(eventEndTime, eventStartTime);

        System.out.println("You are creating a duplicate for an event named "+event.getName() + "originally held at" + eventStartTime);

        System.out.println("Please specify the startTime of the duplicated event,  + \n" +
                "or type 'end' to finish execution. \\n \\n\"");
        String startString = reader.nextLine();
        while (!isValidDateTimeInput(startString) || !isValidDuplicateStartTime(userFacade, eventName, startString)){
            System.out.println("Enter your start time in yyyy-MM-dd HH:mm format");
            startString = reader.nextLine();
            if (startString.equals("end")) {
                return;
            }
        }
        startTime = LocalDateTime.parse(startString, formatter);


        String endString;
        System.out.println("If you would like to change length of the event, enter 'yes'. Enter anything else to exit.");
        endString = reader.nextLine();
        if (endString.equals("yes")) {
            System.out.println("Enter your new end time in yyyy-MM-dd HH:mm format");
            while (!isValidDateTimeInput(endString) || !isValidDuplicateEndTime(startTime, endString)) {
                System.out.println("Enter your end time in yyyy-MM-dd HH:mm format or type 'end' to finish execution.");
                endString = reader.nextLine();
                if (startString.equals("end")) {
                    return;
                }
            }
            endTime = LocalDateTime.parse(endString, formatter);
        }else{
            endTime = startTime.plus(duration);
        }
        userFacade.createEvent(eventName, startTime, endTime);
    }

    private boolean isValidDuplicateStartTime(UserFacade userFacade, String eventName, String startString){
        PriorityQueue<Event> events = userFacade.searchEventsByName(eventName);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        LocalDateTime time = LocalDateTime.parse(startString, formatter);

        while (events.size()!=0 ){
            Event temp = events.remove();
            if (temp.getStartTime() == time){
                return false;
            }
        }
        return true;
    }

    private boolean isValidDuplicateEndTime(LocalDateTime startTime, String endString){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        LocalDateTime time = LocalDateTime.parse(endString, formatter);
        return time.compareTo(startTime) >= 0;
    }


    public void rescheduleEvent(UserFacade userFacade, Event event){
        String eventName = event.getName();
        LocalDateTime eventStartTime = event.getStartTime();
        LocalDateTime eventEndTime = event.getEndTime();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        Duration duration = Duration.between(eventEndTime, eventStartTime);

        System.out.println("You are rescheduling an event named "+event.getName() + "originally held at" + eventStartTime);

        System.out.println("Please specify the new startTime of the event,  + \n" +
                "or type 'end' to finish execution. \\n \\n\"");
        String startString = reader.nextLine();
        while (!isValidDateTimeInput(startString) || !isValidDuplicateStartTime(userFacade, eventName, startString)){
            System.out.println("Enter your start time in yyyy-MM-dd HH:mm format");
            startString = reader.nextLine();
            if (startString.equals("end")) {
                return;
            }
        }
        eventStartTime = LocalDateTime.parse(startString, formatter);
        eventEndTime = eventStartTime.plus(duration);

        event.setStartTime(eventStartTime);
        event.setEndTime(eventEndTime);
    }

        //由EventManager里面的helper算好Duration，以及endTime，以便新建event

    public void postponeEvent(UserFacade userFacade, Event event){
        LocalDateTime eventStartTime = event.getStartTime();
        LocalDateTime eventEndTime = event.getEndTime();

        System.out.println("You are postponing an event named "+event.getName() + "originally held at" + eventStartTime);

        System.out.println("Please type 'yes' to specify how long you'd like to postpone the event, type anything else" +
                "to postpone event indefinitely.");
        String answer = reader.nextLine();
        if(answer == "yes"){
            String timeStr, amount, unit;

            System.out.println("Please type in how long the event will be postponed in this format: \n Selecting unit from:" +
                    "days, hours, minutes \n Please enter in the format of '(#)-(unit)' \n E.g. '3-hours'");
            timeStr = reader.nextLine();
            String[] temp = timeStr.split("-");

            while (!isValidDuration(temp)) {
                System.out.println("Invalid Input \n  Please reenter in the format: '(#)-(unit)' \n E.g. '3-hours'");
                timeStr = reader.nextLine();
                temp = timeStr.split("-");
            }
            amount = temp[0];
            unit = temp[1];
            Duration d = convertStringToDuration(amount, unit);

            eventStartTime.plus(d);
            eventEndTime.plus(d);
            event.setStartTime(eventStartTime);
            event.setEndTime(eventEndTime);
        }

        else{
            event.setStartTime(LocalDateTime.MAX);
            event.setEndTime(LocalDateTime.MAX);
        }
    }

    public void deleteTag(UserFacade userFacade, Event event){
        System.out.println("Type 1 to delete all tags of the event. \n" +
                "Type 2 to delete a specific tag of the event.");
        String option = reader.nextLine();
        if (option.equals("1")){
            userFacade.deleteTags(event);
            System.out.println("The tags have been deleted successfully!");
        }
        else if(option.equals("2")){
            System.out.println("Please type the name of the tag that you want to delete.");
            String tagName = reader.nextLine();
            Tag tag = userFacade.getTagByTitle(tagName);
            userFacade.deleteTag(event, tag);
            System.out.println("The tag has been deleted successfully!");
        }
    }

    public void deleteMemo(UserFacade userFacade, Event event){
        userFacade.deleteMemo(event);
        System.out.println("The memo has been deleted successfully!");
    }

    public void deleteSeries(UserFacade userFacade, Event event){
        System.out.println("Please type the name of the series that you want to delete:");
        String seriesName = reader.nextLine();
        Series series = userFacade.getSeriesByName(seriesName);
        userFacade.deleteEventSeries(event, series);
        System.out.println("The series has been deleted successfully!");
    }

    public void deleteAlerts(UserFacade userFacade, Event event){
        userFacade.deleteAlerts(event);
        System.out.println("The alerts of the event have been deleted successfully!");
    }

    public void deleteEvent(UserFacade userFacade, Event event){
        userFacade.deleteEvent(event);
        System.out.println("The event have been deleted successfully!");
    }

    /**
     * Is valid date time input boolean.
     *
     * @param dateTimeString the date time string
     * @return the boolean
     */
    public boolean isValidDateTimeInput(String dateTimeString) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        try{
            LocalDateTime dateTime = LocalDateTime.parse(dateTimeString, formatter);
        } catch(DateTimeParseException e) {
            return false;
        }
        return true;
    }

    /**
     * Is valid date input boolean.
     *
     * @param dateString the date string
     * @return the boolean
     */
    public boolean isValidDateInput(String dateString) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        try{
            LocalDate date = LocalDate.parse(dateString, formatter);
        } catch(DateTimeParseException e) {
            return false;
        }
        return true;
    }

    /**
     * Is integer greater than zero boolean.
     *
     * @param intString the int string
     * @return the boolean
     */
    public boolean isIntegerGreaterThanZero(String intString){
        long number;
        try{
            number = Long.parseLong(intString);
        } catch(NumberFormatException e) {
            return false;
        }
        return number > 0;
    }

    /**
     * Is integer a valid month boolean.
     *
     * @param intString the int string
     * @return the boolean
     */
    public boolean isIntegerAValidMonth(String intString){
        long number;
        try{
            number = Long.parseLong(intString);
        } catch(NumberFormatException e) {
            return false;
        }
        return 0 < number && number < 13;
    }

    /**
     * Is valid number boolean.
     *
     * @param numStr the num str
     * @return the boolean
     */
    public boolean isValidNumber(String numStr) {
        try{
            Integer.parseInt(numStr);
        } catch(NumberFormatException e) {
            return false;
        }
        return true;
    }

    /**
     * Share the event to targetUser with message.
     *
     * @param userFacade the user facade
     * @param targetUser the user that you want to share your event with
     * @param event the event you want to share
     * @param message the message you want to send
     */
    public void shareEvent(UserFacade userFacade, String targetUser, Event event, String message){
        FileUtils.shareEvent(userFacade.getUserAccount(), targetUser, event, message);
    }

}







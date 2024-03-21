package Presenters;

import Data.Event;
import Data.Series;
import Data.UserFacade;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.PriorityQueue;
import java.util.Scanner;

/**
 * The type Series presenter.
 */
public class SeriesPresenter {
    Scanner reader = new Scanner(System.in);

    /**
     * Create series boolean.
     *
     */
    public void createSeries(UserFacade userFacade){
        System.out.println("Which way do you want to create your series?\n" +
                "Type 1 to create series with existing events;\n" +
                "Type 2 to create series with new events.");
        String option = reader.nextLine();
        while (!option.equals("1") && !option.equals("2")) {
            System.out.println("Your input is invalid. Please retype:");
            option = reader.nextLine();
        }
        if(option.equals("1")) {
            createWithExistingEvents(userFacade);
        } else {
            createWithNewEvents(userFacade);
        }
    }

    public void viewAllSeries(UserFacade userFacade){
        userFacade.displayALlSeries();
    }

    public void addEventToSeries(UserFacade userFacade, Event event){
//        String eventName;
        String seriesName;
        boolean found;
//        Event event = null;
        Series series = null;
        ArrayList<Series> allSeries;

//        while(!found) {
//            System.out.println("Enter name of Event that you want to add to series");
//            eventName = reader.nextLine();
//            event = userFacade.searchEventsByEventName(eventName);
//            if (event == null) {
//                System.out.println("No such event found, please enter again");
//            } else {
//                found = true;
//            }
//        }
        found = false;
        while(!found) {
            System.out.println("Enter name of series that you want to add to");
            seriesName = reader.nextLine();
            allSeries = userFacade.getAllSeries();
            for (Series s : allSeries){
                if(s.getName().equals(seriesName)){
                    found = true;
                    series = s;
                }
            }
        }
        userFacade.linkSingleEventToSeries(event, series);
    }

    public void searchEventsBySeries(UserFacade userFacade){
        boolean found = false;
        String seriesName = null;
        ArrayList<Series> allSeries;
        Series series = null;

        while(!found) {
            System.out.println("Which series do you want to check?");
            seriesName = reader.nextLine();
            allSeries = userFacade.getAllSeries();
            for (Series s : allSeries){
                if(s.getName().equals(seriesName)){
                    found = true;
                    series = s;
                }
            }
        }
        System.out.println(userFacade.seriesAssociatedEvents(series));
    }

    /**
     * Create with existed events boolean.
     *
     */
    public void createWithExistingEvents(UserFacade userFacade){
        String name;
        Event event;
        PriorityQueue<Event> eventsToAdd = new PriorityQueue<>();
        boolean wantToEnd = false;
        PriorityQueue<Event> events = userFacade.getAllEvents();
        System.out.println("You can add following events: ");
//        userFacade.printEvents(allEvents);
        // display all events with number
        System.out.println("Please specify the numbers of events you would like to link to the series, one number each line. " +
                "Or enter 'end' to exit at any time.");
        int count = 1;
        for (Event e: events) {
            System.out.println(count + ". " + e.toString());
            count ++;
        }

        // prompt user to input a number one by one

        String numStr = reader.nextLine();
        while(!wantToEnd) {
            System.out.println("Enter number of Event that you want to add to series, or type 'end' to exit.");
            name = reader.nextLine();
            if (name.equals("end")) {
                wantToEnd = true;
            }else{
                while (!isValidNumber(numStr) || Integer.parseInt(numStr) < 1 || Integer.parseInt(numStr) > events.size()) {
                    if (numStr.equals("end")) {
                        return;
                    } else {
                        System.out.println("Invalid number selection, please re-enter.");
                        numStr = reader.nextLine();
                    }
                }
                int ord = Integer.parseInt(numStr);
                Event[] eventsList = (Event[]) events.toArray();
                eventsToAdd.add(eventsList[ord - 1]);
            }
        }

//        System.out.println("Please select the event you would like to add to by typing an existing event's title: ");
//        name = reader.nextLine();
//        Event event = getExactEvent(userFacade,name);

        /* Create event Names */
//        while(!wantToEnd) {
//            System.out.println("Enter name of Event that you want to add to series, or type 'end' to exit.");
//            name = reader.nextLine();
//            if (name.equals("end")) {
//                wantToEnd = true;
//            } else{
//                Event event = getExactEvent(userFacade,name);
//                Event event = userFacade.searchEventByEventName(name);
//                if (event == null) {
//                    System.out.println("No such event found, please enter again");
//                } else {
//                    eventsToBeAdd.add(event);
//                }
//            }
//        }
        /* Create series name */
        String seriesName = getSeriesName(userFacade);
        Series series = userFacade.createSeries(seriesName);
        userFacade.linkMultipleEvents(eventsToAdd, series);

        System.out.println("Thank you");


    }

    
    /**
     * Create with new events boolean.
     *
     */
    public void createWithNewEvents(UserFacade userFacade){
        String seriesName;
        java.time.LocalDateTime startTime;
        java.time.LocalDateTime endTime;
        long frequency;
        int numbers;

        seriesName = getSeriesName(userFacade);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        startTime = getStartTime(formatter);
        endTime = getEndTime(formatter);
        frequency = getFrequency();
        numbers = getNumbers();

        PriorityQueue<Event> newEvents = userFacade.createMultipleEvents(seriesName,startTime, endTime, frequency, numbers);
        Series series = userFacade.createSeries(seriesName);
        userFacade.linkMultipleEvents(newEvents, series);
    }

    private int getNumbers() {
        int numbers;
        boolean isValidNumbers = false;
        String numberString = null;
        while(!isValidNumbers){
            System.out.println("How many times do you want to repeat?");
            numberString = reader.nextLine();
            isValidNumbers = isIntegerGreaterThanZero(numberString);
        }
        numbers = Integer.parseInt(numberString);
        return numbers;
    }

    private long getFrequency() {
        long frequency;
        boolean isValidFrequency = false;
        String frequencyString = null;
        while (!isValidFrequency) {
            System.out.println("Repeat every ____ days? Please enter a number.");
            frequencyString = reader.nextLine();
            isValidFrequency = isIntegerGreaterThanZero(frequencyString);
        }

        frequency = Long.parseLong(frequencyString);
        return frequency;
    }

    private LocalDateTime getEndTime(DateTimeFormatter formatter) {
        LocalDateTime endTime;
        String endString = null;
        boolean isValidEndTime = false;
        while (!isValidEndTime){
            System.out.println("Enter your first event end time in yyyy-MM-dd HH:mm format");
            endString = reader.nextLine();
            isValidEndTime = isValidDateTimeInput(endString);
        }

        endTime = LocalDateTime.parse(endString, formatter);
        return endTime;
    }

    private LocalDateTime getStartTime(DateTimeFormatter formatter) {
        LocalDateTime startTime;
        String startString = null;

        boolean isValidStartTime = false;
        while (!isValidStartTime){
            System.out.println("Enter your first event start time in yyyy-MM-dd HH:mm format");
            startString = reader.nextLine();
            isValidStartTime = isValidDateTimeInput(startString);
        }

        startTime = LocalDateTime.parse(startString, formatter);
        return startTime;
    }

    private String getSeriesName(UserFacade userFacade) {
        String seriesName = null;
        boolean isValid = false;
        while(!isValid) {
            System.out.println("Enter your series name:");
            seriesName = reader.nextLine();
            boolean existedSeriesName = false;
            for (Series series : userFacade.getAllSeries()) {
                if (series.getName().equals(seriesName)) {
                    existedSeriesName = true;
                }
            }
            if (!existedSeriesName){
                isValid = true;
            }
        }
        return seriesName;
    }

    public boolean isValidDateTimeInput(String dateTimeString) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        try{
            LocalDateTime dateTime = LocalDateTime.parse(dateTimeString, formatter);
        } catch(DateTimeParseException e) {
            return false;
        }
        return true;
    }

    public boolean isIntegerGreaterThanZero(String intString){
        long number;
        try{
            number = Long.parseLong(intString);
        } catch(NumberFormatException e) {
            return false;
        }
        return number > 0;
    }

    public boolean isValidNumber(String numStr) {
        try{
            Integer.parseInt(numStr);
        } catch(NumberFormatException e) {
            return false;
        }
        return true;
    }
}

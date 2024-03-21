package Presenters;

import Data.Event;
import Data.Series;
import Data.UserFacade;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * The type Series presenter.
 */
public class SeriesPresenter {
    Scanner choice = new Scanner(System.in);

    /**
     * Create series boolean.
     *
     */
    public void createSeries(UserFacade userFacade){
        System.out.println("Which way do you want to create your series?\n" +
                "Type 1 to create series with existed events;\n" +
                "Type 2 to create series with new events.");
        String option = choice.nextLine();
        while (!option.equals("1") && !option.equals("2")) {
            System.out.println("Your input is invalid. Please retype:");
            option = choice.nextLine();
        }
        if(option.equals("1")) {
            createWithExistedEvents(userFacade);
        } else {
            createWithNewEvents(userFacade);
        }
    }

    public void viewAllSeries(UserFacade userFacade){
        userFacade.displayALlSeries();
    }

    public void addEventToSeries(UserFacade userFacade){
        String eventName;
        String seriesName;
        boolean found = false;
        Event event = null;
        Series series = null;
        ArrayList<Series> allSeries;

        while(!found) {
            System.out.println("Enter name of Event that you want to add to series");
            eventName = choice.nextLine();
            event = userFacade.searchEventByEventName(eventName);
            if (event == null) {
                System.out.println("No such event found, please enter again");
            } else {
                found = true;
            }
        }
        found = false;
        while(!found) {
            System.out.println("Enter name of series that you want to add to");
            seriesName = choice.nextLine();
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
            seriesName = choice.nextLine();
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
    public void createWithExistedEvents(UserFacade userFacade){
        String name;
        ArrayList<Event> eventsToBeAdd = new ArrayList<>();
        boolean wantToEnd = false;
        ArrayList<Event> allEvents = userFacade.getAllEvents();
        System.out.println("You can add following events: ");
        userFacade.printEvents(allEvents);


        /* Create event Names */
        while(!wantToEnd) {
            System.out.println("Enter name of Event that you want to add to series, or type 'end' to exit.");
            name = choice.nextLine();
            if (name.equals("end")) {
                wantToEnd = true;
            } else{
                Event event = userFacade.searchEventByEventName(name);
                if (event == null) {
                    System.out.println("No such event found, please enter again");
                } else {
                    eventsToBeAdd.add(event);
                }
            }
        }
        /* Create series name */
        String seriesName = getSeriesName(choice, userFacade);
        Series series = userFacade.createSeries(seriesName);
        userFacade.linkMultipleEvents(eventsToBeAdd, series);

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

        seriesName = getSeriesName(choice, userFacade);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        startTime = getStartTime(choice, formatter);
        endTime = getEndTime(choice, formatter);
        frequency = getFrequency(choice);
        numbers = getNumbers(choice);

        ArrayList<Event> newEvents = userFacade.createMultipleEvents(seriesName,startTime, endTime, frequency, numbers);
        Series series = userFacade.createSeries(seriesName);
        userFacade.linkMultipleEvents(newEvents, series);
    }

    private int getNumbers(Scanner choice) {
        int numbers;
        boolean isValidNumbers = false;
        String numberString = null;
        while(!isValidNumbers){
            System.out.println("How many times do you want to repeat?");
            numberString = choice.nextLine();
            isValidNumbers = isIntegerGreaterThanZero(numberString);
        }
        numbers = Integer.parseInt(numberString);
        return numbers;
    }

    private long getFrequency(Scanner choice) {
        long frequency;
        boolean isValidFrequency = false;
        String frequencyString = null;
        while (!isValidFrequency) {
            System.out.println("Repeat every ____ days? Please enter a number.");
            frequencyString = choice.nextLine();
            isValidFrequency = isIntegerGreaterThanZero(frequencyString);
        }

        frequency = Long.parseLong(frequencyString);
        return frequency;
    }

    private LocalDateTime getEndTime(Scanner choice, DateTimeFormatter formatter) {
        LocalDateTime endTime;
        String endString = null;
        boolean isValidEndTime = false;
        while (!isValidEndTime){
            System.out.println("Enter your first event end time in yyyy-MM-dd HH:mm format");
            endString = choice.nextLine();
            isValidEndTime = isValidDateTimeInput(endString);
        }

        endTime = LocalDateTime.parse(endString, formatter);
        return endTime;
    }

    private LocalDateTime getStartTime(Scanner choice, DateTimeFormatter formatter) {
        LocalDateTime startTime;
        String startString = null;

        boolean isValidStartTime = false;
        while (!isValidStartTime){
            System.out.println("Enter your first event start time in yyyy-MM-dd HH:mm format");
            startString = choice.nextLine();
            isValidStartTime = isValidDateTimeInput(startString);
        }

        startTime = LocalDateTime.parse(startString, formatter);
        return startTime;
    }

    private String getSeriesName(Scanner choice, UserFacade userFacade) {
        String seriesName = null;
        boolean isValid = false;
        while(!isValid) {
            System.out.println("Enter your series name:");
            seriesName = choice.nextLine();
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
}

package Presenters;

import Data.*;

import javax.swing.text.DateFormatter;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.PriorityQueue;
import java.util.Scanner;
import java.time.LocalDate;

public class EventPresenter {

    private Scanner reader = new Scanner(System.in);
    private ArrayList<String> units = new ArrayList<String>(
            Arrays.asList("days",
                    "hours",
                    "minutes"));

    public void showAllEvent(UserFacade userFacade) {
        for (Event event : userFacade.getAllEvents()) {
            System.out.println(event);
        }
    }

    public void showPastEvent(UserFacade userFacade) {
        for (Event event : userFacade.getPastEvents()) {
            System.out.println(event);
        }
    }

    public void showCurrentEvent(UserFacade userFacade) {
        for (Event event : userFacade.getCurrentEvents()) {
            System.out.println(event);
        }
    }

    public void showFutureEvent(UserFacade userFacade) {
        for (Event event : userFacade.getFutureEvents()) {
            System.out.println(event);
        }
    }

//    public void displayAllAlerts(UserFacade userFacade){
//        userFacade.displayAllAlerts();
//    }

    public void addAlertToEvent(UserFacade userFacade, String eventName) {
        LocalDateTime eventTime;
        Event event;
        event = userFacade.searchEventByEventName(eventName);
        eventTime = event.getStartTime();

        System.out.println("You are creating an alert for event named " + eventName + " held at " + eventTime.toString()
                + "\n" + "Please enter alert information as specified."
                + "or type 'end' to finish execution. \n \n");
        createAlert(userFacade, event);
    }

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
                break;
            }
        }
        if (option.equals("1")) {
            createSingleAlert(userFacade, event);
        } else {
            createRecurringAlert(userFacade, event);
        }
    }


    private void createSingleAlert(UserFacade userFacade, Event event) {
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
        }
        System.out.println("A non-repeating alert is successfully created for this event.");
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
        }
        System.out.println("Repeating alerts are successfully created for this event.");
    }


    public Duration convertStringToDuration(String amount, String unit) {
        if (unit.equals("days")) {
            return Duration.ofDays(Long.parseLong(amount));
        } else if (unit.equals("hours")) {
            return Duration.ofHours(Long.parseLong(amount));
        } else {
            return Duration.ofMinutes(Long.parseLong(amount));
        }
    }


    public boolean isValidDuration(String[] intStr) {
        if (intStr.length != 2) {
            return false;
        }
        try {
            Integer.parseInt(intStr[0]);
        } catch (NumberFormatException e) {
            return false;
        }
        return units.contains(intStr[1]);
    }


    public void viewAlertsByEvent(UserFacade userFacade, String eventName) {
        Scanner reader = new Scanner(System.in);
        Event event = userFacade.searchEventByEventName(eventName);
        PriorityQueue<Alert> aQueue = userFacade.getAlertsByEvent(event.getName());
        if (aQueue.isEmpty()) {
            System.out.println("No alert has been created for this event.");
        } else {
            for (Alert a : aQueue) {
                System.out.println(a);
            }
        }
    }


    public void addTagToEvent(UserFacade userFacade, String eventName) {
        Event event = userFacade.searchEventByEventName(eventName);
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
        if (success) {
            System.out.println("Tag has been added successfully to the event!");
        } else {
            System.out.println("The tag had been added, please try again with other tags.");
        }
    }

    public void addMemoToEvent(UserFacade userFacade, String eventName) {
        Event event = userFacade.searchEventByEventName(eventName);
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


    public void searchEventByEventName(UserFacade userFacade) {
        System.out.println("Please enter the name of the event you want to search for");
        String eventName = reader.nextLine();
        Event event = userFacade.searchEventByEventName(eventName);
        while (event == null) {
            System.out.println("The tag does not exist, please type a valid title, \n" +
                    "or type 'end' to finish execution.");
            eventName = reader.nextLine();
            if (eventName.equals("end")) {
                return;
            } else {
                event = userFacade.searchEventByEventName(eventName);
            }
        }
        System.out.println(event);
    }

    public void searchEventByDate(UserFacade userFacade) {
        LocalDate eventDate;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        System.out.println("Please enter the time date of the event you want to search for in 'yyyy-MM-dd' format： ");
        String timeString = reader.nextLine();
        while (!isValidDateInput(timeString)) {
            System.out.println("Enter your event start time in 'yyyy-MM-dd' format");
            timeString = reader.nextLine();
        }
        eventDate = LocalDate.parse(timeString, formatter);
        ArrayList<Event> events = userFacade.searchEventByDate(eventDate);
        for (Event e : events) {
            System.out.println(e);
        }
    }

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
        ArrayList<Event> events = userFacade.searchEventByTag(tag.getTitle());
        if (events.size() == 0) {
            System.out.println("No event has been added to this tag.");
        } else {
            for (Event event : events) {
                System.out.println(event);
            }
        }
    }

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
        ArrayList<Event> events = userFacade.searchEventBySeries(seriesName);
        if (events.size() == 0) {
            System.out.println("No event has been added to this series.");
        } else {
            for (Event event : events) {
                System.out.println(event);
            }
        }
    }

    public void viewTagsByEvent(UserFacade userFacade, String eventName) {
        Event event = userFacade.searchEventByEventName(eventName);
        ArrayList<Tag> tags = userFacade.getTagsByEvent(eventName);
        if (tags.size() == 0) {
            System.out.println("No tag has been tagged to this event.");
        } else {
            for (Tag tag : tags) {
                System.out.println(tag);
            }
        }
    }

    public void viewMemoByEvent(UserFacade userFacade, String eventName) {
        Event event = userFacade.searchEventByEventName(eventName);
        Memo memo = userFacade.getMemoByEvent(event);
        if (memo == null) {
            System.out.println("No memo is associated with this event.");
        } else {
            System.out.println(memo);
        }
    }

    public void createEvent(UserFacade userFacade) {
        Scanner reader = new Scanner(System.in);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        System.out.println("Please type the name of the event");
        String eventName = reader.nextLine();
        Event event = userFacade.searchEventByEventName(eventName);
        while (!(event == null)) {
            System.out.println("The event already exists, please type a valid name, \n" +
                    "or type 'end' to finish execution.");
            eventName = reader.nextLine();
            if (eventName.equals("end")) {
                return;
            }
            event = userFacade.searchEventByEventName(eventName);
        }
        LocalDateTime startTime;
        LocalDateTime endTime;
        String startString = null;
        String endString = null;
        boolean isValidStartTime = false;
        boolean isValidEndTime = false;
        while (!isValidStartTime) {
            System.out.println("Enter your start time in yyyy-MM-dd HH:mm format");
            startString = reader.nextLine();
            isValidStartTime = isValidDateTimeInput(startString);
        }

        startTime = LocalDateTime.parse(startString, formatter);
        while (!isValidEndTime) {
            System.out.println("Enter your end time in yyyy-MM-dd HH:mm format");
            endString = reader.nextLine();
            isValidEndTime = isValidDateTimeInput(endString);
        }
        endTime = LocalDateTime.parse(endString, formatter);
        Event newEvent = userFacade.createEventReturnEvent(eventName, startTime, endTime);
        System.out.println("The event has been created successfully!");
        System.out.println(newEvent);
        System.out.println("Do you want to create associated tag or memo to this event? \n" +
                "Type 'tag' to create tag; \n" +
                "Type 'memo' to create memo;");
        String option = reader.nextLine();
        ArrayList<String> options = new ArrayList<>(Arrays.asList("tag", "memo", "end"));
        while(!options.contains(option)){
            System.out.println("Invalid input. Please re-enter or type 'end' to finish execution.");
            option = reader.nextLine();
            if (option.equals("end")){
                return;
            }
        }
        if (option.equals("tag")){
            System.out.println("Please key in the title of this tag:");
            String title = reader.nextLine();
            while (!userFacade.createTag(title)) {
                System.out.println("Sorry, the name of the tag is not available. Please try again. \n" +
                        "If you would like to return to the previous page, type 1.");
                title = reader.nextLine();
                if ("1".equals(title)) {
                    return;
                }
                Tag tag = userFacade.getTagByTitle(title);
                userFacade.addTag(tag, newEvent);
                System.out.println("The tag is added successfully!");
            }
        }
        else if(option.equals("memo")){
            System.out.println("Please key in the title of this memo:");
            String title = reader.nextLine();
            while (!userFacade.createMemo(title)) {
                System.out.println("Sorry, the name of the memo is not available. Please try again. \n" +
                        "If you would like to return to the previous page, press 1.");
                title = reader.nextLine();
                if ("1".equals(title)) {
                    return;
                }
            }
            System.out.println("Memo has been created successfully, do you want to add content to this memo? \n"
                    + "Type 1 to add content. \n"
                    + "Type 2 to finish operation.");
                option = reader.nextLine();
            while (!option.equals("1") && !option.equals("2")) {
                System.out.println("Your input is invalid. Please retype:");
                option = reader.nextLine();
            }
            if (option.equals("1")) {
                System.out.println("Please type the content that you want to add to this memo");
                String content = reader.nextLine();
                Memo memo = userFacade.getMemoByTitle(title);
                userFacade.addContent(memo, content);
                System.out.println("Content is added successfully!");
            }
            Memo memo =  userFacade.getMemoByTitle(title);
            userFacade.addMemo(memo, newEvent);
            System.out.println("The memo is added successfully!");
        }
    }

    public boolean isValidDateTimeInput(String dateTimeString) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        try {
            LocalDateTime dateTime = LocalDateTime.parse(dateTimeString, formatter);
        } catch (DateTimeParseException e) {
            return false;
        }
        return true;
    }

    public boolean isValidDateInput(String dateString) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        try {
            LocalDate date = LocalDate.parse(dateString, formatter);
        } catch (DateTimeParseException e) {
            return false;
        }
        return true;
    }

}







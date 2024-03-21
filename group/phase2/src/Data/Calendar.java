package Data;

import Presenters.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.*;

/**
 * The type Data.Calendar.
 */
public class Calendar {
    private EventPresenter eventPresenter;
    private MemoPresenter memoPresenter;
    private TagPresenter tagPresenter;
    private SeriesPresenter seriesPresenter;
    private TodoPresenter todoPresenter;
    private UserFacade userFacade;
    private Scanner reader = new Scanner(System.in);

    public Calendar(UserFacade userFacade){
        this.userFacade = userFacade;
        eventPresenter = new EventPresenter();
        memoPresenter = new MemoPresenter();
        tagPresenter = new TagPresenter();
        seriesPresenter = new SeriesPresenter();
        todoPresenter = new TodoPresenter();
    }


    public Calendar(String userName){
        eventPresenter = new EventPresenter();
        memoPresenter = new MemoPresenter();
        tagPresenter = new TagPresenter();
        seriesPresenter = new SeriesPresenter();
        userFacade = new UserFacade(userName);
    }

    public Calendar(){
        eventPresenter = new EventPresenter();
        memoPresenter = new MemoPresenter();
        tagPresenter = new TagPresenter();
        seriesPresenter = new SeriesPresenter();
    }

    public void logIn(){
        System.out.println("If you would like to terminate this calendar program, type 'end'");
        Map dic = FileUtils.readUser();
        boolean valid = false;
        String userPassword = null;
        String userName = null;
        String givenPassword;
        while(!valid){
            System.out.println("Please enter your username:");
            userName = reader.nextLine();
            if (userName.equalsIgnoreCase("end")){
                System.exit(0);
            }
//            ArrayList<String> allUserNames = Collections.list(dic.keys());

            if(dic.containsKey(userName)){
                valid = true;
                userPassword = (String) dic.get(userName);
            }else {
                System.out.println("Sorry, User does not exist");
            }
        }
        valid = false;
        while(!valid) {
            System.out.println("Please enter your password:");
            givenPassword = reader.nextLine();
            if (givenPassword.equalsIgnoreCase("end")){
                System.exit(0);

            }
            if (givenPassword.equals(userPassword)) {
                valid = true;
                userFacade = new UserFacade(userName);
                FileUtils.loadDatabaseFromFile(userFacade);
            }else {
                System.out.println("Password incorrect, please enter again: ");
            }
        }

    }

    public void run() {
        logIn();
        getSharedEvent();
        eventPresenter.showAllEvent(userFacade);
        ArrayList<String> items = new ArrayList<>(Arrays.asList("1", "2", "3","4", "5", "6", "7", "8", "9", "10", "end"));
        while (true) {
            System.out.println();
            System.out.println();
            System.out.println("------------------------------------------------------------------");
            System.out.println("Search Event by Keywords, Type '1'");
            System.out.println("Display All Past/Current/Future Events, Tags, Memos, or Series, Type '2'");
            System.out.println("View One Event's Alerts, Tags, Memos, Type '3'");
            System.out.println("Create New Things, Type '4'");
            System.out.println("Add Things to an Existing Event, Type '5'");
            System.out.println("Add an Existing Event to a Series, Type '6'");
            System.out.println("To modify an existing events/memos/alerts, type '7'");
            System.out.println("To delete events/memos/tags/");
            System.out.println("To change your password, Type '8'");
            System.out.println("To manage your Todo list, Type '9'");
            System.out.println("To share event with other users, Type '10'");
            System.out.println("Terminate This Calendar Program, Type 'end'");
            String choice = reader.nextLine();
            while (!items.contains(choice)) {
                System.out.println("Invalid input. Please re-enter or type 'end' to finish execution.");
                choice = reader.nextLine();
            }
            switch (choice) {
                case "1":
                    search();
                    break;
                case "2":
                    display();
                    break;
                case "3":
                    viewEventInfo();
                    break;
                case "4":
                    create();
                    break;
                case "5":
                    addToEvent();
                    break;
                case "6":
                    addEventToSeries();
                    break;
                case "7":
                    modify();
                    break;
                case "8":
                    changePassword();
                    break;
                case "9":
                    manageTodoList();
                    break;
                case "10":
                    shareEvent();
                    break;
                case "end":
                    FileUtils.saveDatabaseToFile(userFacade);
                    return;
            }
        }
    }

    public void getSharedEvent(){
        String sharedBy;
        String eventName;
        String start;
        String end;
        String message;
        int i = 0;
        String choice;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

        ArrayList<String> allSharedInfo = FileUtils.getShared(userFacade.getUserAccount());
        if (allSharedInfo.size() == 0){
            return;
        }

        System.out.println("Your friends share some event(s) with you.");
        while(allSharedInfo.size() >= (i + 5)){
            sharedBy = allSharedInfo.get(i);
            eventName = allSharedInfo.get(i + 1);
            start = allSharedInfo.get(i + 2).substring(0, 16);
            end = allSharedInfo.get(i + 3).substring(0, 16);
            message = allSharedInfo.get(i + 4);
            i = i + 5;
            System.out.println(sharedBy + " said " + message + "  Do you want to add " + eventName +
                    " which lasts from " + start + " to " + end);
            System.out.println("Type 'y' to accept, or 'n' to reject.");
            choice = reader.nextLine();
            LocalDateTime startTime = LocalDateTime.parse(start, formatter);
            LocalDateTime endTime = LocalDateTime.parse(end, formatter);
            while (!choice.equalsIgnoreCase("y") && !choice.equalsIgnoreCase("n")){
                System.out.println("Invalid input. Please re-enter your choice, 'y' or 'n'?");
            }
            if (choice.equalsIgnoreCase("y")){
                userFacade.createEvent(eventName, startTime, endTime);
            }
        }
        System.out.println("No events left to handle, congratulations!");
    }

    public void shareEvent(){
        System.out.println("Which event do you want to share?");
        String eventName = reader.nextLine();
        Event event = getExactEvent(eventName);
        if (event == null){
            return;
        }
        System.out.println("Who do you want to share with?");
        String userName = getExactUser();
        if (userName == null){
            return;
        }
        System.out.println("What message do you want to send?");
        String message = reader.nextLine();
        eventPresenter.shareEvent(userFacade, userName, event, message);
    }

    public void manageTodoList(){
        todoPresenter.showAllTodoLists(userFacade);
        System.out.println("Please select from the following:");
        System.out.println("If you would like to create a new todo-list, type '1'");
        System.out.println("If you would like to see one of your existing todo-lists, type '2'");
        ArrayList<String> options = new ArrayList<>(Arrays.asList("1", "2"));
        String choice = reader.nextLine();
        while(!options.contains(choice)){
            System.out.println("Sorry, the input is invalid, please try again or type 'end' to terminate.");
            choice = reader.nextLine();
            if(choice.equalsIgnoreCase("end")){
                return;
            }
        }
        if (choice.equalsIgnoreCase("1")){
            todoPresenter.createTodoList(userFacade);
        }
        else{
            todoPresenter.selectTodoList(userFacade);
        }
    }

    public void changePassword(){
        System.out.println("Please enter the new password you would like to set to.");
        String firstTime = reader.nextLine();
        System.out.println("Please confirm the new password by entering again.");
        String secondTime = reader.nextLine();
        while(!firstTime.equals(secondTime)){
            System.out.println("The password you entered does not match. Please try again. If you would like to exit " +
                    "type 'end'.");
            secondTime = reader.nextLine();
            if(secondTime.equalsIgnoreCase("end")){
                return;
            }
        }
        if(FileUtils.changePassword(userFacade.getUserAccount(), firstTime)){
            System.out.println("The password is changed successfully.");
        }
        else{
            System.out.println("Sorry, the password you entered is not valid. Please try again.");
            changePassword();
        }
    }

    public void search() {
        ArrayList<String> options = new ArrayList<>(
                Arrays.asList("name", "date", "year", "month","from date to date", "tag", "series", "calendar"));

        System.out.println("Please select the items you want to display by typing one of the following: " +
                "'name', 'date', 'year', 'month', 'from date to date', 'tag', 'series', 'calendar'");
        String choice = reader.nextLine();
        while (!options.contains(choice)) {
            System.out.println("Invalid input. Please re-enter or type 'end' to finish execution.");
            choice = reader.nextLine();
            if (choice.equals("end")) {
                return;
            }
        }
        if (choice.equalsIgnoreCase("name")) {
            eventPresenter.searchEventsByName(userFacade);
        } else if (choice.equalsIgnoreCase("date")) {
            eventPresenter.searchEventByDate(userFacade);
        } else if (choice.equalsIgnoreCase("year")){
            eventPresenter.searchEventByYear(userFacade);
        } else if (choice.equalsIgnoreCase("month")){
            eventPresenter.searchEventByMonth(userFacade);
        } else if (choice.equalsIgnoreCase("from date to date")) {
            eventPresenter.searchEventFromDateToDate(userFacade);
        } else if (choice.equalsIgnoreCase("tag")) {
            eventPresenter.searchEventsByTag(userFacade);
        } else if (choice.equalsIgnoreCase("series")) {
            eventPresenter.searchEventsBySeries(userFacade);
        } else if (choice.equalsIgnoreCase("calendar")){
            eventPresenter.searchEventsByCalendar(userFacade);
        }
    }




//    Display all tags
//    Display all memos
//    Display all series

    public void display(){
        ArrayList<String> items = new ArrayList<>(
                Arrays.asList("pastEvents", "currentEvents", "futureEvents", "tags", "memos", "series", "todoList"));

        System.out.println("Please select the items you want to display by typing one of the following: 'pastEvents'," +
                " 'currentEvents' , futureEvents', 'tags','memos'" +
                "'series', 'todoList'");
        String choice = reader.nextLine();
        while(!items.contains(choice)) {
            System.out.println("Invalid input. Please re-enter or type 'end' to finish execution.");
            choice = reader.nextLine();
            if (choice.equals("end")) {
                return;
            }
        }
        if (choice.equalsIgnoreCase("pastEvents")){
            eventPresenter.showPastEvent(userFacade);
        }
        else if(choice.equalsIgnoreCase("currentEvents")){
            eventPresenter.showCurrentEvent(userFacade);
        } else if (choice.equalsIgnoreCase("futureEvents")){
            eventPresenter.showFutureEvent(userFacade);
        }
        else if (choice.equalsIgnoreCase("tags")) {
            tagPresenter.showAllTag(userFacade);
        } else if (choice.equalsIgnoreCase("memos")) {
            memoPresenter.displayAllMemos(userFacade);
        } else if (choice.equalsIgnoreCase("series")){
            seriesPresenter.viewAllSeries(userFacade);
        }else if (choice.equalsIgnoreCase("todoList")){
            todoPresenter.showAllTodoLists(userFacade);
        }

    }

    public void viewEventInfo(){
        System.out.println("Please type the title of the event that you want to view:");
        String eventName = reader.nextLine();
        Event event = getExactEvent(eventName);
        if (event == null){ return; }

        event.toString();
        ArrayList<String> items = new ArrayList<>(
                Arrays.asList("reschedule", "postpone"));
        System.out.println("If you would like to add reschedule this event, type 'reschedule'");
        System.out.println("If you would like to add postpone this event, type 'postpone'");
        String choice = reader.nextLine();
        while (!items.contains(choice)){
            System.out.println("Invalid input. Please re-enter or type 'end' to finish execution.");
            choice = reader.nextLine();
            if (choice.equals("end")){
                return;
            }
        }

        if (choice.equalsIgnoreCase("reschedule")){
            eventPresenter.rescheduleEvent(userFacade, event);
        } else if (choice.equalsIgnoreCase("postpone")){
            eventPresenter.postponeEvent(userFacade, event);
        }

        System.out.println("Type 1 to view associated tags \n" + "Type 2 to view associated memo \n" +
                "Type 3 to view the tags. \n" + "Type 4 to view the alerts \n" + "Type 5 to view the series.");
        String option = reader.nextLine();
        boolean isValid = isIntegerInTheRange(option, 6);
        while(! isValid){
            System.out.println("The option is not valid, please retype an option or type 'end' to finish execution");
            option = reader.nextLine();
            if (option.equalsIgnoreCase("end")){
                return;
            }
            isValid = isIntegerInTheRange(option, 6);
        }
        switch (option) {
            case "1":
                eventPresenter.viewTagsByEvent(userFacade, event);
                System.out.println("Type 1 to delete this event, and anything else to finish execution.");
                option = reader.nextLine();
                if (option.equals("1")){
                    eventPresenter.deleteEvent(userFacade, event);
                }
                break;
            case "2":
                eventPresenter.viewMemoByEvent(userFacade, event);
                System.out.println("Type 1 to delete this memo, and anything else to finish execution.");
                option = reader.nextLine();
                if (option.equals("1")){
                    eventPresenter.deleteMemo(userFacade, event);
                }
                break;
            case "3":
                eventPresenter.viewTagsByEvent(userFacade, event);
                System.out.println("Type 'delete' to delete the tag/tags of the event, " +
                        "and anything else to finish execution.");
                option = reader.nextLine();
                if (option.equalsIgnoreCase("delete")){
                    eventPresenter.deleteTag(userFacade, event);
                }
                break;
            case "4":
                System.out.println("Type 1 to delete the alerts of the event, and anything else to finish execution.");
                option = reader.nextLine();
                if (option.equals("1")){
                    eventPresenter.deleteAlerts(userFacade, event);
                }
                break;
        }
    }

    public void modify() {
        System.out.println("You could modify events, memos, and alters. \n" +
                "Please type the name of the object that you want to modify");
        ArrayList<String> items = new ArrayList<>(
                Arrays.asList("event", "memo", "alert"));
        String choice = reader.nextLine();
        while (!items.contains(choice)) {
            System.out.println("Invalid input. Please re-enter or type 'end' to finish execution.");
            choice = reader.nextLine();
            if (choice.equals("end")) {
                return;
            }
        }
        if (choice.equalsIgnoreCase("event")){
            modifyEvent();
        }
        else if (choice.equalsIgnoreCase("memo")){
            modifyMemo();
        }

    }

    public void modifyEvent(){
        System.out.println("Please type the name of the event that you want to modify.");
        String eventName = reader.nextLine();
        Event event = getExactEvent(eventName);
        if (event == null){ return; }
        System.out.println("Type 1 to change the name of the event. \n" +
                "Type 2 to change the start and end time of the event. \n");
        ArrayList<String> items = new ArrayList<>(
                Arrays.asList("1", "2"));
        String choice = reader.nextLine();
        while (!items.contains(choice)){
            System.out.println("Invalid input. Please re-enter or type 'end' to finish execution.");
            choice = reader.nextLine();
            if (choice.equals("end")){
                return;
            }
        }
        if (choice.equalsIgnoreCase("1")){
            System.out.println("Please type the new name of the event.");
            String newName = reader.nextLine();
            event.setName(newName);
            System.out.println("The name has been changed successfully.");
        }
        else if(choice.equalsIgnoreCase("2")){
            ArrayList<LocalDateTime> times = eventPresenter.eventTimesMaker();
            LocalDateTime startTime = times.get(0);
            LocalDateTime endTime = times.get(1);
            event.setStartTime(startTime);
            event.setEndTime(endTime);
            System.out.println("The time of the event has been modified successfully!");
        }
    }

    public void modifyMemo(){
        Memo memo = memoPresenter.selectMemoByTitle(userFacade);
        if (memo != null){
            System.out.println("Type 1 to change the title of the memo. \n" +
                    "Type 2 to change the content of the memo." );
            ArrayList<String> items = new ArrayList<>(
                    Arrays.asList("1", "2"));
            String choice = reader.nextLine();
            while (!items.contains(choice)){
                System.out.println("Invalid input. Please re-enter or type 'end' to finish execution.");
                choice = reader.nextLine();
                if (choice.equals("end")){
                    return;
                }
            }
            if (choice.equalsIgnoreCase("1")){
                System.out.println("Please type the new title of the memo:");
                String newTitle = reader.nextLine();
                memo.setTitle(newTitle);
                System.out.println("The title has been changed successfully!");
            }
            else if(choice.equalsIgnoreCase("2")){
                System.out.println("Please type the new content of the memo:");
                String newContent = reader.nextLine();
                memo.setContent(newContent);
                System.out.println("The content of the memo has been changed successfully");
            }
        }
    }

    public void create() {
        ArrayList<String> items = new ArrayList<>(
                Arrays.asList("event", "tag", "memo", "series", "todoList"));

        System.out.println("Please select the item you want to create \n Type(event, tag, memo, series)");
        String choice = reader.nextLine();
        while (!items.contains(choice)) {
            System.out.println("Invalid input. Please re-enter or type 'end' to finish execution.");
            choice = reader.nextLine();
            if (choice.equals("end")) {
                return;
            }
        }
        if (choice.equalsIgnoreCase("event")) {
            eventPresenter.createEvent(userFacade);
        } else if (choice.equalsIgnoreCase("tag")) {
            tagPresenter.createTag(userFacade);
        } else if (choice.equalsIgnoreCase("memo")) {
            memoPresenter.createNewMemo(userFacade);
        } else if (choice.equalsIgnoreCase("series")){
            seriesPresenter.createSeries(userFacade);
        }
        else{
            todoPresenter.createTodoList(userFacade);
        }
    }

    public void addToEvent(){
        System.out.println("Please select the event you would like to add to by typing an existing event's title: ");
        String name = reader.nextLine();
        Event event = getExactEvent(name);
        if (event == null){ return; }

        ArrayList<String> items = new ArrayList<>(
                Arrays.asList("add tag", "add memo", "add alert", "add calendar", "duplicate"));
        System.out.println("If you would like to add tag to it, type 'add tag'");
        System.out.println("If you would like to add memo to it, type 'add memo'");
        System.out.println("If you would like to add alert to it, type 'add alert'");
        System.out.println("If you would like to add calendar to if, type 'add calendar'");
        System.out.println("If you would like to create a duplicate event for it, type 'duplicate'");
        String choice = reader.nextLine();
        while (!items.contains(choice)){
            System.out.println("Invalid input. Please re-enter or type 'end' to finish execution.");
            choice = reader.nextLine();
            if (choice.equals("end")){
                return;
            }
        }
        if (choice.equalsIgnoreCase("add tag")){
            eventPresenter.addTagToEvent(userFacade, event);
        } else if (choice.equalsIgnoreCase("add memo")){
            eventPresenter.addMemoToEvent(userFacade, event);
        } else if (choice.equalsIgnoreCase("add alert")){
            eventPresenter.addAlertToEvent(userFacade, event);
        } else if (choice.equalsIgnoreCase("add calendar")){
            eventPresenter.addCalendarToEvent(userFacade, event);
        } else if (choice.equalsIgnoreCase("duplicate")){
            eventPresenter.createDuplicateEvent(userFacade, event);
        }
    }

    private Event getExactEvent(String name) {

        PriorityQueue<Event> events = userFacade.searchEventsByName(name);
        while (events == null){
            System.out.println("Event name not found, please re-enter or type 'end' to finish execution.");
            String choice = reader.nextLine();
            if (choice.equals("end")){
                return null;
            }
            events = userFacade.searchEventsByName(name);
        }
        if (events.size()==1){
            return events.remove();
        }
        System.out.println("Multiple events exist under this name, please specify its number as listed below");
        int count = 1;
        for (Event e: events) {
            System.out.println(count + ". " + e.toString());
            count ++;
        }
        String numStr = reader.nextLine();
        while (!isValidNumber(numStr) || Integer.parseInt(numStr)<1 || Integer.parseInt(numStr) > events.size()){
            System.out.println("Invalid number selection, please re-enter.");
            numStr = reader.nextLine();
        }
        int ord = Integer.parseInt(numStr);
        Event[] eventsList = (Event[]) events.toArray();
        return eventsList[ord-1];
    }

    private String getExactUser(){
        ArrayList<String> userNames = FileUtils.getAllUsers();
        String choice = null;
        String userName = reader.nextLine();
        while (!userNames.contains(userName)){
            System.out.println("User not found, please re-enter or type 'end' to finish execution.");
            choice = reader.nextLine();
            if (choice.equals("end")){
                return null;
            }
            userName = choice;
        }
        return userName;
    }


    public void addEventToSeries(){
        System.out.println("Please select the event you would like to add to by typing an existing event's title: ");
        String name = reader.nextLine();
        Event event = getExactEvent(name);
        seriesPresenter.addEventToSeries(userFacade, event);
    }


    public boolean isIntegerInTheRange(String intString, int endRange){
        long number;
        try{
            number = Long.parseLong(intString);
        } catch(NumberFormatException e) {
            return false;
        }
        return number > 0 && number <= endRange;
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

    public boolean isValidNumber(String numStr) {
        try{
            Integer.parseInt(numStr);
        } catch(NumberFormatException e) {
            return false;
        }
        return true;
    }
}

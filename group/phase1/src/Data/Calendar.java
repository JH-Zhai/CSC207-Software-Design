package Data;

import Presenters.*;

import java.util.*;

/**
 * The type Data.Calendar.
 */
public class Calendar {
    private EventPresenter eventPresenter;
    private MemoPresenter memoPresenter;
    private TagPresenter tagPresenter;
    private SeriesPresenter seriesPresenter;
    private UserFacade userFacade;
    private Scanner reader = new Scanner(System.in);

    public Calendar(UserFacade userFacade){
        this.userFacade = userFacade;
        eventPresenter = new EventPresenter();
        memoPresenter = new MemoPresenter();
        tagPresenter = new TagPresenter();
        seriesPresenter = new SeriesPresenter();
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
        eventPresenter.showAllEvent(userFacade);
        ArrayList<String> items = new ArrayList<>(Arrays.asList("1", "2", "3","4","5","6", "end"));
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
                case "end":
                    FileUtils.saveDatabaseToFile(userFacade);
                    return;
            }
        }
    }


    public void search() {
        ArrayList<String> options = new ArrayList<>(
                Arrays.asList("name", "date", "tag", "series"));

        System.out.println("Please select the items you want to display by typing one of the following: 'name','date'," +
                "'tag','series'");
        String choice = reader.nextLine();
        while (!options.contains(choice)) {
            System.out.println("Invalid input. Please re-enter or type 'end' to finish execution.");
            choice = reader.nextLine();
            if (choice.equals("end")) {
                return;
            }
        }
        if (choice.equalsIgnoreCase("name")) {
            eventPresenter.searchEventByEventName(userFacade);
        } else if (choice.equalsIgnoreCase("date")) {
            eventPresenter.searchEventByDate(userFacade);
        } else if (choice.equalsIgnoreCase("tag")) {
            eventPresenter.searchEventsByTag(userFacade);
        } else if (choice.equalsIgnoreCase("series")) {
            eventPresenter.searchEventsBySeries(userFacade);
        }
    }




//    Display all tags
//    Display all memos
//    Display all series

    public void display(){
        ArrayList<String> items = new ArrayList<>(
                Arrays.asList("pastEvents", "currentEvents", "futureEvents", "tags", "memos", "series"));

        System.out.println("Please select the items you want to display by typing one of the following: 'pastEvents'," +
                " 'currentEvents' , futureEvents', 'tags','memos'" +
                "'series'");
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
        }

    }

    public void viewEventInfo(){
        System.out.println("Please type the title of the event that you want to view:");
        String eventName = reader.nextLine();
        Event event = userFacade.searchEventByEventName(eventName);
        while (event == null){
            System.out.println("No event found, please view the events that are displayed above and type and valid title," +
                    "\n or type 'end' to end the execution.");
            eventName = reader.nextLine();
            if (eventName.equalsIgnoreCase("end")){
                return;
            }
            event = userFacade.searchEventByEventName(eventName);
        }
        System.out.println("Type 1 to view associated tags \n" + "Type 2 to view associated memo \n" +
                "Type 3 to view the alerts");
        String option = reader.nextLine();
        boolean isValid = isIntegerInTheRange(option, 4);
        while(! isValid){
            System.out.println("The option is not valid, please retype an option or type 'end' to finish execution");
            option = reader.nextLine();
            if (option.equalsIgnoreCase("end")){
                return;
            }
            isValid = isIntegerInTheRange(option, 4);
        }
        switch (option) {
            case "1":
                eventPresenter.viewTagsByEvent(userFacade, event.getName());
                break;
            case "2":
                eventPresenter.viewMemoByEvent(userFacade, event.getName());
                break;
            case "3":
                eventPresenter.viewAlertsByEvent(userFacade, event.getName());
                break;
        }

    }

    public void create() {
        ArrayList<String> items = new ArrayList<>(
                Arrays.asList("event", "tag", "memo", "series"));

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
        } else {
            seriesPresenter.createSeries(userFacade);
        }
    }

    public void addToEvent(){
        System.out.println("Please select the event you would like to add to by typing an existing event's title: ");
        Event event = userFacade.searchEventByEventName(reader.nextLine());
        while (event == null){
            System.out.println("Event not found, please re-enter or type 'end' to finish execution.");
            String choice = reader.nextLine();
            if (choice.equals("end")){
                return;
            }
            event = userFacade.searchEventByEventName(choice);
        }
            ArrayList<String> items = new ArrayList<>(
                    Arrays.asList("add tag", "add memo", "add alert"));
            System.out.println("If you would like to add tag to it, type 'add tag'");
            System.out.println("If you would like to add memo to it, type 'add memo'");
            System.out.println("If you would like to add alert to it, type 'add alert'");
            String choice = reader.nextLine();
            while (!items.contains(choice)){
                System.out.println("Invalid input. Please re-enter or type 'end' to finish execution.");
                choice = reader.nextLine();
                if (choice.equals("end")){
                    return;
                }
            }
            if (choice.equalsIgnoreCase("add tag")){
                eventPresenter.addTagToEvent(userFacade, event.getName());
            }
            else if (choice.equalsIgnoreCase("add memo")){
                eventPresenter.addMemoToEvent(userFacade, event.getName());
            }
            else if (choice.equalsIgnoreCase("add alert")){
                eventPresenter.addAlertToEvent(userFacade, event.getName());
            }
    }


    public void addEventToSeries(){
        seriesPresenter.addEventToSeries(userFacade);
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
}

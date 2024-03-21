package Presenters;
import Data.UserFacade;
import Data.Memo;
import Data.Event;
import java.util.ArrayList;

import java.util.Scanner;

public class MemoPresenter {
    public void displayAllMemos(UserFacade userFacade){
        userFacade.displayAllMemos();
    }

    public void selectMemoByTitle(UserFacade userFacade){
        Scanner reader = new Scanner(System.in);

        System.out.println("Please type the title of the memo that you want to choose");
        String name = reader.nextLine();
        while (true){
            Memo memo = userFacade.getMemoByTitle(name);
            if (!(memo == null)){
                System.out.println(memo);
                break;
            }
            System.out.println("No memo found, please retype the title, \n" + "or type 'end' to finish execution.");
            name = reader.nextLine();
            if (name.equals("end")){
                break;
            }
        }
    }

    public void viewAssociatedEvents(UserFacade userFacade){
        Scanner reader = new Scanner(System.in);
        System.out.println("Please type the title of the memo to view its associated events");
        String name = reader.nextLine();
        while (true){
            Memo memo = userFacade.getMemoByTitle(name);
            if (!(memo == null)) {
                ArrayList<Event> events = userFacade.getEventsByMemo(memo);
                if (events.size() == 0) {
                    System.out.println("No event is associated with this memo.");
                }
                else{
                    for (Event event: events){
                        System.out.println(event); }
                    }
                break;
            }
            else{
                System.out.println("No memo found, please retype the title, \n" + "or type 'end' to finish execution.");
                name = reader.nextLine();
                if (name.equals("end")){
                    break;
                }
            }
        }
    }

    public void addNewContent(UserFacade userFacade) {
        Scanner reader = new Scanner(System.in);
        System.out.println("Please type the title of the memo to add new content");
        String name = reader.nextLine();
        while (true) {
            Memo memo = userFacade.getMemoByTitle(name);
            if (!(memo == null)) {
                System.out.println("Please type the content that you want to add to this memo");
                String content = reader.nextLine();
                userFacade.addContent(memo, content);
                System.out.println("Content is added successfully!");
                break;
            } else {
                System.out.println("No memo found, please retype the title, \n" + "or type 'end' to finish execution.");
                name = reader.nextLine();
                if (name.equals("end")){
                    break;
                }
            }
        }
    }

    public void createNewMemo(UserFacade userFacade){
        Scanner reader = new Scanner(System.in);
        System.out.println("Please key in the title of this memo:");
        String title = reader.nextLine();
        while (!userFacade.createMemo(title)) {
            System.out.println("Sorry, the name of the memo is not available. Please try again. \n" +
                    "If you would like to return to the previous page, press 1.");
            title = reader.nextLine();
            if ("1".equals(title)){
                return;
            }
        }
        System.out.println("Memo has been created successfully, do you want to add content to this memo? \n"
                + "Type 1 to add content. \n"
                + "Type 2 to finish operation.");
        String option = reader.nextLine();
        while (!option.equals("1") && !option.equals("2")) {
            System.out.println("Your input is invalid. Please retype:");
            option = reader.nextLine();
        }
        if (option.equals("1")){
            System.out.println("Please type the content that you want to add to this memo");
            String content = reader.nextLine();
            Memo memo = userFacade.getMemoByTitle(title);
            userFacade.addContent(memo, content);
            System.out.println("Content is added successfully!");
        }
    }






}

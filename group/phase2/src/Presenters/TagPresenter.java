package Presenters;

import Data.Event;
import Data.Tag;
import Data.UserFacade;

import java.util.ArrayList;
import java.util.PriorityQueue;
import java.util.Scanner;

public class TagPresenter extends Thread{

    private Scanner reader = new Scanner(System.in);

    public void showAllTag(UserFacade userFacade){
            ArrayList<Tag> temp = userFacade.getAllTag();
            for (Tag tag : temp){
                System.out.println(tag);
            }
    }

    public void createTag(UserFacade userFacade){
        System.out.println("Please key in the title of this tag:");
            String title = reader.nextLine();
            while (!userFacade.createTag(title)) {
                System.out.println("Sorry, the name of the tag is not available. Please try again. \n" +
                        "If you would like to return to the previous page, type 1.");
                title = reader.nextLine();
                if ("1".equals(title)){
                    return;
                }
            }
            System.out.println("Tag added successfully");
    }

    public void showAllEventByTag(UserFacade userFacade){
            System.out.println("Please key in the title of tag you are searching with for:");
            String title = reader.nextLine();
            PriorityQueue<Event> temp = userFacade.searchEventsByTag(title);
            while (temp.isEmpty()) {
                System.out.println("Sorry, there is no related result. Please try again. \n" +
                        "If you would like to return to the previous page, type 1.");
                title = reader.nextLine();
                if ("1".equals(title)){
                    return;
                }
                temp = userFacade.searchEventsByTag(title);
            }
            for (Event event : temp) {
                System.out.println(event);
            }
    }






}

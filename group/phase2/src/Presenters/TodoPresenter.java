package Presenters;

import Data.Event;
import Data.TodoItem;
import Data.TodoList;
import Data.UserFacade;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.PriorityQueue;
import java.util.Scanner;

public class TodoPresenter {

    private Scanner reader = new Scanner(System.in);

    public void showAllTodoLists(UserFacade userFacade){
        for(TodoList tdl: userFacade.getAllTodoList()){
            System.out.println(tdl);
        }
    }
    public void createTodoList(UserFacade userFacade){
        System.out.println("Please type the header you would like for the Todo list:");
        String header = reader.nextLine();
        if (userFacade.createTodoList(header)){
            System.out.println("Todo list created successfully!");
        }
    }
    public void addTodoItem(UserFacade userFacade, Integer listId){
        System.out.println("Please type in the message you would like to add into this Todo list:");
        String content = reader.nextLine();
        if (userFacade.createTodoItem(content, listId)) {
            System.out.println("Success!");
        }
    }
    public void selectTodoList(UserFacade userFacade){
        System.out.println("Please select the Todo list you would like to see:");
        ArrayList<TodoList> todoList = userFacade.getAllTodoList();
        int count = 1;
        for (TodoList tdl:userFacade.getAllTodoList()) {
            System.out.println(count++ + ". " + tdl.toString());
        }
        String numStr = reader.nextLine();
        while (!isValidNumber(numStr) || Integer.parseInt(numStr)<1 || Integer.parseInt(numStr) >
                todoList.size()){
            System.out.println("Invalid number selection, please re-enter or type 'end' to terminate");
            numStr = reader.nextLine();
            if (numStr.equalsIgnoreCase("end")){
                return;
            }
        }
        int ord = Integer.parseInt(numStr);
        TodoList[] todoLists = (TodoList []) todoList.toArray();
        TodoList todo = todoLists[ord-1];
        Integer todoId = todo.getTodoId();
        userFacade.showAllTodoItemInList(todoId);
        System.out.println("If you would like to add a todo thing to this todo list, type '1'");
        System.out.println("If you would like to associate an existing todo thing to an event, type '2'");
        System.out.println("If you would like to check an todo thing in this todo list, type '3'");
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
            addTodoItem(userFacade, todoId);
        }
        else if(choice.equalsIgnoreCase("2")){
            addAssociation(userFacade, todo);
        }
        else{
            checkTodoItem(todo);
        }
    }
    public void checkTodoItem(TodoList todoList){
        TodoItem todoItem = selectTodoItemInsideList(todoList);
        todoItem.setChecked(!todoItem.getChecked());
        if (todoItem.getChecked()){
        System.out.println("Item checked!");}
        else{
            System.out.println("Item unchecked!");
        }
    }
    public TodoItem selectTodoItemInsideList(TodoList todoList){
        System.out.println("Please choose the item in the todo list from the following:");
        ArrayList<TodoItem> todoItems = todoList.getTodoItems();
        int count = 1;
        for (TodoItem tdi:todoItems) {
            System.out.println(count++ + ". " + tdi.toString());
        }
        String numStr = reader.nextLine();
        while (!isValidNumber(numStr) || Integer.parseInt(numStr)<1 || Integer.parseInt(numStr) >
                todoItems.size()){
            System.out.println("Invalid number selection, please re-enter or type 'end' to terminate");
            numStr = reader.nextLine();
            if (numStr.equalsIgnoreCase("end")){
                return null;
            }
        }
        int ord = Integer.parseInt(numStr);
        TodoItem[] todoItem = (TodoItem []) todoItems.toArray();
        return todoItem[ord-1];
    }

    public void addAssociation(UserFacade userFacade, TodoList todoList){
        TodoItem todoItem = selectTodoItemInsideList(todoList);
        if (todoItem == null){return;}
        System.out.println("Please type in the title of the event you would like to add to the thing in the todo " +
                "list:");
        String answer = reader.nextLine();
        Event choice = getExactEvent(answer, userFacade);
        if (choice != null){
        todoItem.addAssociation(choice.getEid());
            System.out.println("Success!");
        }

    }

    private Event getExactEvent(String name, UserFacade userFacade) {

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

    private boolean isValidNumber(String numStr) {
        try{
            Integer.parseInt(numStr);
        } catch(NumberFormatException e) {
            return false;
        }
        return true;
    }
}

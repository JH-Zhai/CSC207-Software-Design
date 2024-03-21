package Data;

import java.util.ArrayList;

public class TodoManager {
    private static int listCounter = 0;
    private static int itemCounter = 0;

    public boolean createTodoList(DataBase database, String header){
        Integer newId = listCounter;
        TodoList newly = new TodoList(header);
        newly.setTodoId(newId);
        listCounter++;
        database.todoLists.add(newly);
        return true;
    }
    public boolean createTodoItem(DataBase database, String content, Integer listId){
        Integer newId = itemCounter;
        database.todoItems.add(new TodoItem(content, newId, listId));
        itemCounter++;
        return true;
    }
    public void showAllTodoItemInList(DataBase dataBase, Integer listId){
        for (TodoItem tdi : searchTodoListById(dataBase, listId).getTodoItems()){
            System.out.println(tdi);
        }
    }

    public TodoList searchTodoListById(DataBase dataBase, Integer listId) {
        for (TodoList tdl: dataBase.todoLists){
            if(tdl.getTodoId().equals(listId)){
                return tdl;
            }
        }
        return null;
    }

    public boolean readEmptyTodoList(DataBase database, Integer todoListId, String header){
        TodoList tl = new TodoList(todoListId, header);
        database.todoLists.add(tl);
        if (listCounter <= todoListId){
            listCounter = todoListId + 1;
        }
        return true;
    }

    private ArrayList<TodoItem> getTodoItemsByIds(DataBase database, ArrayList<Integer> ids){//by zhaijiahong
        ArrayList<TodoItem> todoItems = new ArrayList<TodoItem>();
        for (Integer id: ids){
            for (TodoItem ti: database.todoItems){
                if (ti.gettItemId() == id){
                    todoItems.add(ti);
                }
            }
        }
        return todoItems;
    }

    public boolean readTodoList(DataBase database, Integer todoListId, String header, ArrayList<Integer> ids){
        ArrayList<TodoItem> todoItems = getTodoItemsByIds(database, ids);
        TodoList tl = new TodoList(todoListId, header, todoItems);
        database.todoLists.add(tl);
        if (listCounter <= todoListId){
            listCounter = todoListId + 1;
        }
        return true;
    }

    public boolean readTodoItem(DataBase database, Integer tItemId, Integer todoListId, Integer eventId, String content, boolean checked){
        TodoItem it = new TodoItem(tItemId,  todoListId,  eventId,  content,  checked);
        database.todoItems.add(it);
        if (itemCounter <= tItemId) {
            itemCounter = tItemId + 1;
        }
        return true;
    }

}

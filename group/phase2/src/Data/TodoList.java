package Data;

import java.util.ArrayList;

public class TodoList {
    private ArrayList<TodoItem> todoItems;
    private String header;
    private Integer TodoId;

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public TodoList(String header) {
        todoItems = new ArrayList<>();
        this.header = header;
    }

    public TodoList(Integer todoListId, String header) {
        todoItems = new ArrayList<>();
        this.header = header;
        this.TodoId = todoListId;
    }

    public TodoList(Integer todoListId, String header, ArrayList<TodoItem> items) {
        todoItems = new ArrayList<>();
        this.header = header;
        this.TodoId = todoListId;
        this.todoItems = items;
    }

    public ArrayList<TodoItem> getTodoItems() {
        return todoItems;
    }

    public ArrayList<Integer> getTodoItemsId(){
        ArrayList<Integer> re_var = new ArrayList<Integer>();
        for(TodoItem t : this.todoItems){
            re_var.add(t.gettItemId());
        }
        return re_var;
    }

    public void setTodoItems(ArrayList<TodoItem> todoItems) {
        this.todoItems = todoItems;
    }

    public Integer getTodoId() {
        return TodoId;
    }

    public void setTodoId(Integer todoId) {
        TodoId = todoId;
    }


    @Override
    public String toString() {
        return header;
//        StringBuilder string;
//        string = new StringBuilder();
//        for (TodoItem item : todoItems) {
//            string.append(item).append("/n");
//        }
//        return string.toString();
    }
}

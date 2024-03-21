package Data;

public class TodoItem {
    private String content;
    private Integer eventId = -1;
    private Integer tItemId;
    private boolean checked = false;
    private Integer TodoListId;

    public TodoItem(String content, Integer tItemId, Integer todoListId) {
        this.content = content;
        this.tItemId = tItemId;
        TodoListId = todoListId;
    }

    public TodoItem(Integer tItemId, Integer todoListId, Integer eventId, String content, boolean checked) {
        this.content = content;
        this.tItemId = tItemId;
        TodoListId = todoListId;
        this.eventId = eventId;
        this.checked = checked;
    }


    public String getContent() {
        return content;
    }
    public void setContent(String content) {
        this.content = content;
    }

    public Integer getEventId() {
        return eventId;
    }

    public void setEventId(Integer eventId) {
        this.eventId = eventId;
    }

    public Integer gettItemId() {
        return tItemId;
    }

    public void settItemId(Integer tItemId) {
        this.tItemId = tItemId;
    }

    public Boolean getChecked() {
        return checked;
    }

    public void setChecked(Boolean checked) {
        this.checked = checked;
    }

    public Integer getTodoListId() {
        return TodoListId;
    }

    public void setTodoListId(Integer todoListId) {
        TodoListId = todoListId;
    }

    public void addAssociation(Integer eventId){
        setEventId(eventId);
    }
    public String toString(){
        if (checked){
        return "checked" + content;
        }
        else{
            return "Todo" + content;
        }
    }

}

package Data;

/**
 * The type Event and memo.
 */
public class EventAndMemo {
    private Event event;
    private Memo memo;
    private Integer eventId;
    private Integer memoId;

    /**
     * Constructor of a Data.EventAndMemo object.  @param event the event
     *
     * @param memo the memo
     */
    public EventAndMemo(Event event, Memo memo){
        this.event = event;
        this.memo = memo;
        this.eventId = event.getEid();
        this.memoId = memo.getId();
    }

    /**
     * A getter which get the event in the Data.EventAndMemo object.  @return the event
     */
    public Event getEvent(){
        return event;
    }

    /**
     * A getter which get the memo in the Data.EventAndMemo object.  @return the memo
     */
    public Memo getMemo(){
        return memo;
    }

    /**
     * A getter which get the id of the event which is stored in the Data.EventAndMemo object.  @return the string
     */
    public Integer getEventId(){
        return eventId;
    }

    /**
     * A getter which get the id of the memo which is stored in the Data.EventAndMemo object.  @return the string
     */
    public Integer getMemoId(){
        return memoId;
    }
}

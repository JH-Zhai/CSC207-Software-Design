package Data;

import java.util.ArrayList;
import java.util.PriorityQueue;
/**
 * The type Data base.
 */
public class DataBase {
    /**
     * The Events.
     */
    protected PriorityQueue<Event> events = new PriorityQueue<>();
    /**
     * The Tags.
     */
    protected ArrayList<Tag> tags = new ArrayList<>();
    /**
     * The Memos.
     */
    protected ArrayList<Memo> memos = new ArrayList<>();
    /**
     * The Data.Series.
     */
    protected ArrayList<Series> series = new ArrayList<>();
    /**
     * The Data.Series.
     */
    protected PriorityQueue<Alert> alerts = new PriorityQueue<>();
    /**
     * The Data.Event and tag.
     */
    protected ArrayList<EventAndTag> eventAndTag = new ArrayList<>();
    /**
     * The Data.Event and memos.
     */
    protected ArrayList<EventAndMemo> eventAndMemos = new ArrayList<>();
    /**
     * The Data.Event and series.
     */
    protected ArrayList<EventAndSeries> eventAndSeries = new ArrayList<>();
    /**
     * The Alerts.
     */

//    protected PriorityQueue<Alert> pastAlertQueue;

}

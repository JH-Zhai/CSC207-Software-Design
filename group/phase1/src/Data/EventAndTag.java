package Data;

/**
 * The type Data.Event and tag.
 */
public class EventAndTag {

    private Integer eventId;
    private Integer tagId;
    private Event event;
    private Tag tag;

    public EventAndTag(Event event, Tag tag) {
        this.event = event;
        this.tag = tag;
        this.eventId = event.getEid();
        this.tagId = tag.getId();
    }

    /**
     * Gets event id.
     *
     * @return the event id
     */
    public Integer getEventId() {
        return eventId;
    }

    /**
     * Gets tag id.
     *
     * @return the tag id
     */
    public Integer getTagId() {
        return tagId;
    }

    /**
     * Gets event.
     *
     * @return the event
     */
    public Event getEvent() {
        return event;
    }

    /**
     * Gets tag.
     *
     * @return the tag
     */
    public Tag getTag() {
        return tag;
    }
}

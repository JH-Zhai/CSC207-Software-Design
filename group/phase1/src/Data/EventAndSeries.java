package Data;

/**
 * The type Data.Event and series.
 */
public class EventAndSeries {
    private Integer eventId;
    private Integer seriesId;
    private Event event;
    private Series series;

    public EventAndSeries(Event event, Series series) {
        this.event = event;
        this.series = series;
        this.eventId = event.getEid();
        this.seriesId = series.getId();
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
     * Gets series id.
     *
     * @return the series id
     */
    public Integer getSeriesId() {
        return seriesId;
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
     * Gets series.
     *
     * @return the series
     */
    public Series getSeries() {
        return series;
    }
}

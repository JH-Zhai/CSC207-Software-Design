package Data;

import java.util.ArrayList;

/**
 * The type Data.Series manager.
 */
public class SeriesManager {
    private static int counter = 0;
    /**
     * Create series boolean.
     *
     * @param name the name
     * @param dataBase the dataBase
     * @return the boolean
     */
    public Series createSeries(DataBase dataBase, String name) {
        for (Series series: dataBase.series){
            if (series.getName().equals(name)){
                return series;
            }
        }
        counter += 1;
        Series newSeries = new Series(name, counter);
        dataBase.series.add(newSeries);
        return newSeries;
    }

    public void displayAllSeries(DataBase dataBase){
        StringBuilder allSeries = new StringBuilder("Your have these series:");
        for (Series series: dataBase.series){
            allSeries.append("\n").append(series.getName());
            allSeries.append("\t");
            allSeries.append(seriesAssociatedEvents(dataBase, series));
        }
        allSeries.append("\n");
        System.out.print(allSeries);
    }

    public String seriesAssociatedEvents(DataBase dataBase, Series series){
        StringBuilder associatedEvents = new StringBuilder();
        for (EventAndSeries eas : dataBase.eventAndSeries){
            if (eas.getSeries().getName().equals(series.getName())){
                associatedEvents.append(eas.getEvent().getName()).append("\t");
            }
        }
        return associatedEvents.toString();
    }


    /**
     * Create series series.
     *
     * @param name the name
     * @param dataBase the dataBase
     * @param id  the sid
     * @return the series
     */
    public Boolean readSeries(DataBase dataBase, String name, Integer id){
        for (Series series: dataBase.series){
            if (series.getName().equals(name)){
                return false;
            }
        }
        if (counter<= id){
            counter = id + 1;
        }
        Series newSeries = new Series(name,id);
        dataBase.series.add(newSeries);
        return true;
   }

    /**
     * Link single event to series event and series.
     * return true if event is add successfully.
     * return false if event is already in dataBase.
     *
     * @param dataBase the data base
     * @param event    the event
     * @param series   the series
     * @return the event and series
     */
    public boolean linkSingleEventToSeries(DataBase dataBase, Event event, Series series){
        String eventName = event.getName();
        String seriesName = series.getName();
        for (EventAndSeries eas : dataBase.eventAndSeries){
            if (eas.getEvent().getName().equals(eventName) && eas.getSeries().getName().equals(seriesName)){
                return false;
            }
        }
        EventAndSeries eventAndSeries = new EventAndSeries(event, series);
        dataBase.eventAndSeries.add(eventAndSeries);
        return true;
    }

    /**
     * Link multiple events to series array list.
     * return true iff all events are added to dataBase.
     * return false if one of event is not add successfully.
     *
     * @param dataBase the data base
     * @param events   the events
     * @param series   the series
     * @return the array list
     */
    public boolean linkMultipleEventsToSeries(DataBase dataBase, ArrayList<Event> events, Series series){
        boolean allAdded = true;
        for (Event e: events) {
            allAdded = allAdded && linkSingleEventToSeries(dataBase, e, series);
        }
        return allAdded;
    }

    public Series getSeriesById(DataBase dataBase, Integer id){
        for(Series series: dataBase.series){
            if(series.getId().equals(id)){
                return series;
            }
        }
        return null;
    }

    public Series getSeriesByName(DataBase dataBase, String seriesName){
        for(Series series: dataBase.series){
            if(series.getName().equals(seriesName)){
                return series;
            }
        }
        return null;
    }


}

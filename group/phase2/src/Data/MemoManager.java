package Data;

import java.util.ArrayList;

/**
 * The type Memo manager.
 */
public class MemoManager {
    private static Integer counter = 0;

    /** All methods in MemoManager must be static. */

    /**
     * Create a new Memo and return true is created successfully and false otherwise.  @param database the database
     *
     * @param title the title
     * @return the boolean
     */
    public boolean createMemo(DataBase database, String title){
        for (Memo memo : database.memos){
            if (memo.getTitle().equals(title)){
                return false;
            }
        }
        Integer newId = counter;
        database.memos.add(new Memo(title, newId));
        counter ++;
        return true;
    }

    /**
     * Another way to create a new memo.  @param database the database
     *
     * @param title the title
     * @param mid   the mid
     * @return the boolean
     */
    public boolean readMemo(DataBase database, String title, Integer mid){
        for (Memo memo : database.memos){
            if (memo.getTitle().equals(title)){
                return false;
            }
        }
        database.memos.add(new Memo(title, mid));
        if(counter <= mid){
            counter = mid + 1;
        }
        return true;
    }


    /**
     * Add a memo to a given event, return true if successfully added and false otherwise.  @param database the database
     *
     * @param event the event
     * @param memo  the memo
     * @return the boolean
     */
    public boolean addMemo(DataBase database, Event event, Memo memo){
        for(EventAndMemo eventAndMemo: database.eventAndMemos){
            if (eventAndMemo.getEventId().equals(event.getEid()) &&
                    eventAndMemo.getMemoId().equals(memo.getId())){
                return false;
            }
        }
        database.eventAndMemos.add(new EventAndMemo(event, memo));
        return true;
    }

    public ArrayList<Event> getEventsByMemo(DataBase dataBase, Memo memo){
        ArrayList<Event> res = new ArrayList<Event>();
        for (EventAndMemo eventAndMemo : dataBase.eventAndMemos){
            if (eventAndMemo.getMemoId().equals(memo.getId())){
                res.add(eventAndMemo.getEvent());
            }
        }
        return res;
    }

    public Memo getMemoByEvent(DataBase dataBase, Event event){
        for (EventAndMemo eventAndMemo : dataBase.eventAndMemos){
            if (eventAndMemo.getEventId().equals(event.getEid())){
                return eventAndMemo.getMemo();
            }
        }
        return null;
    }

    public Memo getMemoById(DataBase dataBase, Integer mid){
        for(Memo memo: dataBase.memos){
            if(memo.getId().equals(mid)){
                return memo;
            }
        }
        return null;
    }

    public Memo getMemoByTitle(DataBase dataBase, String memoTitle){
        for(Memo memo: dataBase.memos){
            if(memo.getTitle().equals(memoTitle)){
                return memo;
            }
        }
        return null;
    }

    public void displayAllMemos(DataBase dataBase){
        for(Memo memo: dataBase.memos){
            System.out.println(memo + "\n");
        }
    }


}

package Data;

import java.util.ArrayList;

/**
 * The type Tag manager.
 */
public class TagManager {
    private static int counter = 0;


    /**
     * Create tag boolean.
     *
     * @param dataBase the data base
     * @param title    the title
     * @return the boolean
     */
    public Boolean createTag(DataBase dataBase, String title){
        for (Tag tag : dataBase.tags){
            if (tag.getTitle().equals(title)){
                return false;
            }
        }
        Integer newId = counter;
        counter ++;
        dataBase.tags.add(new Tag(title, newId));
        return true;
    }
    public ArrayList<Tag> getAllTag(DataBase dataBase){
        ArrayList<Tag> tags = (ArrayList<Tag>) dataBase.tags.clone();
        return tags;
    }

    public Boolean readTag(DataBase dataBase, String title, Integer tid) {
        for (Tag tag: dataBase.tags){
            if (tag.getTitle().equals(title)){
                return false;
            }
        }
        if (counter <= tid) {
            counter = tid + 1;
        }
        dataBase.tags.add(new Tag(title, tid));
        return true;
    }

    public Tag getTagByTitle(DataBase dataBase, String title){
        for (Tag tag: dataBase.tags){
            if (tag.getTitle().equals(title)){
                return tag;
            }
        }
        return null;
    }


    /**
     * Add tag boolean.
     *
     * @param database the data base
     * @param event    the event
     * @param tag      the tag
     * @return the boolean
     */
    public Boolean addTag(DataBase database, Event event, Tag tag){
        for(EventAndTag eventAndTag: database.eventAndTag){
            if (eventAndTag.getEventId().equals(event.getEid()) &&
                    eventAndTag.getTagId().equals(tag.getId())){
                return false;
            }
        }
        database.eventAndTag.add(new EventAndTag(event, tag));
        return true;
    }

    /**
     * Remove boolean.
     *
     * @param dataBase the data base
     * @param event    the event
     * @return the boolean
     */
    public Boolean remove(DataBase dataBase, Event event, Tag tag){
        for (EventAndTag eventAndTag: dataBase.eventAndTag){
            if (eventAndTag.getEvent() == event && eventAndTag.getTag() == tag){
                return dataBase.eventAndTag.remove(eventAndTag);
            }
        }
        return false;
    }

    public Tag getTagById(DataBase dataBase, Integer tid){
        for(Tag tag: dataBase.tags){
            if(tag.getId().equals(tid)){
                return tag;
            }
        }
        return null;
    }

    public ArrayList<Tag> getTagsByEvent(DataBase dataBase, Integer eid){
        ArrayList<Tag> tags = new ArrayList<>();
        for (EventAndTag eventAndTag: dataBase.eventAndTag){
            if (eventAndTag.getEvent().getEid().equals(eid)){
                tags.add(eventAndTag.getTag());
            }
        }
       return tags;
    }

    public void deleteTag(DataBase dataBase, Event event, Tag tag){
        int eventId = event.getEid();
        int tagId = tag.getId();
        dataBase.eventAndTag.removeIf(eventAndTag -> eventAndTag.getEventId() == eventId && eventAndTag.getTagId() == tagId);
    }
}

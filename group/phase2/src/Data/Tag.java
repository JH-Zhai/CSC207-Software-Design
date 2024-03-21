package Data;

import java.util.ArrayList;

/**
 * The type Data.Tag.
 */
public class Tag {
    private String title;
    private Integer tId;

    /**
     * Instantiates a new Data.Tag.
     *
     * @param name the name
     * @param tId  the t id
     */
    public Tag(String name, Integer tId){
        this.title = name;
        this.tId = tId;
    }

    /**
     * Gets id.
     *
     * @return the id
     */
    public Integer getId() {
        return tId;
    }

    /**
     * Sets title.
     *
     * @param title the title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Gets title.
     *
     * @return the title
     */
    public String getTitle() {
        return title;
    }

    @Override
    public String toString(){
        return getTitle();
    }

}

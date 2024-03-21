package Data;

/**
 * The type Data.Series.
 */
public class Series {
    private Integer id;
    private String name;


    /**
     * Instantiates a new Data.Series.
     *
     * @param name the name
     * @param id  the sid
     */
    public Series(String name, Integer id) {
        this.name = name;
        this.id = id;
    }

    /**
     * Gets name.
     *
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets name.
     *
     * @param name the name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets sid.
     *
     * @return the Id
     */
    public Integer getId() {
        return id;
    }

    @Override
    public String toString() {
        return name;
    }

}

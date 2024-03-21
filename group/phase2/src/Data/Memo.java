package Data;
/**
 * A memo which could be associated with one or more events.
 */
public class Memo {

    /**
     * The title of a memo.
     */
    private String title;

    /**
     * The content of the memo.
     */
    private String content;

    /**
     * The id of the memo, starting with "m".
     */
    private Integer mid;

    public Memo(String title, Integer mid) {
        this.title = title;
        this.mid = mid;
        this.content = "";
    }

    public String getContent() {
        return content;
    }

    /**
     * A getter which get the id of the memo.
     *
     * @return the id
     */
    public Integer getId() {
        return this.mid;
    }


    @Override
    public String toString() {
        return "Title: " + this.title + "\n" + "Content: " + this.content;
    }

    /**
     * Add the content of the memo.  @param newContent the new content
     */
    public void addContent(String newContent) {
        this.content += newContent;
    }

    /**
     * A getter which get the title of the memo.  @return the title
     */
    public String getTitle() {
        return this.title;
    }

    public void deleteContent(){
        this.content = "";
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setContent(String newContent){this.content = newContent;}
}

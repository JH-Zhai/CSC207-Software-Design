package farmyard;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class ANIMAL_MANURE {


    /** How this piece of feces appears on the screen. */
    private String appearance;

    /** My colour. Ah, the vagaries of British vs. US spelling. */
    Color colour;

    /** Use for random movement left and right.  */
    public double d;

    /** This feces's first coordinate. */
    private int x;
    /** This feces's second coordinate. */
    protected int y;

    /** The apperance of the feces. */
    static String manure_appearnce = ".";

    /**
     * Constructs a new feces at the specified cursor location (x, y).
     *
     * @param  x  the x co-ordinate of the food's cursor location.
     * @param  y  the y co-ordinate of the food's cursor location.
     */
    public ANIMAL_MANURE() {
        colour = Color.BLACK.darker().darker().darker();
        appearance = ANIMAL_MANURE.manure_appearnce;
    }

    /**
     * Set this item's location.
     * @param a the first coordinate.
     * @param b  the second coordinate.
     */
    public void setLocation(int a, int b) {
        // set x to a
        x = a;
        // set y to b
        y = b;
    }

    /**
     * Draws the given string in the given graphics context at
     * at the given cursor location.
     *
     * @param  g  the graphics context in which to draw the string.
     * @param  s  the string to draw.
     * @param  x  the x-coordinate of the string's cursor location.
     * @param  y  the y-coordinate of the string's cursor location.
     */
    void drawString(GraphicsContext g, String s, int x, int y) {
        g.setFill(colour);
        g.fillText(s, y * 10, x * 6);
    }


    /**
     * Draws this farm pen item.
     *
     * @param  g  the graphics context in which to draw this item.
     */
    public void draw(GraphicsContext g) {
        drawString(g, appearance, y, x);
    }


}

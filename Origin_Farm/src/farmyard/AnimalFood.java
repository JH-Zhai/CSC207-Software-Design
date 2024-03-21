package farmyard;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

/**
 * Animal Food
 */

public class AnimalFood {

    /** How this piece of food appears on the screen. */
    private String appearance;

    /** My colour. Ah, the vagaries of British vs. US spelling. */
    Color colour;

    /** Use for random movement left and right.  */
    public double d;

    /** This food's first coordinate. */
    private int x;
    /** This food's second coordinate. */
    protected int y;

    /** The apperance of the food. */
    private static final String test = "%";

    /**
     * Constructs a new bubble at the specified cursor location (x, y).
     *
     * @param  x  the x co-ordinate of the food's cursor location.
     * @param  y  the y co-ordinate of the food's cursor location.
     */
    public AnimalFood() {
        colour = Color.GRAY.darker().darker().darker();
        // start off with o as the appearance cause all animal food looks the same
        appearance = AnimalFood.test;
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



    /**
     * Causes this item to take its turn in the farm-pen simulation, blown due to strong winds.
     * Up in this case
     */
    public void blownUp() {

        // Move upwards
        y--;
        x = x; // no change left or right

    }
    /**
     * Causes this item to take its turn in the farm-pen simulation, blown due to strong winds.
     * Down in this case
     */
    public void blownDown() {

        // Move downwards
        y++;
        x = x; // no change left or right

    }
    /**
     * Causes this item to take its turn in the farm-pen simulation, blown due to strong winds.
     * Up and Left in this case
     */
    public void blownLeft() {

        // Move upwards.
        y = y;
        x = x - 1;// left
    }
    /**
     * Causes this item to take its turn in the farm-pen simulation, blown due to strong winds.
     * Up and Right in this case
     */
    public void blownRight() {

        // Move upwards.
        y = y;
        x = x + 1;// right
    }

}

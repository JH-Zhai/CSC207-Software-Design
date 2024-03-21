package farmyard;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

/** An egg that a farmer collects. */
public class Egg {

    /** How this piece of food appears on the screen. */
    private char eggShape = 'o';

    /** This food's first coordinate. */
    private int x;
    /** This food's second coordinate. */
    protected int y;

    /**
     * Set this item's location.
     * @param hereR the horizontalcoordinate.
     * @param hereC  the vertical  coordinate.
     */
    public void setLocation(int hereR, int hereC) {
        x = hereR;
        // set y to b
        y = hereC;
    }

    /**
     * Draws this farm pen item.
     *
     * @param  g  the graphics context in which to draw this item.
     */
    public void draw(GraphicsContext g) {
        System.out.println("Brown stuff");
        g.setFill(Color.ROSYBROWN);
        g.fillText(String.valueOf(eggShape), y * 10, x * 6);

    }

    int getX() {
        return x;
    }


}

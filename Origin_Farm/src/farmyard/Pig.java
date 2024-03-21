package farmyard;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Pig {
    /** How this Pig appears on the screen. */
    String appearance;

    /** Indicates whether this Pig is moving right. */
    boolean goingRight;

    /** This Pig's first coordinate. */
    int r;
    /** The colour of this Pig. */
    Color colour;
    /** This Pig's second coordinate. */
    private int c;

    /** Constructs a new Pig. */
    public Pig() {
        colour = Color.PINK.darker().darker().darker();
        appearance = ":(8)";
        goingRight = true;
    }

    /**
     * Set this item's location.
     *
     * @param a the first coordinate.
     * @param b the second coordinate.
     */
    public void setLocation(int a, int b) {
        r = a;
        c = b;
    }

    /** Build and initialize this Pig's forward and backward appearances. */
    private String reverseAppearance() {
        String reverse = "";
        for (int i = appearance.length() - 1; i >= 0; i--) {
            switch (appearance.charAt(i)) {
                case '\\':
                    reverse += '/';
                    break;
                case '/':
                    reverse += '\\';
                    break;
                case ')':
                    reverse += '(';
                    break;
                case '(':
                    reverse += ')';
                    break;
                case '>':
                    reverse += '<';
                    break;
                case '<':
                    reverse += '>';
                    break;
                case '}':
                    reverse += '{';
                    break;
                case '{':
                    reverse += '}';
                    break;
                case '[':
                    reverse += ']';
                    break;
                case ']':
                    reverse += '[';
                    break;
                default:
                    reverse += appearance.charAt(i);
                    break;
            }
        }

        return reverse;
    }

    /** Turns this pig around, causing it to reverse direction. */
    protected void turnAround() {
        goingRight = !goingRight;
        if (goingRight) {
            appearance = reverseAppearance();
        } else {
            appearance = reverseAppearance();
        }
    }

    /**
     * Draws the given string in the given graphics context at at the given cursor location.
     *
     * @param g the graphics context in which to draw the string.
     * @param s the string to draw.
     * @param x the x-coordinate of the string's cursor location.
     * @param y the y-coordinate of the string's cursor location.
     */
    void drawString(GraphicsContext g, String s, int x, int y) {
        g.setFill(colour);
        g.fillText(s, y * 10, x * 6);
    }

    /**
     * Draws this farm pen item.
     *
     * @param g the graphics context in which to draw this item.
     */
    public void draw(GraphicsContext g) {
        drawString(g, appearance, r, c);
    }

    /** Causes this item to take its turn in the farm-pen simulation. */
    public void move() {

        double d3 = Math.random();
        if (d3 < 0.1) {
            turnAround();
        }

        // Move one spot to the right or left.
        if (goingRight) {
            c += 1;
        } else {
            c -= 1;
        }

        // Sometimes we digest.
        double d = Math.random();
        if (d < 0.2) {
            clearStomach();
        }
    }

    /**
     * Helps animal clear stomach
     *
     * @return
     */
    private final boolean clearStomach() {
        System.out.println("Pig stink");

        ANIMAL_MANURE newManure = new ANIMAL_MANURE();
        newManure.manure_appearnce = "*";
        newManure.setLocation(c, r);

        Human.myFarmAnimals[r][c] = newManure;

        return true;
    }
}

package farmyard;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Chicken {
    /** How this Chicken appears on the screen. */
    String appearance;

    /** Indicates whether this Chicken is moving right. */
    boolean goingRight;

    /** This Chicken's first coordinate. */
    int r;
    /** The colour of this Chicken. */
    Color colour;
    /** This Chicken's second coordinate. */
    private int c;

    /** Constructs a new Chicken. */
    public Chicken() {
        colour = Color.RED;
        appearance = "/'/>";
        goingRight = true;
    }

    /** Set this item's location. */
    public static Egg aneggishere() {
        for (int r = 0; r != Human.myFarmAnimals.length; r++)
            for (int c = 0; c != Human.myFarmAnimals[0].length; c++)
                if (Human.myFarmAnimals[r][c] instanceof Egg) return (Egg) Human.myFarmAnimals[r][c];
        return null;
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

    /** Build and initialize this Chicken's forward and backward appearances. */
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

    /** Turns this fish around, causing it to reverse direction. */
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

        // Sometimes food doesn't sit well in the stomach, so I have to clear my stomach
        double d1 = Math.random();
        if (d1 < 0.2) {
            digest();
        }

        // Move one spot to the right or left.
        if (goingRight) {
            c += 1;
        } else {
            c -= 1;
        }

        // Every now and then lay an egg.
        double d2 = Math.random();
        if (d1 < 0.1) {
            layEgg();
        }

        double d3 = Math.random();
        if (d1 < 0.1) {
            turnAround();
        }
    }

    /** Lay an egg. */
    private void layEgg() {
        System.out.println("Breakfast! " + "Egg loc: " + c + " " + r);
        int hereC = c;
        int hereR = r;
        Egg egg = new Egg();
        egg.setLocation(hereC, hereR);

        Human.myFarmAnimals[hereC][hereR] = egg;
    }

    /**
     * Finish digesting
     *
     * @return
     */
    private final boolean digest() {
        System.out.println("New stuff to make things grow.");

        ANIMAL_MANURE getTheScoop = new ANIMAL_MANURE();
        getTheScoop.manure_appearnce = ".";
        getTheScoop.setLocation(c, r);

        Human.myFarmAnimals[r][c] = getTheScoop;

        return true;
    }
}

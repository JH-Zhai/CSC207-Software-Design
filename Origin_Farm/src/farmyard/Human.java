package farmyard;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.util.ArrayList;

/**
 * A Human
 */

public class Human {

    /**
     * (int)(640/6) columns, (int)(480/10) rows.
     */
    public static Object[][] myFarmAnimals = new Object[(int) (480 / 10)][(int) (640 / 6)];
    /** How this lovely human appears on the screen. */
    String appearance;

    /** Indicates whether this human is moving right. */
    boolean goingRight;

    /** This human's first coordinate. */
    int r;
    /** This human's second coordinate. */
    private int c;

    private ArrayList<Egg> myBasket = new ArrayList<Egg>();

    private GraphicsContext g;




    /** The animal food the human is feeding his/her beloved animals. */
    static final String thing = ".";


    /**
     * Constructs a new Human.
     */
    public Human() {

        appearance = "human";
        goingRight = true;
    }



    /**
     * Set this item's location.
     * @param a the first coordinate.
     * @param b  the second coordinate.
     */
    public void setLocation(int a, int b) {
        r = a;
        c = b;
    }



    /**
     * Causes human to drop down 4 piece s of food all around.
     */
    protected void dropFood() {
        AnimalFood fooood = new AnimalFood();
        fooood.setLocation(c -1 , r -1 );
        myFarmAnimals[r - 1][c - 1] = fooood;

        fooood = new AnimalFood();
        fooood.setLocation(c -1 , r +1 );
        myFarmAnimals[r + 1][c - 1] = fooood;

        fooood = new AnimalFood();
        fooood.setLocation(c +1 , r -1 );
        myFarmAnimals[r - 1][c + 1] = fooood;

        fooood = new AnimalFood();
        fooood.setLocation(c +1 , r +1 );
        myFarmAnimals[r + 1][c + 1] = fooood;


    }



    /**
     * Build and initialize this human's forward and backward
     * appearances.
     */
    private String reverseAppearance() {
        String reverse = "";
        for (int i=appearance.length()-1; i>=0; i--) {
            switch (appearance.charAt(i)) {
                case ')': reverse += '('; break;
                case '(': reverse += ')'; break;
                case '>': reverse += '<'; break;
                case '<': reverse += '>'; break;
                case '}': reverse += '{'; break;
                case '{': reverse += '}'; break;
                case '[': reverse += ']'; break;
                case ']': reverse += '['; break;
                default: reverse += appearance.charAt(i); break;
            }
        }

        return reverse;
    }



    /**
     * Turns this human around, causing it to reverse direction.
     */
    protected void turnAround() {
        goingRight = !goingRight;
        if (goingRight) {
            appearance = reverseAppearance();
        } else {
            appearance = reverseAppearance();
        }
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
        g.fillText(s, y * 10, x * 6);                        g.fillText("Eggs: " + myBasket.size(), 2 * 10, 2 * 6);

    }



    /**
     * Draws this farm pen item.
     *
     * @param  g  the graphics context in which to draw this item.
     */
    public void draw(GraphicsContext g) {
        this.g = g; g.setFill(Color.SANDYBROWN.darker());
        drawString(g, appearance, r, c);
    }


    Egg target = null;

    /**
     * Causes this item to take its turn in the farm-pensimulation.
     */
    public void move() {

        if (target == null) {
            target = Chicken.aneggishere();
        }

        if (target != null) {
            System.out.println("Target acquired: " + target.getX() + " " + target.y + "| Me: " + c + " " + r);
            // Am I on an egg?
            if (r == target. getX() && c == target.y) {
                System.out.println("Egg!");
                this.myBasket.add(target);
                target = null;
                if (myBasket.size() % 12 == 0) {
                    System.out.println("Dozen!");
                    g.fillText("Eggs: " + myBasket.size(), 1 * 10, 1 * 6);
                }

            }

            else {

                // move toward the egg
                if (r < target.getX()) {
                    r += 1;
                } else {
                    r -= 1;
                }
                if (c < target.y) {
                    c += 1;
                } else {
                    c -= 1;
                }
            }}

        else // no egg to pick up
            // Move one spot to the right or left.
            if (goingRight) {
                // Figure out whether to move up or down, or neither.
                double d = Math.random();
                if (d < 0.1) {
                    r += 1;
                } else if (d < 0.2) {
                    r -= 1;
                }
                c += 1;
            } else {
                // Figure out whether to move up or down, or neither.
                double d = Math.random();
                if (d < 0.1) {
                    r += 1;
                } else if (d < 0.2) {
                    r -= 1;
                }
                c -= 1;
            }

        // Figure out whether I should drop food.
        double d = Math.random();
        if (d < 0.05) { dropFood(); }

        // Figure out whether I turn around.
        d = Math.random();
        if (d < 0.1) { turnAround(); }

    }


}

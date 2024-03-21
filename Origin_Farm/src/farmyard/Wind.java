package farmyard;

/** If the iwnd was last blowing up then it is likely to keep blowing up. Same for left/right.*/
public class Wind {

    // Which way was the windod last blowing up or down?
    private static int lastUp = 0;

    // Which way was the windod last blowing left or right?
    private static int lastLeft = 0;
    /**
     * -1 if the wind is blowing up
     * 1 if the wind is blowing down
     * 0 if the wind isn't blowing
     * Keep blowing the same direction 30% the time.
     * Turn around 10% of the time.
     * Otherwise no wind.
     * @return
     */
    public static int windBlowingUp() {

        if (lastUp != 0)
            if (Math.random() < 0.3) {
                return lastUp;
            } else if (Math.random() < 0.1) {
                lastUp = -lastUp;
                return lastUp;
            } else
                lastUp = 0;
        else
            // lastUp was zero. Change wind 10% updown each.
            if (Math.random() < 0.1) {
                lastUp = -1;
                return lastUp;
            } else if (Math.random() < 0.1) {
                lastUp = 1;
                return lastUp;
            }
        return lastUp;
    }

    /**
     * -1 if the wind is blowing left
     * 1 if the wind is blowing right
     * 0 if the wind isn't blowing
     * @return
     */
    public static int windBlowingLeft() {
        if (lastLeft != 0)
            if (Math.random() < 0.3) {
                return lastLeft;
            } else if (Math.random() < 0.1) {
                lastLeft = -lastLeft;
                return lastLeft;
            } else
                lastLeft = 0;
        else
            // lastUp was zero. Change wind 10% updown each.
            if (Math.random() < 0.1) {
                lastLeft = -1;
                return lastLeft;
            } else if (Math.random() < 0.1) {
                lastLeft = 1;
                return lastLeft;
            }
        return lastLeft;
    }
}

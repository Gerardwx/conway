/**
 * Singleton timekeeper for simulation. Keeps lifetime
 * running total and binary values for current / next
 * state
 */
public class Clock {
    private static Clock INSTANCE = new Clock();
    long counter;
    private int cycle;
    private int nextCycle;

    /**
     * prevent creation except by class
     */
    private Clock() {
        counter = 0;
        cycle = 0;
        nextCycle = 1;
    }

    public static Clock getInstance() {
        return INSTANCE;
    }

    /**
     * increment the time
     */
    public void step() {
        counter++;
        cycle = (int) counter % 2;
        nextCycle = (int)(counter + 1) %2;
    }

    public long getCount() {
        return counter;
    }

    /**
     * current cycle
     * @return: 0 or 1
     */
    public int getCycle() {
        return cycle;
    }

    /**
     * next cycle
     * @return: 0 or 1
     */
    public int getNextCycle() {
        return nextCycle;
    }
}

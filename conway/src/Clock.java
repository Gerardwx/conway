public class Clock {
    private static Clock INSTANCE = new Clock();
    long counter;
    private int cycle;
    private int nextCycle;

    private Clock() {
        counter = 0;
        cycle = 0;
        nextCycle = 1;
    }

    public static Clock getInstance() {
        return INSTANCE;
    }

    public void step() {
        counter++;
        cycle = (int) counter % 2;
        nextCycle = (int)(counter + 1) %2;
    }

    public long getCount() {
        return counter;
    }

    public int getCycle() {
        return cycle;
    }

    public int getNextCycle() {
        return nextCycle;
    }
}

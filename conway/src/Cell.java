import java.util.Collection;

/**
 * Class that implements alive / dead state for next generation
 */
public abstract class Cell {
    /**
     * current and next generation states, alternating
     */
    private final boolean states[];
    private Collection<Cell> neighbors;

    public Cell( ) {
        this.states = new boolean[2];
    }

    public void setNeighbors(Collection<Cell> neighbors) {
        this.neighbors = neighbors;
    }

    public boolean isAlive( ) {
        return states[Clock.getInstance().getCycle()];
    }

    /**
     * calculate alive / dead for next cycle in clock
     */
    public void calculateNext( ) {
        int aliveNeighbors = (int) neighbors.stream().filter(c -> c.isAlive()).count();
        states[Clock.getInstance().getNextCycle( )] = compute(aliveNeighbors);
    }

    public void setAlive() {
        assert Clock.getInstance().getCycle() == 0;
        states[0] = true;
    }

    public void setDead() {
        assert Clock.getInstance().getCycle() == 0;
        states[0] = false;
    }

    /**
     * implement rules for alive / dead next time cycle
     * @param numberAlive
     * @return True if cell will be alive
     */
    protected abstract boolean compute(int numberAlive);
}

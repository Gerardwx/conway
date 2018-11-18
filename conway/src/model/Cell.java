package model;

import java.util.Collection;

/**
 * Class that implements alive / dead state for next generation
 */
public abstract class Cell {
    /**
     * current and next generation generations, alternating
     */
    private final boolean generations[];
    private Collection<Cell> neighbors;

    public Cell() {
        this.generations = new boolean[2];
    }

    public void setNeighbors(Collection<Cell> neighbors) {
        this.neighbors = neighbors;
    }

    public boolean isAlive() {
        return generations[Clock.getInstance().getCycle()];
    }

    /**
     * calculate alive / dead for next cycle in clock
     */
    public void calculateNext() {
        int aliveNeighbors = (int) neighbors.stream().filter(c -> c.isAlive()).count();
        generations[Clock.getInstance().getNextCycle()] = compute(aliveNeighbors);
    }

    public void setAlive() {
        assert Clock.getInstance().isBeginningOfSimulation();
        generations[Clock.getInstance().getCycle()] = true;
    }

    public void setDead() {
        assert Clock.getInstance().isBeginningOfSimulation();
        generations[Clock.getInstance().getCycle()] = false;
    }

    /**
     * implement rules for alive / dead next time cycle
     *
     * @param numberAlive
     * @return True if cell will be alive
     */
    protected abstract boolean compute(int numberAlive);
}

import java.util.Collection;

public abstract class Cell {
    private final boolean states[];
    private Collection<Cell> neighbors;

    public Cell( ) {
        this.states = new boolean[2];
    }

    public void setNeighbors(Collection<Cell> neighbors) {
        this.neighbors = neighbors;
    }

    public boolean isFilled( ) {
        return states[Clock.getInstance().getCycle()];
    }

    public void calculateNext( ) {
        Clock clock = Clock.getInstance();
        int clk = clock.getCycle();
        int nc = clock.getNextCycle();

        int aliveNeighbors = (int) neighbors.stream().filter(c -> c.isFilled()).count();
        states[Clock.getInstance().getNextCycle( )] = compute(aliveNeighbors);
    }


    protected abstract boolean compute(int numberAlive);

    public void setAlive() {
        assert Clock.getInstance().getCycle() == 0;
        states[0] = true;
    }
}

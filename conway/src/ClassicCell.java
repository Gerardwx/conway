public class ClassicCell extends Cell {
    /**
     * Classic rules (from Wikipedia)
     * Any live cell with fewer than two live neighbors dies, as if by underpopulation.
     * Any live cell with two or three live neighbors lives on to the next generation.
     * Any live cell with more than three live neighbors dies, as if by overpopulation.
     * Any dead cell with exactly three live neighbors becomes a live cell, as if by reproduction.
     * @param numberAlive
     */

    @Override
    protected boolean compute(int numberAlive) {
        assert numberAlive >= 0;
        switch (numberAlive) {
            case 0:
            case 1:
                return false;
            case 2:
                boolean selfAlive = isFilled();
                return selfAlive;
            case 3:
                return true;
            default:
                return false;
        }
    }
}

public class ConsoleDisplay implements  Display{
    int lastY;

    public ConsoleDisplay() {
        lastY = 0;
    }

    @Override
    public void complete() {
        lastY = 0;
        System.out.println();
        System.out.println();
    }

    @Override
    public void show(Cell cell, int x, int y) {
        char mark = cell.isFilled() ? 'X' : '.';
        if (y > lastY) {
            System.out.println();
            lastY = y;
        }
        System.out.print(mark);
    }
}

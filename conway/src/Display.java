public interface Display {

    public void show(Cell cell, int x, int y);
    public default void complete( ) {}
}

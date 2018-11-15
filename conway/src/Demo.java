/**
 * console demo (10 x 10 world)
 */
public class Demo {
    /**
     * number of cycles to show
     */
    public static final int LIMIT  = 100;

    public static void main(String[] args) {
        World w = new ClassicWorld(10,10,(x,y) -> new ClassicCell());
        ConsoleDisplay display = new ConsoleDisplay(w);
        w.setAlive(5,6);
        w.setAlive(5,7);
        w.setAlive(5,8);
        display.show();
        for (int i = 0 ; i < LIMIT; i++) {
            w.step();
            display.show();
        }
    };
    
}

import console.ConsoleDisplay;
import model.ClassicCell;
import model.ClassicWorld;
import model.World;

/**
 * console demo (10 x 10 world)
 */
public class Demo {
    /**
     * number of cycles to show
     */
    public static final int LIMIT  = 100;

    public static void main(String[] args) {
        World w = new ClassicWorld(10,10,(c) -> new ClassicCell());
        ConsoleDisplay display = new ConsoleDisplay(w);
        w.get(5,6).setAlive();
        w.get(5,7).setAlive();
        w.get(5,8).setAlive();
        display.show();
        for (int i = 0 ; i < LIMIT; i++) {
            w.step();
            display.show();
        }
    };
    
}

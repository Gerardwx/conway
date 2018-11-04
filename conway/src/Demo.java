public class Demo {
    public static final int LIMIT  = 100;

    public static void main(String[] args) {
        Display display = new ConsoleDisplay();
        World w = new World(10,10,display);
        w.setAlive(5,6);
        w.setAlive(5,7);
        w.setAlive(5,8);
        w.show();
        for (int i = 0 ; i < LIMIT; i++) {
            w.step();
            w.show();
        }

    };
}

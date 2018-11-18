package fxgraphic;

import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.geometry.VPos;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.TextAlignment;
import model.Boundary;
import model.Cell;
import model.Location;
import model.World;

import java.util.function.Supplier;


/**
 * Canvas which draws the world and allows cells to be
 * toggled
 */
public class GraphicDisplay extends Canvas {

    private final World world;
    private final String startupMessage;
    private final Supplier<CShape> cShapeSupplier;
    private final EventHandler<MouseEvent> mouseHandler;
    private Runnable displayMethod;

    public GraphicDisplay(World world, String startupMessage, Supplier<CShape> cShapeSupplier) {
        this.world = world;
        this.startupMessage = startupMessage;
        this.cShapeSupplier = cShapeSupplier;
        mouseHandler = this::toggleCell;
        displayMethod = this::startupMessage;
        addEventHandler(MouseEvent.MOUSE_CLICKED, mouseHandler);
    }


    /**
     * invoke currently set display method
     */
    public void display() {
        displayMethod.run();
    }

    public void disableClick() {
        removeEventHandler(MouseEvent.MOUSE_CLICKED, mouseHandler);
    }


    /**
     * mouse click handler, toggle state of cell clicked on
     *
     * @param mouseEvent
     */
    public void toggleCell(MouseEvent mouseEvent) {
        displayMethod = this::drawGrid;
        double w = world.getWidth();
        double h = world.getHeight();
        double dx = getWidth() / w;
        double dy = getHeight() / h;
        int x = (int) (mouseEvent.getX() / dx);
        int y = (int) (mouseEvent.getY() / dy);
        Location start = new Location(x, y);
        for (Location delta : cShapeSupplier.get().getLocations()) {
            Location where = Location.add(world, start,delta);
            Cell cell = world.get(where);
            if (cell.isAlive()) {
                cell.setDead();
            } else {
                cell.setAlive();
            }
        }
        display();
    }

    /**
     * show startup message
     */
    public void startupMessage() {
        GraphicsContext gc = getGraphicsContext2D();
        gc.setFill(Color.WHITE);
        gc.fillRect(0, 0, getWidth(), getHeight());
        gc.setFill(Color.BLACK);
        gc.setTextAlign(TextAlignment.CENTER);
        gc.setTextBaseline(VPos.CENTER);
        gc.fillText(
                startupMessage,
                Math.round(getWidth() / 2),
                Math.round(getHeight() / 2)
        );
    }

    /**
     * display the world
     */
    public void drawGrid() {
        GraphicsContext gc = getGraphicsContext2D();
        gc.setFill(Color.WHITE);
        gc.fillRect(0, 0, getWidth(), getHeight());
        gc.setFill(Color.RED);
        double w = world.getWidth();
        double h = world.getHeight();
        double dx = getWidth() / w;
        double dy = getHeight() / h;

        for (int x = 0; x < w; x++) {
            for (int y = 0; y < h; y++) {
                Cell cell = world.get(x, y);
                if (cell.isAlive()) {
                    double sx = x * dx;
                    double sy = y * dy;
                    gc.fillRoundRect(sx, sy, dx, dy, dx / 2, dy / 2);
                }
            }
        }
    }
}

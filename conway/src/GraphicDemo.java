import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Duration;


/**
 * JavaFX demo with 100 x 100 world and controls to
 * set cells and adjust display rate
 */
public class GraphicDemo extends Application  {
    private final World world;
    private final Button startButton;
    private final Button faster;
    private final Button slower;
    private final Timeline timeLine;
    private GraphicDisplay graphicDisplay;
    /**
     * how much Faster / slower buttons adjust timeLine frame rate
     */
    private static final double RATE_ADJUSTMENT_FACTOR = 1.2;


    public static void main(String[] args) {
        launch(args);
    }

    /**
     * create controls
     */
    public GraphicDemo( ) {
        super( );
        world = new World(100,100,(x,y)-> new ClassicCell());

        startButton = new Button("Start");
        startButton.setOnAction(this::beginLife);
        faster = new Button("Faster");
        faster.setOnAction( (ae) -> adjustRate(RATE_ADJUSTMENT_FACTOR));
        faster.setDisable(true);
        slower = new Button("Slower");
        slower.setOnAction( (ae) -> adjustRate(1/RATE_ADJUSTMENT_FACTOR));
        slower.setDisable(true);

        KeyFrame kf = new KeyFrame(Duration.seconds(1), this::update);
        timeLine = new Timeline(kf);
        timeLine.setCycleCount(Timeline.INDEFINITE);
    }


    /**
     * set up Application / scence per Java FX Application lifeccyle
     * @param primaryStage
     */
    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Conway's game of life");
        primaryStage.setWidth(800);
        primaryStage.setHeight(600);
        BorderPane borderPane = new BorderPane();

        graphicDisplay = new GraphicDisplay(world,
                "Click on screen to toggle cells, then press Start");
        Pane centerPane = new Pane();
        centerPane.getChildren().add(graphicDisplay);
        borderPane.setCenter(centerPane);

        FlowPane buttonPane = new FlowPane();
        Button exit = new Button("Exit");
        exit.setOnAction((ae) -> Platform.exit());

        buttonPane.getChildren().addAll(startButton,faster,slower,exit);

        borderPane.setBottom(buttonPane);
        Scene sc = new Scene(borderPane);
        graphicDisplay.widthProperty().bind(sc.widthProperty());
        graphicDisplay.heightProperty().bind(sc.heightProperty());
        primaryStage.setScene(sc);
        primaryStage.show();
        sc.heightProperty().addListener(this::dimensionChanged);
        sc.widthProperty().addListener(this::dimensionChanged);
    }

    /**
     * disable setup controls, start the timeline
     * @param actionEvent
     */
    private void beginLife(ActionEvent actionEvent) {
        startButton.setDisable(true);
        graphicDisplay.disableClick();
        faster.setDisable(false);
        slower.setDisable(false);
        timeLine.play();
    }
    
    private void adjustRate(double adjustment) {
        timeLine.setRate(timeLine.getCurrentRate() * adjustment);
    }

    /**
     * timeline event, step world and draw it
     * @param ae
     */
    private void update(ActionEvent ae) {
        world.step();
        graphicDisplay.display();
    }

    /**
     * redraw screen when size changes
      * @param ov
     * @param oldValue
     * @param newValue
     */
    private void dimensionChanged(ObservableValue<? extends  Number> ov, Number oldValue, Number newValue) {
        graphicDisplay.display();
    }
}

import fxgraphic.CShape;
import fxgraphic.GraphicDisplay;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Duration;
import model.ClassicCell;
import model.ClassicWorld;
import model.World;


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
    private final ObservableList<CShape> shapeList;
    private final ChoiceBox<CShape> choiceBox;
    private CShape currentShape;
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
        world = new ClassicWorld(100,100,(c)-> new ClassicCell());

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
        shapeList = FXCollections.observableArrayList(CShape.DOT, CShape.RIGHT_GLIDER, CShape.LEFT_GLIDER);
        choiceBox = new ChoiceBox<>(shapeList);
        currentShape = CShape.DOT;
        assert shapeList.contains(currentShape);
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
                "Click on screen to toggle cells, then press Start",
                () -> currentShape);
        Pane centerPane = new Pane();
        centerPane.getChildren().add(graphicDisplay);
        borderPane.setCenter(centerPane);

        FlowPane buttonPane = new FlowPane();
        Button exit = new Button("Exit");
        exit.setOnAction((ae) -> Platform.exit());

        ChoiceBox<CShape> choiceBox = new ChoiceBox<>(shapeList);
        choiceBox.setValue(shapeList.get(0));
        choiceBox.getSelectionModel().selectedItemProperty().addListener(this::shapeChanged);

        buttonPane.getChildren().addAll(choiceBox,startButton,faster,slower,exit);

        borderPane.setBottom(buttonPane);
        Scene sc = new Scene(borderPane);
        graphicDisplay.widthProperty().bind(centerPane.widthProperty());
        graphicDisplay.heightProperty().bind(centerPane.heightProperty());
        primaryStage.setScene(sc);
        primaryStage.show();
        sc.heightProperty().addListener(this::dimensionChanged);
        sc.widthProperty().addListener(this::dimensionChanged);
    }

    private void shapeChanged(ObservableValue<? extends CShape> value, CShape oldValue, CShape newValue) {
        currentShape = newValue;
    }

    /**
     * disable setup controls, start the timeline
     * @param actionEvent
     */
    private void beginLife(ActionEvent actionEvent) {
        choiceBox.setDisable(true); //TODO: Figure out why this doesn't work
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

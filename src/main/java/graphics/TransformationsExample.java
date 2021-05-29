package graphics;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.transform.Transform;
import javafx.stage.Stage;

public class TransformationsExample extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    public void start(Stage primaryStage) {

        Rectangle rectangle = new Rectangle();
        rectangle.setX(200);
        rectangle.setY(200);
        rectangle.setWidth(100);
        rectangle.setHeight(100);
        rectangle.setStroke(Color.TRANSPARENT);
        rectangle.setFill(Color.valueOf("#00ffff"));



        Pane pane = new Pane();
        pane.getChildren().add(rectangle);

        Scene scene = new Scene(pane, 1024, 800, true);
        primaryStage.setScene(scene);
        primaryStage.setTitle("2D Example");

        primaryStage.show();

        scene.addEventHandler(KeyEvent.KEY_TYPED,(event -> {

            //rectangle.setX(0);
            switch(event.getCharacter()) {
                case "w":
                    rectangle.setX(rectangle.getX() + 10);
                    break;
                case "A":


                    break;
                case "S":


                    break;
                case "D":


                    break;


                default:
                    break;
            }

        }));


    }
}

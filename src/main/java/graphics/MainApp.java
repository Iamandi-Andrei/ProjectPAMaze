package graphics;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.embed.swing.SwingFXUtils;
import javafx.geometry.Rectangle2D;
import javafx.scene.*;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.transform.Translate;
import javafx.stage.*;
import mazeObj.Cell;
import mazeObj.Maze;

import javax.imageio.ImageIO;
import java.awt.image.RenderedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Paths;

public class MainApp extends Application {

    Rectangle player;
    Stage primaryStage;
    double moveX;
    double moveY;
    Maze maze;
    Group root;


    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("JavaFX App");
        this.buildStage(primaryStage);
        Group root = new Group();
        this.primaryStage=primaryStage;
        Scene scene = this.buildScreenMain(primaryStage, root);
        primaryStage.setScene(scene);
        primaryStage.show();
        Button next = this.addButton(scene);
        root.getChildren().add(next);
        Group root2 = new Group();
        this.root = root2;
        Scene second = this.buildScreenMain(primaryStage, root2);
        this.addButtonHandler(next, second, primaryStage);

        int value = 40;
        this.generateMazeScene(value,40, root2, second);
        this.addExport(second,root2);
        this.addPlayerHandles(second, root2);
        System.out.println("here");


    }
    public void addExport(Scene scene,Group root){

        ComboBox<String> formats=new ComboBox<>();
        formats.getItems().addAll(".png",".csv");
        root.getChildren().add(formats);


        Button button=new Button();
        root.getChildren().add(button);
        button.setText("Export format");
        button.setLayoutX(0);
        button.setLayoutY(scene.getHeight()-100);
        button.setPrefWidth(150);
        button.setPrefHeight(40);
        formats.setLayoutX(0+button.getPrefWidth());
        formats.setLayoutY(scene.getHeight()-100);
        this.addExportHandler(button,formats);

    }

    public void addExportHandler(Button button,ComboBox<String> formats){

        button.addEventHandler(MouseEvent.MOUSE_CLICKED,(event)->{

            DirectoryChooser locationChooser=new DirectoryChooser();

            File selectedFile = locationChooser.showDialog(this.primaryStage);
            switch(formats.getValue()) {
                case ".png":
                WritableImage image = root.snapshot(new SnapshotParameters(), null);
                RenderedImage renderedImage = SwingFXUtils.fromFXImage(image, null);
                //Write the snapshot to the chosen file
                try {
                    ImageIO.write(renderedImage, "png", new File(selectedFile.getAbsolutePath() + "\\out.png"));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
                case ".csv":
                    //this.maze.generateCSV(selectedFile.getAbsolutePath());
                    System.out.println("CSV:done");
                    //this.maze.print();
                    break;
            }

        });


    }

    public void addPlayerHandles(Scene second, Group root2) {
        second.addEventHandler(KeyEvent.KEY_TYPED, (event) -> {
            switch (event.getCharacter()) {
                case "w":
                    player.setY(player.getY() - moveY);
                    if (this.checkOverlapping())
                        player.setY(player.getY() + moveY);
                    break;
                case "a":
                    player.setX(player.getX() - moveX);
                    if (this.checkOverlapping())
                        player.setX(player.getX() + moveX);
                    break;
                case "s":
                    player.setY(player.getY() + moveY);
                    if (this.checkOverlapping())
                        player.setY(player.getY() - moveY);
                    break;
                case "d":
                    player.setX(player.getX() + moveX);
                    if (this.checkOverlapping())
                        player.setX(player.getX() - moveX);
                    break;


                default:
                    break;


            }
            player.toFront();
            //System.out.println(event.getCharacter() + "  hhh");


        });


    }

    public Scene buildScreenMain(Stage primaryStage, Group root) {
        Rectangle2D screenBounds = Screen.getPrimary().getBounds();


        Scene scene = new Scene(root, screenBounds.getWidth() / 1.4, screenBounds.getHeight() - 100);
        //root.getChildren().add(button);


        return scene;


    }

    public void addButtonHandler(Button button, Scene nextOne, Stage stage) {
        button.addEventHandler(MouseEvent.MOUSE_CLICKED, (event) -> {

            stage.setScene(nextOne);


        });

    }

    public boolean checkOverlapping() {

        for (Node n : root.getChildren()) {
            if (!player.equals(n))
                if (player.getBoundsInParent().intersects(n.getBoundsInParent())) {
                    Rectangle r = (Rectangle) n;
                    if (r.getFill() == Color.BLACK)
                        return true;
                }

        }
        return false;

    }

    public void generateMazeScene(int value1,int value2, Group root, Scene scene) {

       // Maze m = new Maze(value1, value2);
        Maze m=null;
        //m.generateRecursive();
        double maxX = scene.getWidth();
        double maxY = scene.getHeight() - 100;
        double ratioX = maxX / value1;
        double ratioY = maxY / value2;
        moveX = ratioX / 3;
        moveY = ratioY / 3;
        maze = m;
        Cell[][] c = m.getMaze();
        for (int i = 0; i < c.length; i++)
            for (int j = 0; j < c[0].length; j++)
                this.drawCell(ratioX / 2 + ratioX * i, ratioY / 2 + ratioY * j, ratioX, ratioY, c[i][j], root);

        System.out.println("done");


    }

    public Button addButton(Scene scene) {
        Button button = new Button("Start");
        button.setPrefWidth(100);
        button.setPrefHeight(40);
        button.setLayoutX(scene.getWidth() / 2 - 100);
        button.setLayoutY(scene.getHeight() / 2 - 100);
        return button;
    }

    public void buildStage(Stage primaryStage) {
        Rectangle2D screenBounds = Screen.getPrimary().getBounds();
        primaryStage.setWidth(screenBounds.getWidth() / 1.4);
        primaryStage.setHeight(screenBounds.getHeight() - 100);
    }

    public static void main(String[] args) {
        Application.launch(args);
    }

    public void drawCell(double x, double y, double ratioX, double ratioY, Cell c, Group root) {
        int[][] walls = c.getWalls();
        double percentX = ratioX / 3;
        double percentY = ratioY / 3;
        for (int i = 0; i < 3; i++)
            for (int j = 0; j < 3; j++) {
                Rectangle r = new Rectangle(x + (i - 1) * percentX - percentX / 2, y + (j - 1) * percentY - percentY / 2, percentX, percentY);
                switch (walls[i][j]) {
                    case 1:
                        r.setFill(Color.BLACK);
                        break;
                    case 0:
                        r.setFill(Color.DARKKHAKI);
                        break;
                    case 2:
                        r.setFill(Color.RED);
                        r.setHeight(r.getHeight() - 2);
                        r.setWidth(r.getWidth() - 2);
                        r.setLayoutX(r.getLayoutX() + 1);
                        r.setLayoutY(r.getLayoutY() + 1);
                        this.player = r;
                        break;
                    case 3:
                        r.setFill(Color.BLUE);
                        break;
                }
                root.getChildren().add(r);
            }


    }


}

package farm_simulation;

import farmyard.*;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 * Our take on the "classical" game Farm Ville
 *
 */



public class Main extends Application {

    /**
     * The width of a character.
     */
    public final static int charWidth = 6;
    /**
     * The height of a character.
     */
    public final static int charHeight = 10;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("FarmVille");

        Group root = new Group();
        Scene theScene = new Scene(root);
        primaryStage.setScene(theScene);
        Canvas canvas = new Canvas(1024, 720);
        root.getChildren().add(canvas);

        GraphicsContext gc = canvas.getGraphicsContext2D();

        Human.myFarmAnimals[0][0] = new Chicken();
        ((Chicken) Human.myFarmAnimals[0][0]).setLocation(23, 18);
        Human.myFarmAnimals[6][12]=new Chicken();
        ((Chicken) Human.myFarmAnimals[6][12]).setLocation(6,12);
        Human.myFarmAnimals[17][4]=new Chicken();
        ((Chicken) Human.myFarmAnimals[17][4]).setLocation(17,4);
        Human.myFarmAnimals[15][28]=new Chicken();
        ((Chicken) Human.myFarmAnimals[15][28]).setLocation(15,28);
        Human.myFarmAnimals[23][18]=new Chicken();
        ((Chicken) Human.myFarmAnimals[23][18]).setLocation(15,36);
        Human.myFarmAnimals[16][35]=new Chicken();
        ((Chicken) Human.myFarmAnimals[16][35]).setLocation(16,35);
        Human.myFarmAnimals[16][35]=new Chicken();
        ((Chicken) Human.myFarmAnimals[16][35]).setLocation(16,35);
        Human.myFarmAnimals[16][22]=new Chicken();
        ((Chicken) Human.myFarmAnimals[16][22]).setLocation(16,18);
        Human.myFarmAnimals[23][18]=new Chicken();
        ((Chicken) Human.myFarmAnimals[23][18]).setLocation(23,18);
        Human.myFarmAnimals[6][12]=new Chicken();
        ((Chicken) Human.myFarmAnimals[6][12]).setLocation(6,12);
        Human.myFarmAnimals[10][20]=new Pig();
        ((Pig) Human.myFarmAnimals[10][20]).setLocation(10,20);
        Human.myFarmAnimals[20][10] =new Pig();
        ((Pig) Human.myFarmAnimals[20][10]).setLocation(20,10);
        Human.myFarmAnimals[30][30]=new Human();
        ((Human) Human.myFarmAnimals[30][30]).setLocation(30,30);


        drawShapes(gc);

        Timeline gameLoop = new Timeline();
        gameLoop.setCycleCount(Timeline.INDEFINITE);
        final long timeStart = System.currentTimeMillis();

        KeyFrame kf = new KeyFrame(
                Duration.seconds(0.5),
                new EventHandler<ActionEvent>() {
                    public void handle(ActionEvent ae) {
                        double t = (System.currentTimeMillis() - timeStart) / 1000.0;

                        for(int a=0;a!=(int)(480/10);a++){
                            for(int b=0;b!=(int)(640/6);b++){
                                if (Human.myFarmAnimals[a][b] != null)
                                    if (Human.myFarmAnimals[a][b] instanceof Chicken) {
                                        ((Chicken) Human.myFarmAnimals[a][b]).move();
                                    }else if (Human.myFarmAnimals[a][b] instanceof Pig) {
                                        ((Pig) Human.myFarmAnimals[a][b]).move();
                                    }else if (Human.myFarmAnimals[a][b] instanceof  Human){
                                        ((Human) Human.myFarmAnimals[a][b]).move();
                                    }else if (Human.myFarmAnimals[a][b] instanceof ANIMAL_MANURE){

                                    }
                                if (Human.myFarmAnimals[a][b] instanceof AnimalFood) {
                                    // Figure out whether to float left or right, if at all.
                                    AnimalFood lolfood= (AnimalFood) Human.myFarmAnimals[a][b];

                                    lolfood.d = Wind.windBlowingUp();
                                    if (lolfood.d == -1) lolfood.blownUp();
                                    if (lolfood.d == 1) lolfood.blownDown();
                                    lolfood.d = Wind.windBlowingLeft();
                                    if (lolfood.d == -1) lolfood.blownLeft();
                                    if (lolfood.d == 1) lolfood.blownRight();
                                }
                            }
                        }

                        // Clear the canvas
                        gc.clearRect(0, 0, 1024, 720);
                        drawShapes(gc);
                    }
                });

        gameLoop.getKeyFrames().add(kf);
        gameLoop.play();
        primaryStage.show();
    }

    private void drawShapes(GraphicsContext gc) {
        // Tell all the farmyard items to draw themselves.
        for (int a = 0; a != (int) (480 / 10); a++) {
            for (int b = 0; b != (int) (640 / 6); b++) {
                if (Human.myFarmAnimals[a][b] != null) {

                    if (Human.myFarmAnimals[a][b] instanceof Chicken) {

                        ((Chicken) Human.myFarmAnimals[a][b]).draw(gc);
                    }else if(Human.myFarmAnimals[a][b] instanceof Pig){
                        ((Pig) Human.myFarmAnimals[a][b]).draw(gc);
                    } else if (Human.myFarmAnimals[a][b] instanceof  Human) {
                        ((Human) Human.myFarmAnimals[a][b]).draw(gc);
                    } else if (Human.myFarmAnimals[a][b] instanceof ANIMAL_MANURE){
                        ((ANIMAL_MANURE) Human.myFarmAnimals[a][b]).draw(gc);
                    }
                    if (Human.myFarmAnimals[a][b] instanceof AnimalFood) {
                        ((AnimalFood) Human.myFarmAnimals[a][b]).draw(gc);
                    }
                    if (Human.myFarmAnimals[a][b] instanceof Egg) {
                        ((Egg) Human.myFarmAnimals[a][b]).draw(gc);
                    }
                }
            }
        }

    }
}

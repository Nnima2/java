import javafx.application.Application;
import javafx.scene.input.KeyCombination;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.image.Image;
import javafx.scene.*;
import org.w3c.dom.Text;

import java.awt.*;

public class Main extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        Group root  =new Group();
        Scene scene = new Scene(root,600,600, Color.SKYBLUE);

        Image icon = new Image("images\\icon.png");

//        Text text = new Text("salam");
//        text.setText("Ali parvin madar.... boro dige!!!");
        stage.getIcons().add(icon);
        stage.setTitle("Hello Farmer");
        stage.setWidth(420);
        stage.setHeight(420);
        stage.setResizable(false);
        stage.setX(50);
        stage.setY(0);


            stage.setFullScreen(true);
            stage.setFullScreenExitHint("you cant escape unless you press q");
            stage.setFullScreenExitKeyCombination(KeyCombination.valueOf("q"));
            stage.setFullScreen(true);

            stage.setScene(scene);
            stage.show();

    }
}
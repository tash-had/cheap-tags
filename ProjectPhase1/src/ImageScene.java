import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;


public class ImageScene {

    public static Scene getImageScene(Button back){

        StackPane imagesLayout = new StackPane();
        imagesLayout.getChildren().add(back);

        // constructs images scene;
        return new Scene(imagesLayout, 300, 250);
    }
}

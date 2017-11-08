import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;


public class ImageScene {

    public static Scene constructImageScene(Button back){
//        Button back = new Button();
//        back.setText("Back");
//        back.setOnAction(e -> window.setScene(home));

        StackPane imagesLayout = new StackPane();
        imagesLayout.getChildren().add(back);

        // constructs images scene;
        Scene images = new Scene(imagesLayout, 300, 250);
        return images;
    }
}

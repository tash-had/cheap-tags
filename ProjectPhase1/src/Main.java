import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class Main extends Application{
    @Override
    public void start(Stage primaryStage) {

        // Create two buttons to open image, or view my tags.
        Button openBtn = new Button();
        Button tagsBtn = new Button();
        openBtn.setText("Open");
        tagsBtn.setText("My Tags");

        openBtn.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
            }
        });

        StackPane root = new StackPane();
        root.getChildren().add(openBtn);
        root.getChildren().add(tagsBtn);
        openBtn.setTranslateY(0);
        tagsBtn.setTranslateY(80);

        Scene scene = new Scene(root, 300, 250);

        primaryStage.setTitle("Main");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    public static void main(String[] args) {
        launch(args);
    }
}

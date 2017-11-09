import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.stage.DirectoryChooser;

import java.io.File;

public class Main extends Application{

    public Scene home, tagScreen, images;
    public Stage window;

    @Override
    public void start(Stage primaryStage) {
        window = primaryStage;

        // Back button
        Button back = new Button();
        back.setText("Back");
        back.setOnAction(e -> window.setScene(home));

        // Button to choose directory
        Button chooseDirectoryButton = new Button();
        chooseDirectoryButton.setText("Choose a directory");
        chooseDirectoryButton.setOnAction(e -> {
            DirectoryChooser directoryChooser=  new DirectoryChooser();
            directoryChooser.setTitle("Select a directory");
            File selectedFile = directoryChooser.showDialog(primaryStage);
            if (selectedFile != null) {
//                ImageFileManager.fetchFiles(selectedFile);
                window.setScene(ImageScene.constructImageScene(back));
            }
            else {
                window.setScene(home);
            }
        });

        // Button to view my tags
        Button tagButton = new Button();
        tagButton.setText("My Tags");
<<<<<<< HEAD
        Button back2 = new Button("Back");
=======
//        tagButton.setOnAction(e -> window.setScene(DisplayTagsView.getScene(back));

        Button back2 = new Button("go back!");
>>>>>>> a506fd4ebe770c821136eec98b9a0ee9e3307376
        tagButton.setOnAction(e -> window.setScene(DisplayTagsView.getScene(back2)));
        back2.setOnAction(e -> window.setScene(home));


        // constructs home layout
        StackPane homeLayout = new StackPane();
        homeLayout.getChildren().add(chooseDirectoryButton);
        homeLayout.getChildren().add(tagButton);
        chooseDirectoryButton.setTranslateY(0);
        tagButton.setTranslateY(50);

        // constructs home scene
        home = new Scene(homeLayout, 300, 250);

        window.setTitle("Main");
        window.setScene(home);
        window.show();

//        // constructs images layout
//        StackPane imagesLayout = new StackPane();
//        imagesLayout.getChildren().add(back);
//
//        // constructs images scene;
//        images = new Scene(imagesLayout, 300, 250);
    }
    public static void main(String[] args){
        launch(args);
    }
}

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

    public Scene home;
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
            // if a file is selected, go to single image scene
            if (selectedFile != null) {
                //ImageFileManager.fetchFiles(selectedFile);
                window.setScene(ImageScene.getImageScene(back));
            }
            // else, if nothing is selected, go back to home scene
            else {
                window.setScene(home);
            }
        });

        // Button to view my tags
        Button tagButton = new Button();
        tagButton.setText("My Tags");
        tagButton.setOnAction(e -> window.setScene(DisplayTagsView.getScene(back)));

        // constructs home layout
        StackPane homeLayout = new StackPane();
        // adding directory and tag buttons
        homeLayout.getChildren().add(chooseDirectoryButton);
        homeLayout.getChildren().add(tagButton);
        chooseDirectoryButton.setTranslateY(0);
        tagButton.setTranslateY(50);

        // constructs home scene
        home = new Scene(homeLayout, 300, 250);
        window.setTitle("Home");
        window.setScene(home);
        window.show();
    }
    public static void main(String[] args){
        launch(args);
    }
}

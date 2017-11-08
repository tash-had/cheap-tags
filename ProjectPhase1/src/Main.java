import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.stage.DirectoryChooser;

import java.io.File;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.HashMap;

public class Main extends Application{

    private Scene home, tagScreen, images;
    private Stage window;

    @Override
    public void start(Stage primaryStage) {
        window = primaryStage;

        // Button to choose directory
        Button chooseDirectoryButton = new Button();
        chooseDirectoryButton.setText("Choose a directory");
        chooseDirectoryButton.setOnAction(e -> {
            DirectoryChooser directoryChooser=  new DirectoryChooser();
            directoryChooser.setTitle("Select a directory");
            File selectedFile = directoryChooser.showDialog(primaryStage);
            if (selectedFile != null) {
//                ImageFileManager.fetchFiles(selectedFile);
                window.setScene(images);
            }
            else {
                window.setScene(home);
            }
        });

        // Button to view my tags
        Button tagButton = new Button();
        tagButton.setText("My Tags");
        tagButton.setOnAction(e -> window.setScene(tagScreen));

        // Back button
        Button back = new Button();
        back.setText("Back");
        back.setOnAction(e -> window.setScene(home));

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

        // constructs images layout
        StackPane imagesLayout = new StackPane();
        imagesLayout.getChildren().add(back);

        // constructs images scene;
        images = new Scene(imagesLayout, 300, 250);
    }
    public static void main(String[] args) throws FileNotFoundException {
        launch(args);

        File file = new File("todo.txt");
        ImageFile imf = new ImageFile("todo.txt");
        HashMap <String, ImageFile> hm = new HashMap<>();
        for (ImageFile myFile : hm.values()){
            if (myFile.currentName.equals("todo.txt")){
                // this image exists alread
            }
        }


        BufferedReader fileInput = new BufferedReader(new FileReader(file.getAbsolutePath()));
    }
}

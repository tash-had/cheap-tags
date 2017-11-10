import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application{

    @Override
    public void start(Stage primaryStage) throws IOException {
        PrimaryStageManager.setPrimaryStage(primaryStage);
        PrimaryStageManager.setDefaultStageWidth(1080);
        PrimaryStageManager.setDefaultStageHeight(720);
        PrimaryStageManager.setScreen("Cheap Tags", "home_screen_view.fxml");
        PrimaryStageManager.showPrimaryStage();

//        Parent root = FXMLLoader.load(getClass().getResource("home_screen_view.fxml"));
//
//        // Back button
//        Button back = new Button();
//        back.setText("Back");
//        back.setOnAction(e -> mainStage.setScreen(home));
//
//        // Button to choose directory
//        Button chooseDirectoryButton = new Button();
//        chooseDirectoryButton.setText("Choose a directory");
//        chooseDirectoryButton.setOnAction(e -> {
//            DirectoryChooser directoryChooser=  new DirectoryChooser();
//            directoryChooser.setTitle("Select a directory");
//            File selectedFile = directoryChooser.showDialog(primaryStage);
//            if (selectedFile != null) {
//                mainStage.setScreen(ImageScene.constructImageScene(back));
//            }
//            else {
//                mainStage.setScreen(home);
//            }
//        });
//
//        // Button to view my tags
//        Button tagButton = new Button();
//        tagButton.setText("My Tags");
////        tagButton.setOnAction(e -> window.setScreen(DisplayTagsView.getScene(back));
//
//        Button back2 = new Button("go back!");
//        tagButton.setOnAction(e -> mainStage.setScreen(DisplayTagsView.getScene(back2)));
//        back2.setOnAction(e -> mainStage.setScreen(home));
//
//
//        // constructs home layout
//        StackPane homeLayout = new StackPane();
//        homeLayout.getChildren().add(chooseDirectoryButton);
//        homeLayout.getChildren().add(tagButton);
//        chooseDirectoryButton.setTranslateY(0);
//        tagButton.setTranslateY(50);
//
//        // constructs home scene
//        home = new Scene(homeLayout, 300, 250);
//        mainStage.setTitle("Main");
////        mainStage.setScreen(home);
//
////        mainStage.setResizable(false);
//        mainStage.setScreen(new Scene(root, 1080,720, Color.WHITE));
//        mainStage.show();

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

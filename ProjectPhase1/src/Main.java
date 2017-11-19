import javafx.application.Application;
import javafx.stage.Stage;
import managers.ImageFileOperationsManager;
import managers.PrimaryStageManager;
import model.ImageFile;
import utils.FileOperations;


//import tests.FileOperationsTest;


import java.io.File;
import java.io.IOException;

public class Main extends Application{

    @Override
    public void start(Stage primaryStage) throws IOException {
        PrimaryStageManager.setPrimaryStage(primaryStage);
        PrimaryStageManager.setDefaultStageWidth(1080);
        PrimaryStageManager.setDefaultStageHeight(720);
        PrimaryStageManager.setScreen("Cheap Tags", "/activities/home_screen_view.fxml");
        PrimaryStageManager.showPrimaryStage();
    }
    public static void main(String[] args){
        launch(args);
        File file = new File("/Users/tash-had/Desktop/fikder.jpg/@Tag1 @Tag1 @Tag1 @Tag1 489 - ZRa7b8X.jpg");
        System.out.println(file.getAbsolutePath());
        FileOperations.renameFile(file, "yooooo_fam.jpg");
        System.out.println(file.getAbsolutePath());
    }


}

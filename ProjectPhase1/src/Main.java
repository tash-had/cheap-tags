import javafx.application.Application;
import javafx.stage.Stage;
import managers.PrimaryStageManager;
import managers.SessionDataManager;
import managers.StateManager;
import managers.UserDataManager;


//import tests.FileOperationsTest;


import java.io.IOException;

public class Main extends Application{
    public static UserDataManager userData = null;
    public static SessionDataManager sessionData = null;

    @Override
    public void start(Stage primaryStage) throws IOException {
        StateManager.startSession();
        PrimaryStageManager.setPrimaryStage(primaryStage);
        PrimaryStageManager.setDefaultStageWidth(1080);
        PrimaryStageManager.setDefaultStageHeight(720);
        PrimaryStageManager.setScreen("Cheap Tags", "/activities/home_screen_view.fxml");
        PrimaryStageManager.showPrimaryStage();
    }
    public static void main(String[] args){
        launch(args);
    }


}

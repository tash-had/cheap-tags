import javafx.application.Application;
import javafx.stage.Stage;
import managers.*;

import static managers.PrimaryStageManager.getPrimaryStageManager;

//import tests.FileOperationsTest;


import java.io.IOException;

public class Main extends Application{
    @Override
    public void start(Stage primaryStage) throws IOException {
        StateManager.startSession();
        PrimaryStageManager.setPrimaryStage(primaryStage);
        getPrimaryStageManager().setDefaultScreenWidth(1080);
        getPrimaryStageManager().setDefaultScreenHeight(720);
        getPrimaryStageManager().setScreen("Cheap Tags", "/activities/home_screen_view.fxml");
        getPrimaryStageManager().showStage();
        
    }
    public static void main(String[] args){
        launch(args);
    }


}

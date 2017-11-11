import javafx.application.Application;
import javafx.stage.Stage;
import managers.PrimaryStageManager;

import java.io.IOException;

public class Main extends Application{

    @Override
    public void start(Stage primaryStage) throws IOException {
        PrimaryStageManager.setPrimaryStage(primaryStage);
        PrimaryStageManager.setDefaultStageWidth(1080);
        PrimaryStageManager.setDefaultStageHeight(720);
        PrimaryStageManager.setScreen("Cheap Tags", "activities/home_screen_view.fxml");
        PrimaryStageManager.showPrimaryStage();

    }
    public static void main(String[] args){
        launch(args);
    }


}

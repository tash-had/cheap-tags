import javafx.application.Application;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import managers.*;

import static managers.PrimaryStageManager.getPrimaryStageManager;

import java.io.IOException;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws IOException {
        StateManager.startSession();
        PrimaryStageManager.setPrimaryStage(primaryStage);
        getPrimaryStageManager().setDefaultScreenWidth(1280);
        getPrimaryStageManager().setDefaultScreenHeight(800);
        getPrimaryStageManager().setScreen("Cheap Tags", "/activities/home_screen_view.fxml");
        primaryStage.initStyle(StageStyle.DECORATED);
        getPrimaryStageManager().showStage();
    }

    public static void main(String[] args) {
        launch(args);
    }
}

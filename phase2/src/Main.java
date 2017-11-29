import javafx.application.Application;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import managers.*;

import static managers.PrimaryStageManager.getPrimaryStageManager;

import java.io.IOException;

public class Main extends Application{
    @Override
    public void start(Stage primaryStage) throws IOException {
        StateManager.startSession();
        PrimaryStageManager.setPrimaryStage(primaryStage);
        getPrimaryStageManager().setDefaultScreenWidth(1080);
        getPrimaryStageManager().setDefaultScreenHeight(720);
        getPrimaryStageManager().setScreen("Cheap Tags", "/activities/home_screen_view.fxml");
        primaryStage.initStyle(StageStyle.DECORATED);
        getPrimaryStageManager().showStage();

//        StageManager sm = new StageManager(new Stage());
//        sm.setDefaultScreenWidth(1000);
//        sm.setDefaultScreenHeight(300);
//        sm.setScreen("Window Title", "/activities/tag_screen_view.fxml");
//        sm.showStage();
    }
    public static void main(String[] args){
        launch(args);
    }


}

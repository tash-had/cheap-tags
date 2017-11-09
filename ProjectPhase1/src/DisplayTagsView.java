import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class DisplayTagsView {

    public DisplayTagsView(){
    }
    
    public static Scene getScene(Button back){

        StackPane layout = new StackPane();
        layout.getChildren().add(back);
        Scene s = new Scene(layout, 300,250);
        return s;

    }




}

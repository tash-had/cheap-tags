import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import java.util.ArrayList;

public class DisplayTagsView {

    public static ScrollPane constructTagScene(){
        GridPane grid = new GridPane();
        grid.setPadding(new Insets(5));
        grid.setHgap(5);
        grid.setVgap(5);

        for (int r = 0; r < 8; r++) {
            for (int c = 0; c < 8; c++) {
                Button button = new Button(r + ":" + c);
                grid.add(button, c, r);
            }
        }

        ScrollPane scrollPane = new ScrollPane(grid);
        return scrollPane;
    }

}

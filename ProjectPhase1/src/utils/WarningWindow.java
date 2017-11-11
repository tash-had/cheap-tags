package utils;

import com.sun.corba.se.spi.monitoring.LongMonitoredAttributeBase;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

//citing this from JavaFx Tut5th.
public class WarningWindow{
    public static void display(String message){
        Stage window = new Stage();

        //this one should be taken care of first
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle("WARNING!!!");
        window.setMinWidth(300);

        Label text = new Label();
        text.setText(message);
        Button button = new Button("I Know, Close This Window");
        button.setOnAction(event -> window.close());

        VBox layout = new VBox(15);
        layout.getChildren().add(text);
        layout.getChildren().add(button);
        layout.setAlignment(Pos.CENTER);

        Scene scene = new Scene(layout);
        window.setScene(scene);
        window.showAndWait();//display this window that needed to be
        //close to further process other things.
    }
}

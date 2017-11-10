import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by tash-had on 2017-11-10.
 */
public class newWindowController implements Initializable {
    @FXML
    Button b;

    public void buttonClick(){
        System.out.println("Helll");
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ConfigureJFXControl.setFontOfLabeled("resources/fonts/Roboto-Light.ttf", 30,b);

    }
}
